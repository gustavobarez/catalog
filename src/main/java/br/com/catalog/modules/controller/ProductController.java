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

@Path("/api/product")
public class ProductController {

    @Inject
    public ProductService productService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insert(ProductDTO productDTO) {
        var product = productService.insert(productDTO);
        URI location = URI.create("/api/product/" + product.getId());
        ProductResponseDTO dto = new ProductResponseDTO(product);
        return Response.created(location).entity(dto).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") String id, ProductDTO productDTO) {
        var dto = productService.update(id, productDTO);
        return Response.ok(dto).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") String id) {
        var dto = productService.delete(id);
        return Response.ok(dto).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") String id) {
        var product = productService.findById(id);
        ProductResponseDTO dto = new ProductResponseDTO(product.get());
        return Response.ok(dto).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        var products = productService.findAll();
        return Response.ok(products).build();
    }

}
