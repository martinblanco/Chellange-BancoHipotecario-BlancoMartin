package com.chellange.exception.mapper;

import org.jboss.logging.Logger;
import com.chellange.exception.ExternalException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ExternalExceptionMapper implements ExceptionMapper<ExternalException> {
	private static final Logger LOG = Logger.getLogger(NotFoundExceptionMapper.class);
	
    @Override
    public Response toResponse(ExternalException ex) {
    	LOG.error("Error al consumir API externa: " + ex.getMessage());
        return Response.status(Response.Status.BAD_GATEWAY).entity(ex.getMessage()).build();
    }
}