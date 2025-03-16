package com.HieuVo.BookStore_BackEnd.Config;

import com.HieuVo.BookStore_BackEnd.Model.Type;
import com.HieuVo.BookStore_BackEnd.Model.User;

import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MethodRestConfig implements RepositoryRestConfigurer {

    private String url = "http://localhost:5173";

    private final EntityManager entityManager;

    public MethodRestConfig(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


//    config với api RepositoryRest
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        // Lấy tất cả các entity và expose ID
        config.exposeIdsFor(entityManager.getMetamodel().getEntities().stream()
                .map(entityType -> entityType.getJavaType())
                .toArray(Class[]::new));
        //CORS configuration
        cors.addMapping("/**")
                .allowedOrigins(url)
                .allowedMethods("GET", "POST", "PUT", "DELETE");


        HttpMethod[] blockMethods = {
                HttpMethod.POST,
                HttpMethod.PUT,
                HttpMethod.DELETE
        };
        // disableHttpMethods(Type.class, config, blockMethods);
        // disableHttpMethods(User.class, config, deleteMethod);

    }

//    CORS với api tự viết
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")  // Cho phép tất cả API
                        .allowedOrigins(url)  // Frontend
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
    // tùy chọn chặn các phương thức HTTP
    private void disableHttpMethods(Class c, RepositoryRestConfiguration config, HttpMethod[] methods) {

        config.getExposureConfiguration()
                .forDomainType(c)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(methods))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(methods));
    }

}
