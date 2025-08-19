package br.com.catalog.modules.controller;

import java.net.URI;
import br.com.catalog.modules.dto.ProductDTO;
import br.com.catalog.modules.dto.ProductResponseDTO;
import br.com.catalog.modules.service.ProductService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

@Path("/api/product")
@Tag(name = "Product", description = "Product management operations")
public class ProductController {

    @Inject
    public ProductService productService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create product", description = "Creates a new product in the catalog")
    @APIResponses({
            @APIResponse(responseCode = "201", description = "Product created successfully"),
            @APIResponse(responseCode = "400", description = "Invalid input data")
    })
    public Response insert(ProductDTO productDTO) {
        var product = productService.insert(productDTO);
        URI location = URI.create("/api/product/" + product.getId());
        ProductResponseDTO dto = new ProductResponseDTO(product);
        return Response.created(location).entity(dto).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Update product", description = "Updates an existing product")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Product updated successfully"),
            @APIResponse(responseCode = "404", description = "Product not found")
    })
    public Response update(@Parameter(description = "Product ID") @PathParam("id") String id, ProductDTO productDTO) {
        var dto = productService.update(id, productDTO);
        return Response.ok(dto).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Delete product", description = "Deletes a product from the catalog")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Product deleted successfully"),
            @APIResponse(responseCode = "404", description = "Product not found")
    })
    public Response delete(@Parameter(description = "Product ID") @PathParam("id") String id) {
        var dto = productService.delete(id);
        return Response.ok(dto).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get product by ID", description = "Retrieves a specific product by its ID")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Product found"),
            @APIResponse(responseCode = "404", description = "Product not found")
    })
    public Response findById(@Parameter(description = "Product ID") @PathParam("id") String id) {
        var product = productService.findById(id);
        ProductResponseDTO dto = new ProductResponseDTO(product.get());
        return Response.ok(dto).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "List all products", description = "Retrieves all products from the catalog")
    @APIResponse(responseCode = "200", description = "List of products")
    public Response findAll() {
        var products = productService.findAll();
        return Response.ok(products).build();
    }
}