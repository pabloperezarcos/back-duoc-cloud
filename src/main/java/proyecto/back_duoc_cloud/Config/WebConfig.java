package proyecto.back_duoc_cloud.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Permitir todas las rutas
                        // Permitir solicitudes desde el frontend
                        // .allowedOrigins("http://localhost:4200")
                        // Permitir solicitudes desde el frontend
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos permitidos
                        .allowedHeaders("*"); // Permitir todas las cabeceras
                // .allowCredentials(true); // Permitir cookies o credenciales
            }
        };
    }
}
