package genesys.project.usermanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for web settings, including CORS (Cross-Origin Resource Sharing) policies.
 * This configuration allows requests from specified origins and defines allowed HTTP methods and headers.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    // Basically configures CORS setting to allow the UI to access the endpoints of this API
    @Bean
    public WebMvcConfigurer corsConfig() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        /**
                         If you're planning on using the UI and chose a different port
                         make sure that's reflected here
                         **/
                        .allowedOrigins("http://localhost:3000")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*");
            }
        };
    }
}
