package proyecto.back_duoc_cloud.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> {
                }) // Habilitar CORS
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/api/alertas/**").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .anyRequest().authenticated())
                // .httpBasic(httpBasic -> { }); // Configuración para autenticación básica
                .httpBasic(httpBasic -> httpBasic.disable());

        return http.build();
    }


}
