package java_spring.job_hub.service;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import com.google.api.client.util.DateTime;
import java_spring.job_hub.dto.request.*;
import java_spring.job_hub.dto.response.AuthenticationResponse;
import java_spring.job_hub.dto.response.IntrospectResponse;
import java_spring.job_hub.entity.InvalidatedToken;
import java_spring.job_hub.entity.Role;
import java_spring.job_hub.entity.User;
import java_spring.job_hub.exception.AppException;
import java_spring.job_hub.exception.ErrorCode;
import java_spring.job_hub.repository.InvalidatedTokenRepository;
import java_spring.job_hub.repository.RoleRepository;
import java_spring.job_hub.repository.httpClient.OutboundIdentityClient;
import java_spring.job_hub.repository.UserRepository;

import java_spring.job_hub.repository.httpClient.OutboundUserClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthenticationService {
    RoleRepository roleRepository;
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    InvalidatedTokenRepository invalidatedTokenRepository;
    OutboundIdentityClient outboundIdentityClient;
    OutboundUserClient outboundUserClient;

    @NonFinal
    @Value("${JWT_SIGNER_KEY}")
    protected String SIGNER_KEY;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    protected Long REFRESHABLE_DURATION;

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected Long VALID_DURATION;

//    private final RoleRepository roleRepository;

    @Value("${GOOGLE_CLIENT_ID:NOT_SET}")
    @NonFinal
    protected String CLIENT_ID;

    @Value("${GOOGLE_CLIENT_SECRET:NOT_SET}")
    @NonFinal
    protected String CLIENT_SECRET;

    @Value("${GOOGLE_REDIRECT_URI:NOT_SET}")
    @NonFinal
    protected String REDIRECT_URI;
//    @PostConstruct
//    public void printEnvVariables() {
//        log.info("🚀 SIGNER_KEY: {}", SIGNER_KEY);
//        log.info("🚀 GOOGLE_CLIENT_ID: {}", CLIENT_ID);
//        log.info("🚀 GOOGLE_CLIENT_SECRET: {}", CLIENT_SECRET);
//        log.info("🚀 GOOGLE_REDIRECT_URI: {}", REDIRECT_URI);
//    }
    @NonFinal
    protected String GRANT_TYPE = "authorization_code";

    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        var token = request.getToken();
        boolean isValid = true;

        try {
            verifyToken(token, false);
        } catch (Exception e) {
            isValid = false;
        }

        return IntrospectResponse.builder().valid(isValid).build();
    }
    //Login with google
    public AuthenticationResponse outboundAuthenticate(String code) {

        var response = outboundIdentityClient.exchangeToken(ExchangeTokenRequest.builder()
                        .code(code)
                        .clientId(CLIENT_ID)
                        .clientSecret(CLIENT_SECRET)
                        .redirectUri(REDIRECT_URI)
                        .grantType(GRANT_TYPE)
                .build());
        Role role = roleRepository.findByName("USER").orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXIST));
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        var userInfor = outboundUserClient.getUserInfor("json", response.getAccessToken());
        var user = userRepository.findUserByUsername(userInfor.getEmail()).orElseGet(
                () ->  userRepository.save(User.builder()
                                .username(userInfor.getEmail())
                                .id(userInfor.getId())
                                .email(userInfor.getEmail())
                                .firstName(userInfor.getGivenName())
                                .lastName(userInfor.getFamilyName())
                                .createAt(LocalDateTime.now())
                                .roles(roles)
                        .build())
        );
        log.info("Token response: {}" + userInfor);
        return AuthenticationResponse.builder()
                .token(response.getAccessToken())
                .build();
    }



    // Kiem tra dang nhap và tao token cho client
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = userRepository
                .findUserByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        boolean password = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!password) {
            throw new AppException(ErrorCode.PASSWORD_IS_NOT_CORRECT);
        }
        if (!user.getIsActivation()){
            throw  new AppException(ErrorCode.UNVERIFIED_ACCOUNT);
        }

        var token = generateToken(user);

        return AuthenticationResponse.builder().token(token).authenticated(true).build();
    }
    // Tao token bang user
    private String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("devteria.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(user))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
    }

    private String buildScope(User user) {
        if (user == null) {
            return "";
        }
        StringJoiner stringJoiner = new StringJoiner(" ");

        if (!CollectionUtils.isEmpty(user.getRoles()))
            user.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getName()); // ROLE_ giup phan biet cac role va permission
                if (!CollectionUtils.isEmpty(role.getPermissions()))
                    role.getPermissions().forEach(permission -> stringJoiner.add(permission.getName()));
            });

        return stringJoiner.toString();
    }

    public void logoutToken(LogoutRequest request) throws ParseException, JOSEException {

        try {
            var signToken = verifyToken(request.getToken(), false);

            String jid = signToken.getJWTClaimsSet().getJWTID();
            Date expireTime = signToken.getJWTClaimsSet().getExpirationTime();

            InvalidatedToken invalidatedToken =
                    InvalidatedToken.builder().id(jid).expiryTime(expireTime).build();
            invalidatedTokenRepository.save(invalidatedToken);
        } catch (Exception e) {
            log.info("Token already expired");
        }
    }
    // Giai ma token
    private SignedJWT verifyToken(String token, boolean isRefresh) throws ParseException, JOSEException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes()); // tạo trình xác thực verifier

        SignedJWT signedJWT = SignedJWT.parse(token); // Phan tich token

        // Kiem tra xem co dung de refresh hay ko
        Date expiryTime = (isRefresh)
                ? new Date(signedJWT
                        .getJWTClaimsSet()
                        .getIssueTime()
                        .toInstant()
                        .plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS)
                        .toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier); // true nếu như đã xác thực
        if (!(verified && expiryTime.after(new Date()))) { // Kiem tra tinh hop le cua token
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        if (invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        return signedJWT; // Doi tuong dai dien cho JWT co the lay ra cac thanh phan trong token (Header, Payload,
        // Signature)
    }

    public AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException {
        var signedJWT = verifyToken(request.getToken(), true);

        var jid = signedJWT.getJWTClaimsSet().getJWTID();
        var expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken =
                InvalidatedToken.builder().id(jid).expiryTime(expiryTime).build();
        invalidatedTokenRepository.save(invalidatedToken);

        var username = signedJWT.getJWTClaimsSet().getSubject();

        var user = userRepository
                .findUserByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));

        var token = generateToken(user);

        return AuthenticationResponse.builder().authenticated(true).token(token).build();
    }
}
