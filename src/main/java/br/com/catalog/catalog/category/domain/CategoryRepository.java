package br.com.catalog.catalog.category.domain;

import java.util.UUID;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CategoryRepository implements PanacheRepository<CategoryEntity> {

    public CategoryEntity findById(UUID id) {
        return find("id", id).firstResult();
    }

}
