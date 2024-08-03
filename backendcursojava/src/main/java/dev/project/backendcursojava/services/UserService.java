package dev.project.backendcursojava.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import dev.project.backendcursojava.entities.PostEntity;
import dev.project.backendcursojava.entities.UserEntity;
import dev.project.backendcursojava.exceptions.EmailExitsException;
import dev.project.backendcursojava.repository.PostRepositoryInterface;
import dev.project.backendcursojava.repository.UserRepositoryInterface;
import dev.project.backendcursojava.shared.dto.PostDto;
import dev.project.backendcursojava.shared.dto.UserDto;

@Service
public class UserService implements UserServiceInterface {

    @Autowired
    UserRepositoryInterface userRepository;

    @Autowired
    PostRepositoryInterface postRepository;

    @Autowired
    ModelMapper mapper;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDto createUser(UserDto userDto) {
        // Todo logica para el usuario

        if (userRepository.findByEmail(userDto.getEmail()) != null)
            throw new EmailExitsException("Record with the email already exists");

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDto, userEntity);

        userEntity.setUserId("testUserId");
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

        UUID userUuid = UUID.randomUUID();
        userEntity.setUserId(userUuid.toString());

        UserEntity storedUserDetail = userRepository.save(userEntity);

        UserDto userToReturn = new UserDto();
        BeanUtils.copyProperties(storedUserDetail, userToReturn);

        return userToReturn;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        UserEntity userEntity = userRepository.findByEmail(email);

        if (userEntity == null)
            throw new UsernameNotFoundException(email);

        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), true, true, true, true,
                AuthorityUtils.NO_AUTHORITIES);
    }

    @Override
    public UserDto getUser(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);

        if (userEntity == null)
            throw new UsernameNotFoundException(email);

        UserDto userDto = new UserDto();

        BeanUtils.copyProperties(userEntity, userDto);

        return userDto;
    }

    @Override
    public List<PostDto> getUserPosts(String email) {
        // TODO Auto-generated method stub
        UserEntity userEntity = userRepository.findByEmail(email);
        List<PostEntity> posts = postRepository.getByUserIdOrderByCreatedAtDesc(userEntity.getId());

        List<PostDto> postDtos = new ArrayList<PostDto>();

        for (PostEntity post : posts) {
            PostDto postDto = mapper.map(post, PostDto.class);
            postDtos.add(postDto);
        }

        return postDtos;
    }

}
