package br.com.catalog.modules.controller;

import java.net.URI;

import br.com.catalog.modules.dto.ProductDTO;
import br.com.catalog.modules.dto.ProductResponseDTO;
import br.com.catalog.modules.service.ProductService;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/product")
public class ProductController {

    @Inject
    public ProductService productService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response insert(ProductDTO productDTO) {
        var product = productService.insert(productDTO);
        URI location = URI.create("/api/product/" + product.getId());
        ProductResponseDTO dto = new ProductResponseDTO(product);
        return Response.created(location).entity(dto).build();
    }

}
