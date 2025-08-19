package br.com.catalog.catalog.category.application;

import java.net.URI;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import br.com.catalog.catalog.category.domain.CategoryEntity;
import br.com.catalog.catalog.category.domain.CategoryService;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@Path("/api/category")
@Tag(name = "Category", description = "Category management operations")
public class CategoryController {

    public CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create category", description = "Creates a new category in the catalog")
    @APIResponses({
            @APIResponse(responseCode = "201", description = "Category created successfully"),
            @APIResponse(responseCode = "400", description = "Invalid input data")
    })
    public Response insert(CategoryDTO categoryDTO, @Context UriInfo uriInfo) {
        CategoryEntity category = categoryService.insert(categoryDTO);
        URI location = URI.create("/api/category/" + category.getId());
        CategoryResponseDTO dto = new CategoryResponseDTO(category);
        return Response.created(location).entity(dto).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Update category", description = "Updates an existing category")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Category updated successfully"),
            @APIResponse(responseCode = "404", description = "Category not found")
    })
    public Response update(@Parameter(description = "Category ID") @PathParam("id") String id,
            CategoryDTO categoryDTO) {
        var dto = categoryService.update(id, categoryDTO);
        return Response.ok(dto).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Delete category", description = "Deletes a category from the catalog")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Category deleted successfully"),
            @APIResponse(responseCode = "404", description = "Category not found")
    })
    public Response delete(@Parameter(description = "Category ID") @PathParam("id") String id) {
        var dto = categoryService.delete(id);
        return Response.ok(dto).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get category by ID", description = "Retrieves a specific category by its ID")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Category found"),
            @APIResponse(responseCode = "404", description = "Category not found")
    })
    public Response findById(@Parameter(description = "Category ID") @PathParam("id") String id) {
        var category = categoryService.findById(id).get();
        return Response.ok(category).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "List all categories", description = "Retrieves all categories from the catalog")
    @APIResponse(responseCode = "200", description = "List of categories")
    public Response findAll() {
        var categories = categoryService.findAll();
        return Response.ok(categories).build();
    }
}