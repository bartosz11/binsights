package one.bartosz.metrics.config;

import one.bartosz.metrics.LoggerInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.resource.EncodedResourceResolver;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class MVCConfiguration implements WebMvcConfigurer {
    
    private final boolean production;

    public MVCConfiguration(@Value("${metrics.production}") boolean production) {
        this.production = production;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //add our cool logging interceptor
        registry.addInterceptor(new LoggerInterceptor());
    }
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        //set default content type to application json
        configurer.defaultContentType(MediaType.APPLICATION_JSON);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/assets/**")
                .setCachePeriod(3600)
                .addResourceLocations("classpath:/static/assets/")
                .resourceChain(true)
                .addResolver(new EncodedResourceResolver())
                .addResolver(new PathResourceResolver());
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //allow vite dev server to talk to our API
        if (!production)
            registry.addMapping("/**").allowedOrigins("http://localhost:5173").allowCredentials(true).allowedMethods("GET", "HEAD", "POST", "OPTIONS", "DELETE", "PATCH", "PUT");
    }
}
