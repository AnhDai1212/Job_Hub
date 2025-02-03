package java_spring.job_hub.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private static final String[] PUBLIC_ENDPOINTS = {
        "/users",
        "/auth/introspect",
        "/auth/token",
        "/roles",
        "/permissions",
        "/auth/logout",
        "/auth/refresh",
    };

    private CustomJwtDecoder customJwtDecoder;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Thêm cấu hình CORS tại đây
                //                .csrf(csrf -> csrf.disable()) // Vô hiệu hóa CSRF. khi ko su dung jwt
                .authorizeHttpRequests(request -> request.requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINTS)
                        .permitAll()
                        .requestMatchers(HttpMethod.GET, "/user-check/**","/users/activate")
                        .permitAll()

                        //                                .requestMatchers(HttpMethod.POST, "/auth/token").permitAll()
                        //                        .requestMatchers(HttpMethod.GET, "/users").hasAuthority("ADMIN")
                        .anyRequest()
                        .authenticated());
        httpSecurity.oauth2ResourceServer(
                oauth2 -> oauth2.jwt(
                                jwtConfigurer -> jwtConfigurer
                                        .decoder(customJwtDecoder)
                                        .jwtAuthenticationConverter(
                                                jwtAuthenticationConverter()) // chuyen doi cac doi tuong trong jwt
                                // thanh mot doi tuong authenticated trong
                                // spring security
                                )
                        .authenticationEntryPoint(
                                new JwtAuthenticationEntryPoint()) // Xu ly error xay ra tren tang filler chua xuong toi
                // service
                );
        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        return httpSecurity.build();
    }

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        //        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");  // thêm cái roll de tao cai prefit phia
        // trc role là ROLL_
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix(""); // Xoá đi để su dung prefit bên phía buildScope

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }
    // Chuyen doi va giai ma jwt bang secretKey
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:3000"); // Origin frontend
        config.addAllowedHeader("*"); // Cho phép tất cả header
        config.addAllowedMethod("*"); // Cho phép tất cả phương thức
        config.setAllowCredentials(true); // Cho phép cookies hoặc thông tin xác thực

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
