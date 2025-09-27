package com.ejemplo;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.chellange.controller.JSONPlaceholderClient;
import com.chellange.exception.NotFoundException;
import com.chellange.service.PostServiceImpl;
import com.chellange.exception.ExternalException;

class PostServiceTest {

    @InjectMocks
    PostServiceImpl postService;

    @Mock
    JSONPlaceholderClient client;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        postService.maxPosts = 5;
        postService.maxComments = 4;
    }

    @Test
    void testDeletePostOk() {
        Response mockResponse = mock(Response.class);
        when(mockResponse.getStatus()).thenReturn(200);
        when(client.deletePost(1)).thenReturn(mockResponse);
        int status = postService.deletePost(1);
        assertEquals(200, status);
    }

    @Test
    void testDeletePostNotFound() {
        Response mockResponse = mock(Response.class);
        when(mockResponse.getStatus()).thenReturn(404);
        when(client.deletePost(2)).thenReturn(mockResponse);
        NotFoundException ex = assertThrows(NotFoundException.class, () -> postService.deletePost(2));
        assertTrue(ex.getMessage().contains("no fue encontrado"));
    }

    @Test
    void testDeletePostExternalError() {
        Response mockResponse = mock(Response.class);
        when(mockResponse.getStatus()).thenReturn(500);
        when(client.deletePost(3)).thenReturn(mockResponse);
        ExternalException ex = assertThrows(ExternalException.class, () -> postService.deletePost(3));
        assertTrue(ex.getMessage().contains("HTTP 500"));
    }
}