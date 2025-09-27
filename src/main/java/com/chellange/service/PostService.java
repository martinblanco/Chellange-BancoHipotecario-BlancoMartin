package com.chellange.service;

import java.util.List;
import com.chellange.dto.CommentDTO;
import com.chellange.dto.PostDTO;
import com.chellange.dto.UserDTO;

public interface PostService {
	int deletePost(int postId);
	String getPosts();
	List<PostDTO> getAllPosts();
	String getCompletePost(PostDTO post);
	UserDTO getUserById(int userId);
	List<CommentDTO> getCommentsByPostId(int postId);
}