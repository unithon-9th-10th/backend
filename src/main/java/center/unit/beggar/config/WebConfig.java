package center.unit.beggar.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@ConditionalOnWebApplication
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        WebMvcConfigurer.super.addCorsMappings(registry);

        registry.addMapping("/api/v1/**")
                .allowedHeaders("X-BEGGAR-MEMBER-ID")
                .allowedMethods("OPTIONS", "HEAD", "GET", "POST", "PUT", "DELETE")
                .allowedOrigins("http://localhost:3000")
                .allowCredentials(true);
    }
}
