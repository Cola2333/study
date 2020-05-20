package life.usc.study.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringBootWebMvcConfigurer implements WebMvcConfigurer {
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        //把files开头的url解析到file:...
        registry.addResourceHandler("/files/**").addResourceLocations("file:C:\\WJJ\\java\\IDEAIU-workspace\\study\\src\\main\\resources\\upload\\");
    }
}