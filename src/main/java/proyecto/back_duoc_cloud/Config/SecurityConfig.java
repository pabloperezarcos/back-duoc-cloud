package proyecto.back_duoc_cloud.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Deshabilita CSRF
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/alertas").permitAll() // Permitir acceso a /api/alertas sin autenticación
                        .anyRequest().authenticated() // Requiere autenticación para otras rutas
                )
                .httpBasic(httpBasic -> {
                }); // Configuración para autenticación básica

        return http.build();
    }
}
