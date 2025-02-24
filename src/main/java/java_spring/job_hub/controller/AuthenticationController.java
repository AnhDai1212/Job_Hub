package java_spring.job_hub.controller;

import java.text.ParseException;
import java.util.Map;

import java_spring.job_hub.dto.request.AuthenticationRequest;
import java_spring.job_hub.dto.request.IntrospectRequest;
import java_spring.job_hub.dto.request.LogoutRequest;
import java_spring.job_hub.dto.request.RefreshRequest;
import java_spring.job_hub.dto.response.ApiResponse;
import java_spring.job_hub.dto.response.AuthenticationResponse;
import java_spring.job_hub.dto.response.IntrospectResponse;
import java_spring.job_hub.service.AuthenticationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.nimbusds.jose.JOSEException;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import java.util.Collections;
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;


    @PostMapping("/outbound/authentication")
    ApiResponse<AuthenticationResponse> outboundAuthentication (@RequestParam("code") String code){
        var result = authenticationService.outboundAuthenticate(code);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }




    //    @PostMapping("/login")
    //    public ApiResponse<AuthResponse> loginPage(@RequestBody UserLoginRequest request) {
    //        return ApiResponse.<AuthResponse>builder()
    //                .result()
    //                .build()
    //    }
    @PostMapping("/token")
    public ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse authenticationResponse = authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(authenticationResponse)
                .build();
    }

    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> introspectResponseApiResponse(@RequestBody IntrospectRequest request)
            throws JOSEException, ParseException {
        var result = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder().result(result).build();
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(@RequestBody LogoutRequest request) throws JOSEException, ParseException {
        authenticationService.logoutToken(request);
        return ApiResponse.<Void>builder().build();
    }

    @PostMapping("/refresh")
    public ApiResponse<AuthenticationResponse> refreshToken(@RequestBody RefreshRequest request)
            throws ParseException, JOSEException {
        return ApiResponse.<AuthenticationResponse>builder()
                .result(authenticationService.refreshToken(request))
                .build();
    };



    @PostMapping("/google")
    public String googleLogin(@RequestBody String idTokenString) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    new NetHttpTransport(),
                    new JacksonFactory()
            )
                    .setAudience(Collections.singletonList("3964472893-0g9mdp6aaka2ml6f8cccuq60feha3fdf.apps.googleusercontent.com")) // Replace with your actual client ID
                    .build();

            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();

                // Get email of user
                String email = payload.getEmail();
                return "Đăng nhập thành công với email: " + email;
            } else {
                return "Google login failed: Invalid ID token";
            }
        } catch (Exception e) {
            return "Google login failed: " + e.getMessage();
        }
    }

}
