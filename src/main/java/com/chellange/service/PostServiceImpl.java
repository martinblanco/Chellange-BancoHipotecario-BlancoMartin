package com.chellange.service;

import java.util.List;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;
import com.chellange.controller.JSONPlaceholderClient;
import com.chellange.dto.CommentDTO;
import com.chellange.dto.PostDTO;
import com.chellange.dto.UserDTO;
import com.chellange.exception.ExternalException;
import com.chellange.exception.NotFoundException;
import com.chellange.exception.mapper.NotFoundExceptionMapper;

import io.quarkus.cache.CacheResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class PostServiceImpl implements PostService {
	private static final Logger LOG = Logger.getLogger(NotFoundExceptionMapper.class);
	
	@Inject
	@RestClient
	JSONPlaceholderClient JSONPlaceholderClient;
	
	@ConfigProperty(name = "app.max-posts")
	public int maxPosts;
	
	@ConfigProperty(name = "app.max-comments")
	public int maxComments;
	
	@Override
	public String getPosts() {
		LOG.info("Buscando un maximo de " + maxPosts + " con un maximo de " + maxComments + " comentarios cada uno.");
	    List<PostDTO> posts = getAllPosts();
	    StringBuilder sb = new StringBuilder();
	    int count = 0;
	    if(posts == null || posts.isEmpty()) {
	    	LOG.info("No se encontro ningun Post");
	    	sb.append("No se encontro ningun Post");
	    	return sb.toString();
	    }
	    LOG.info("Se encontraron " + posts.size() + " posts.");
	    sb.append("Se encontraron " + posts.size() + " posts. Se muestran como maximo " + maxPosts + " posts.\n");
	    sb.append("-------------------------------------------\n");
	    for (PostDTO post : posts) {
	    	sb.append(getCompletePost(post));
	    	sb.append("-------------------------------------------\n");
	    	count++;
	        if (count >= maxPosts) break;
	    }
	    return sb.toString();
	}
	 
	@Override
	public String getCompletePost(PostDTO post) {
		StringBuilder sb = new StringBuilder();
		UserDTO user = getUserById(post.getUserId());
	    sb.append(user.toString());
	    sb.append(post.toString());
	    
	    List<CommentDTO> comments = getCommentsByPostId(post.getId());
	    if(comments == null || comments.isEmpty()) {
	    	LOG.info("No se encontro ningun comentario en este post");
	    	sb.append("0 Comentarios.");
	    	return sb.toString();
	    }
	    sb.append(comments.size()+ " Comentarios (Se muestran como maximo " + maxComments + " comentarios):\n");
	    int count = 0;
	    for (CommentDTO comment : comments) {
	       sb.append(comment.toString());
	       count++;
           if (count >= maxComments) break;
	    }
	    return sb.toString();
	}
	
	@Override
	@CacheResult(cacheName = "posts-cache")
	public List<PostDTO> getAllPosts() {
        Response response = JSONPlaceholderClient.getPosts();
        if (response.getStatus() != 200)
            throw new ExternalException("Error al obtener posts: HTTP " + response.getStatus());
        return response.readEntity(new GenericType<List<PostDTO>>(){});
    }

	@Override
	@CacheResult(cacheName = "user-cache")
    public UserDTO getUserById(int userId) {
        Response response = JSONPlaceholderClient.getUser(userId);
        if (response.getStatus() == 404) 
        	throw new NotFoundException("Usuario con id " + userId + " no encontrado.");
        else if (response.getStatus() >= 400)
            throw new ExternalException("Error externo obteniendo usuario con id " + userId + " : HTTP " + response.getStatus());

        return response.readEntity(UserDTO.class);
    }

    @Override
    @CacheResult(cacheName = "comments-cache")
    public List<CommentDTO> getCommentsByPostId(int postId) {
        Response response = JSONPlaceholderClient.getCommentsByPost(postId);
        if (response.getStatus() == 404) 
        	throw new NotFoundException("Post con id " + postId + " no encontrado.");
        if (response.getStatus() != 200)
            throw new ExternalException("Error externo obteniendo comentarios del post con id" + postId + " : HTTP " + response.getStatus());
        return response.readEntity(new GenericType<List<CommentDTO>>() {});
    }
    
    @Override
    public int deletePost(int postId) {
    	LOG.info("Intentando eliminar post con id: " + postId);
        Response response = JSONPlaceholderClient.deletePost(postId);
        int status = response.getStatus();
        if (status == 404) 
        	throw new NotFoundException("No se elimino el Post con id " + postId + " porque no fue encontrado.");
        else if (status >= 400) 
        	throw new ExternalException("Error externo intentando eliminar el post con id " + postId + " : HTTP " + status);
        LOG.info("Se elimino de forma correcta el post con id: " + postId);
        return status;
    }
   
}	