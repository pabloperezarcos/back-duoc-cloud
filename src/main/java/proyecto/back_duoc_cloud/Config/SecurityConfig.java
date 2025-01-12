package proyecto.back_duoc_cloud.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.web.cors.CorsConfiguration;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
              /*   .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.addAllowedOrigin("*"); // Permitir todos los orígenes
                    config.addAllowedHeader("*"); // Permitir todos los encabezados
                    config.addAllowedMethod("*"); // Permitir todos los métodos HTTP
                    return config;
                })) */
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/api/alertas/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/**").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .anyRequest().authenticated())
                .httpBasic(httpBasic -> {
                }); // Configuración para autenticación básica
        // .httpBasic(httpBasic -> httpBasic.disable());

        return http.build();
    }

}
