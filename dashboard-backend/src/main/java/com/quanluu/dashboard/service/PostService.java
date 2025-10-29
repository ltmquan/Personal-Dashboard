package com.quanluu.dashboard.service;

import com.quanluu.dashboard.model.dto.PostDTO;

import java.util.List;
import java.util.Optional;

public interface PostService {

    List<PostDTO> getAllPosts();

    Optional<PostDTO> getPostById(Long id);

    PostDTO createPost(PostDTO postDTO);

    PostDTO updatePost(Long id, PostDTO postDTO);

    void deletePost(Long id);
}
