package br.com.catalog.catalog.product.domain;

import java.util.UUID;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProductRepository implements PanacheRepository<ProductEntity> {

    public ProductEntity findById(UUID id) {
        return find("id", id).firstResult();
    }

    public ProductEntity findByTitle(String title) {
        return find("title", title).firstResult();
    }

}
