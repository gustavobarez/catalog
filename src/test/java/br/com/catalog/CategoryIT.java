package br.com.catalog;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import br.com.catalog.modules.dto.CategoryDTO;
import io.quarkus.test.junit.QuarkusIntegrationTest;
import io.quarkus.test.junit.TestProfile;
import io.restassured.http.ContentType;

@QuarkusIntegrationTest
@TestProfile(LocalStackProfile.class)
@TestMethodOrder(OrderAnnotation.class)
public class CategoryIT {
        public static UUID categoryId;

        @Test
        @Order(1)
        @DisplayName("Should create a category")
        public void testCreateCategory() {
                CategoryDTO category = new CategoryDTO(
                                "Test Title",
                                "Test Desc",
                                "Test Owner");
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
                                .body("title", equalTo(category.title()));
        }

        @Test
        @Order(2)
        @DisplayName("Should search a category by ID")
        public void testGetCategory() {
                given()
                                .when()
                                .get("/api/category/" + categoryId)
                                .then()
                                .log().all()
                                .statusCode(200)
                                .body("id", equalTo(categoryId.toString()));
        }

        @Test
        @Order(3)
        @DisplayName("Should update a category")
        public void testUpdateCategory() {
                CategoryDTO category = new CategoryDTO(
                                "Updated Title",
                                "Updated Desc",
                                "Updated Owner");
                given()
                                .contentType(ContentType.JSON)
                                .body(category)
                                .when()
                                .put("/api/category/" + categoryId)
                                .then()
                                .log().all()
                                .statusCode(200)
                                .body("title", equalTo("Updated Title"));
        }

        @Test
        @Order(4)
        @DisplayName("Should delete a category")
        public void testDeleteCategory() {
                given()
                                .when()
                                .delete("/api/category/" + categoryId)
                                .then()
                                .log().all()
                                .statusCode(200);
                given()
                                .when()
                                .get("/api/category/" + categoryId)
                                .then()
                                .statusCode(404);
        }
}