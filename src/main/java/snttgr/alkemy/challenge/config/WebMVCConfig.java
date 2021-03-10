package snttgr.alkemy.challenge.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


//Extending default configuration
@Configuration
public class WebMVCConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new SchoolClassFormatter());
        registry.addFormatter(new ProfessorFormatter());

    }
}
