package genesys.project.usermanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

// Marks this class as a configuration class for Spring
@Configuration
// Enables Spring Security for the application
@EnableWebSecurity
public class SecurityConfig {
    /**
     * Defines a Spring bean that sets up the security filter chain that basically
     * disables CSRF protection and configures authorization to allow all requests without authentication.
     * Not safe, but for the sake of this simple project, I chose this path.
     **/
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll());
        return http.build();
    }
}
