package dev.project.backendcursojava.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import dev.project.backendcursojava.shared.dto.PostDto;
import dev.project.backendcursojava.shared.dto.UserDto;

public interface UserServiceInterface extends UserDetailsService {
    public UserDto createUser(UserDto userDto);

    public UserDto getUser(String email);

    public List<PostDto> getUserPosts(String email);
}
