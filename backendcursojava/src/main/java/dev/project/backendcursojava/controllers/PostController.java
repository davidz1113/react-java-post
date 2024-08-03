package dev.project.backendcursojava.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.project.backendcursojava.models.requests.PostCreateRequestModel;
import dev.project.backendcursojava.models.responses.OperationStatusResponseModel;
import dev.project.backendcursojava.models.responses.PostResponseModel;
import dev.project.backendcursojava.services.PostServiceInterface;
import dev.project.backendcursojava.services.UserServiceInterface;
import dev.project.backendcursojava.shared.dto.PostCreationDto;
import dev.project.backendcursojava.shared.dto.PostDto;
import dev.project.backendcursojava.shared.dto.UserDto;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    PostServiceInterface postServiceInterface;

    @Autowired
    ModelMapper mapper;

    @Autowired
    UserServiceInterface userServiceInterface;

    @PostMapping
    public PostResponseModel createPost(@RequestBody PostCreateRequestModel createRequestModel) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String emaString = authentication.getPrincipal().toString();

        PostCreationDto postCreationDto = mapper.map(createRequestModel, PostCreationDto.class);
        postCreationDto.setUserEmail(emaString);

        PostDto postDto = postServiceInterface.createPost(postCreationDto);

        PostResponseModel postToReturn = mapper.map(postDto, PostResponseModel.class);

        if (postToReturn.getExpiredAt().compareTo(new Date(System.currentTimeMillis())) < 0) {
            postToReturn.setLapse(true);
        }

        return postToReturn;
    }

    @GetMapping(path = "/last")
    public List<PostResponseModel> lastPosts() {
        List<PostDto> posts = postServiceInterface.getLastPosts();

        List<PostResponseModel> postsToReturn = new ArrayList<>();

        for (PostDto postDto : posts) {
            PostResponseModel postResponseModel = mapper.map(postDto, PostResponseModel.class);
            postsToReturn.add(postResponseModel);
        }

        return postsToReturn;
    }

    @GetMapping(path = "/{id}")
    public PostResponseModel getPost(@PathVariable String id) {

        PostDto postDto = postServiceInterface.getPost(id);

        PostResponseModel postResponseModel = mapper.map(postDto, PostResponseModel.class);

        if (postResponseModel.getExpiredAt().compareTo(new Date(System.currentTimeMillis())) < 0) {
            postResponseModel.setLapse(true);
        }

        if (postResponseModel.getExposure().getId() == 1 || postResponseModel.getLapse()) { // cuando es privado
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getPrincipal().toString();

            UserDto userDto = userServiceInterface.getUser(email);
            if (userDto.getId() != postDto.getUser().getId()) {
                throw new RuntimeException("No tienes permisos para ver este post");
            }

        }

        return postResponseModel;
    }

    @DeleteMapping(path = "/{id}")
    public OperationStatusResponseModel deletePost(@PathVariable String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userServiceInterface.getUser(authentication.getPrincipal().toString());

        OperationStatusResponseModel oResponseModel = new OperationStatusResponseModel();
        oResponseModel.setName("DELETE");
        postServiceInterface.deletePost(id, user.getId());
        oResponseModel.setResult("SUCCESS");

        return oResponseModel;
    }

    @PutMapping(path = "/{id}")
    public PostResponseModel updatePost(@PathVariable String id,
            @RequestBody PostCreateRequestModel postCreateRequestModel) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userServiceInterface.getUser(authentication.getPrincipal().toString());

        PostCreationDto postUpdateDto = mapper.map(postCreateRequestModel, PostCreationDto.class);

        PostDto postDto = postServiceInterface.updatePost(id, user.getId(), postUpdateDto);
        PostResponseModel postResponseModel = mapper.map(postDto, PostResponseModel.class);

        return postResponseModel;
    }

}
