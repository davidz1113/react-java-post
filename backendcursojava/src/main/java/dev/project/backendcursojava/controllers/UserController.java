package dev.project.backendcursojava.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.project.backendcursojava.models.requests.UserDetailRequestModel;
import dev.project.backendcursojava.models.responses.PostResponseModel;
import dev.project.backendcursojava.models.responses.UserDetailResponseModel;
import dev.project.backendcursojava.services.UserServiceInterface;
import dev.project.backendcursojava.shared.dto.PostDto;
import dev.project.backendcursojava.shared.dto.UserDto;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/users") // http://localhost:8080/users
public class UserController {

    @Autowired
    UserServiceInterface userService;

    @Autowired
    ModelMapper mapper;

    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public UserDetailResponseModel getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String email = auth.getPrincipal().toString();

        UserDto userdto = userService.getUser(email);

        UserDetailResponseModel returnValue = mapper.map(userdto, UserDetailResponseModel.class);

        // BeanUtils.copyProperties(userdto, returnValue);

        return returnValue;
    }

    @PostMapping
    public UserDetailResponseModel createUser(@RequestBody UserDetailRequestModel userDetails) {
        UserDetailResponseModel returnValue = new UserDetailResponseModel();

        UserDto userDto = new UserDto();

        BeanUtils.copyProperties(userDetails, userDto);

        UserDto createdUser = userService.createUser(userDto);

        BeanUtils.copyProperties(createdUser, returnValue);

        return returnValue;
    }

    @GetMapping(path = "/posts")
    public List<PostResponseModel> getPosts() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String email = auth.getPrincipal().toString();

        List<PostDto> posts = userService.getUserPosts(email);

        List<PostResponseModel> returnValue = new ArrayList<PostResponseModel>();

        // convertir lista de post dto a post rest
        for (PostDto postDto : posts) {
            PostResponseModel postModel = mapper.map(postDto, PostResponseModel.class);
            if (postModel.getExpiredAt().compareTo(new Date(System.currentTimeMillis())) < 0) {
                postModel.setLapse(true);
            }
            returnValue.add(postModel);
        }

        return returnValue;

    }

}
