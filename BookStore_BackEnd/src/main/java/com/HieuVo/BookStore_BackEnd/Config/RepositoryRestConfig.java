package com.HieuVo.BookStore_BackEnd.Config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

@Configuration
public class RepositoryRestConfig {

    private final EntityManager entityManager;

    public RepositoryRestConfig(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Bean
    public RepositoryRestConfigurer repositoryRestConfigurer() {
        return RepositoryRestConfigurer.withConfig(config -> {
            // Lấy tất cả các entity và expose ID
            config.exposeIdsFor(entityManager.getMetamodel().getEntities().stream()
                    .map(Type::getJavaType)
                    .toArray(Class[]::new));
        });
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        // Cho phép Jackson truy cập trực tiếp vào field thay vì cần getter
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        return mapper;
    }
}
