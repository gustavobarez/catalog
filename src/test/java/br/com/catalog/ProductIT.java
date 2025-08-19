package br.com.catalog;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import br.com.catalog.modules.dto.ProductDTO;
import br.com.catalog.modules.entity.CategoryEntity;
import io.quarkus.test.junit.QuarkusIntegrationTest;
import io.quarkus.test.junit.TestProfile;
import io.restassured.http.ContentType;

@QuarkusIntegrationTest
@TestProfile(LocalStackProfile.class)
@TestMethodOrder(OrderAnnotation.class)
public class ProductIT {

        public static UUID categoryId;
        public static UUID productId;

        @Test
        @Order(1)
        public void testCreateCategory() {
                CategoryEntity category = new CategoryEntity(
                                null,
                                "Title Test",
                                "Test Desc",
                                "Test Owner",
                                null);

                String returnedId = given()
                                .contentType(ContentType.JSON)
                                .body(category)
                                .when()
                                .post("/api/category")
                                .then()
                                .log().all()
                                .statusCode(201)
                                .extract()
                                .path("id");

                categoryId = UUID.fromString(returnedId);

                given()
                                .when()
                                .get("/api/category/" + categoryId)
                                .then()
                                .log().all()
                                .statusCode(200)
                                .body("title", equalTo(category.getTitle()));
        }

        @Test
        @Order(2)
        @DisplayName("Deve criar um produto com sucesso")
        public void testCreateProduct() {
                ProductDTO product = new ProductDTO(
                                "Produto Teste",
                                "Test Desc",
                                new BigDecimal("99.99"),
                                categoryId.toString(),
                                "ownerTest");

                String returnedId = given()
                                .contentType(ContentType.JSON)
                                .body(product)
                                .when()
                                .post("/api/product")
                                .then()
                                .log().all()
                                .statusCode(201)
                                .body("title", equalTo(product.title()))
                                .body("description", equalTo(product.description()))
                                .extract()
                                .path("id");

                productId = UUID.fromString(returnedId);

                given()
                                .when()
                                .get("/api/product/" + productId)
                                .then()
                                .log().all()
                                .statusCode(200)
                                .body("id", equalTo(productId.toString()))
                                .body("title", equalTo(product.title()));
        }

        @Test
        @Order(3)
        @DisplayName("Deve retornar erro ao criar produto com categoria inexistente")
        public void testCreateProductWithInvalidCategory() {
                ProductDTO product = new ProductDTO("Produto Teste",
                                "Description",
                                new BigDecimal("99.99"),
                                UUID.randomUUID().toString(),
                                "ownerTest");

                given()
                                .contentType(ContentType.JSON)
                                .body(product)
                                .when()
                                .post("/api/product")
                                .then()
                                .log().all()
                                .statusCode(404);
        }

        @Test
        @Order(4)
        @DisplayName("Deve buscar um produto por ID")
        public void testGetProduct() {
                given()
                                .when()
                                .get("/api/product/" + productId)
                                .then()
                                .log().all()
                                .statusCode(200)
                                .body("id", equalTo(productId.toString()));
        }

        @Test
        @Order(5)
        @DisplayName("Deve atualizar um produto")
        public void testUpdateProduct() {
                ProductDTO product = new ProductDTO("Produto Atualizado",
                                "Nova Descrição",
                                new BigDecimal("199.99"),
                                categoryId.toString(),
                                "ownerTest");

                given()
                                .contentType(ContentType.JSON)
                                .body(product)
                                .when()
                                .put("/api/product/" + productId)
                                .then()
                                .log().all()
                                .statusCode(200)
                                .body("title", equalTo("Produto Atualizado"));
        }

        @Test
        @Order(6)
        @DisplayName("Deve deletar um produto")
        public void testDeleteProduct() {
                given()
                                .when()
                                .delete("/api/product/" + productId)
                                .then()
                                .log().all()
                                .statusCode(200);

                given()
                                .when()
                                .get("/api/product/" + productId)
                                .then()
                                .statusCode(404);
        }

}
