package br.com.catalog.modules.entity;

import java.math.BigDecimal;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

import br.com.catalog.modules.dto.ProductDTO;
import io.smallrye.common.constraint.NotNull;
import io.vertx.core.json.JsonObject;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    private String title;

    @NotNull
    private String description;

    @Column(precision = 19, scale = 4, nullable = false)
    private BigDecimal price;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @JsonBackReference
    private CategoryEntity category;

    @Column(name = "owner_id", nullable = false)
    private String ownerId;

    public ProductEntity(ProductDTO dto) {
        this.title = dto.title();
        this.description = dto.description();
        this.price = dto.price();
        this.ownerId = dto.ownerId();
    }

    @Override
    public String toString() {
        JsonObject json = new JsonObject();

        json.put("title", this.title);
        json.put("description", this.description);
        json.put("price", this.price);
        try {
            if (this.category != null) {
                json.put("categoryId", this.category.getId());
                json.put("categoryTitle", this.category.getTitle());
            }
        } catch (Exception e) {
            json.put("categoryId", "lazy_not_loaded");
        }
        json.put("ownerId", this.ownerId);
        json.put("type", "product");

        return json.toString();
    }

}
