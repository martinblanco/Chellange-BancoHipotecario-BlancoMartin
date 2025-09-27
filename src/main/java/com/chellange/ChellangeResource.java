package com.chellange;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.chellange.service.PostService;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/post")
@Tag(name = "Posts", description = "Endpoints relacionados con posts")
public class ChellangeResource {
	@Inject
	PostService postService;
	
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Operation(summary = "Obtiene todos los posts con Comentarios y informacion del Autor", 
    	description = "Muestra todos los Post con el nombre de la persona que lo publico y con los comentarios que recibio con el mail de la persona que comento")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Lista de posts obtenida correctamente"),
        @APIResponse(responseCode = "500", description = "Error interno al obtener los posts")
    })
    public String getPostWithAuthorAndComments() {
    	return postService.getPosts();
    }
    
    @DELETE
    @Path("/{id}")
    @Operation(summary = "Elimina un post por su ID", description = "Realiza un DELETE de un post por su ID unico.")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Post eliminado correctamente"),
        @APIResponse(responseCode = "404", description = "Post no encontrado"),
        @APIResponse(responseCode = "500", description = "Error interno al eliminar post")
    })
    public Response deletePost(@PathParam("id") int id) {
        return Response.status(postService.deletePost(id)).build();
    }
}