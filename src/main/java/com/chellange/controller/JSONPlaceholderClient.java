package com.chellange.controller;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import jakarta.validation.constraints.Min;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@RegisterRestClient(configKey="api-external")
public interface JSONPlaceholderClient {
	
	@GET
	@Path("/posts")
    @Produces(MediaType.APPLICATION_JSON)
    Response getPosts();
	
	@GET
	@Path("/posts/{postId}/comments")
    @Produces(MediaType.APPLICATION_JSON)
    Response getCommentsByPost(@PathParam("postId") @Min(1) int id);
	
	@GET
	@Path("/users/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getUser(@PathParam("userId") @Min(1) int id);

    @DELETE
    @Path("/posts/{id}")
    Response deletePost(@PathParam("id") @Min(1) int id);
}