package com.mytutors.mytutors.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import java.nio.file.Paths;


@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String ruta = Paths.get(System.getProperty("user.home"), "mytutors/uploads/img/usuarios").toUri().toString();

        registry.addResourceHandler("/imagenes/**")
                .addResourceLocations(ruta);
    }

}
