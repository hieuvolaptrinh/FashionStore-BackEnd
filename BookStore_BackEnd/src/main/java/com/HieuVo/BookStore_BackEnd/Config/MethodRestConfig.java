package com.HieuVo.BookStore_BackEnd.Config;

import com.HieuVo.BookStore_BackEnd.Model.Type;
import com.HieuVo.BookStore_BackEnd.Model.User;

import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class MethodRestConfig implements RepositoryRestConfigurer {

    private String url = "http://localhost:5173";

    private final EntityManager entityManager;

    public MethodRestConfig(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        //
        HttpMethod[] blockMethods = {
                HttpMethod.POST,
                HttpMethod.PUT,
                HttpMethod.DELETE
        };
        // Lấy tất cả các entity và expose ID
        config.exposeIdsFor(entityManager.getMetamodel().getEntities().stream()
                .map(entityType -> entityType.getJavaType())
                .toArray(Class[]::new));

        // HttpMethod[] deleteMethod = {
        // HttpMethod.DELETE
        // };

        // disableHttpMethods(Type.class, config, blockMethods);
        // disableHttpMethods(User.class, config, deleteMethod);

    }

    // tùy chọn chặn các phương thức HTTP
    private void disableHttpMethods(Class c, RepositoryRestConfiguration config, HttpMethod[] methods) {

        config.getExposureConfiguration()
                .forDomainType(c)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(methods))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(methods));
    }

}
