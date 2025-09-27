package com.chellange.exception.mapper;

import org.jboss.logging.Logger;
import com.chellange.exception.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {
	private static final Logger LOG = Logger.getLogger(NotFoundExceptionMapper.class);
    
    @Override
    public Response toResponse(NotFoundException exception) {
    	LOG.warn("Recurso no encontrado (404 Not Found): " + exception.getMessage());
        return Response.status(Response.Status.NOT_FOUND).entity(exception.getMessage()).build();
    }
}