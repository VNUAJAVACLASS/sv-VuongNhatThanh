package vnua.fita.tthieu.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry
            .addResourceHandler("/img/**")
            .addResourceLocations("classpath:/static/img/");

        // (không bắt buộc, nhưng nên có)
        registry
            .addResourceHandler("/css/**")
            .addResourceLocations("classpath:/static/css/");

        registry
            .addResourceHandler("/js/**")
            .addResourceLocations("classpath:/static/js/");
    }
}
