package br.com.catalog.modules.controller;

import java.net.URI;

import br.com.catalog.modules.dto.CategoryDTO;
import br.com.catalog.modules.dto.CategoryResponseDTO;
import br.com.catalog.modules.entity.CategoryEntity;
import br.com.catalog.modules.service.CategoryService;
import jakarta.inject.Inject;
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
public class CategoryController {

    @Inject
    public CategoryService categoryService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response insert(CategoryDTO categoryDTO, @Context UriInfo uriInfo) {
        CategoryEntity category = categoryService.insert(categoryDTO);
        URI location = URI.create("/api/category/" + category.getId());
        CategoryResponseDTO dto = new CategoryResponseDTO(category);
        return Response.created(location).entity(dto).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") String id, CategoryDTO categoryDTO) {
        var dto = categoryService.update(id, categoryDTO);
        return Response.ok(dto).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") String id) {
        var category = categoryService.findById(id).get();
        CategoryResponseDTO dto = new CategoryResponseDTO(category);
        return Response.ok(dto).build();
    }

}
