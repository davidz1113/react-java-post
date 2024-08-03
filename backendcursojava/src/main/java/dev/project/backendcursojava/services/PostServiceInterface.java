package dev.project.backendcursojava.services;

import java.util.List;

import dev.project.backendcursojava.shared.dto.PostCreationDto;
import dev.project.backendcursojava.shared.dto.PostDto;

public interface PostServiceInterface {
    
    public PostDto createPost(PostCreationDto postCreationDto);

    public List<PostDto> getLastPosts();

    public PostDto getPost(String postId);

    public void deletePost(String postId, long userId);

    public PostDto updatePost(String postId, long userId, PostCreationDto postUpdateDto);
}
