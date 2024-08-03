package dev.project.backendcursojava.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.project.backendcursojava.entities.ExposureEntity;
import dev.project.backendcursojava.entities.PostEntity;
import dev.project.backendcursojava.entities.UserEntity;
import dev.project.backendcursojava.repository.ExposureRepositoryInterface;
import dev.project.backendcursojava.repository.PostRepositoryInterface;
import dev.project.backendcursojava.repository.UserRepositoryInterface;
import dev.project.backendcursojava.shared.dto.PostCreationDto;
import dev.project.backendcursojava.shared.dto.PostDto;

@Service
public class PostService implements PostServiceInterface {

    @Autowired
    PostRepositoryInterface postRepository;

    @Autowired
    UserRepositoryInterface userRepository;

    @Autowired
    ModelMapper mapper;

    @Autowired
    ExposureRepositoryInterface exposureRepository;

    @Override
    public PostDto createPost(PostCreationDto postCreationDto) {
        // postRepository.save(null);
        UserEntity userEntity = userRepository.findByEmail(postCreationDto.getUserEmail());
        Optional<ExposureEntity> exposureEntity = exposureRepository.findById(postCreationDto.getExposureId());

        PostEntity postEntity = new PostEntity();
        postEntity.setUser(userEntity);
        postEntity.setExposure(exposureEntity.get());
        postEntity.setTitle(postCreationDto.getTitle());
        postEntity.setContent(postCreationDto.getContent());
        postEntity.setPostId(UUID.randomUUID().toString());
        postEntity.setExpiredAt(new Date(System.currentTimeMillis() + (postCreationDto.getExpirationTime() * 60000)));

        PostEntity createdPost = postRepository.save(postEntity);

        PostDto postDtoReturn = mapper.map(createdPost, PostDto.class);

        return postDtoReturn;
    }

    @Override
    public List<PostDto> getLastPosts() {
        long publicExposureId = 2;

        List<PostEntity> postEntities = postRepository.getLastPublicPosts(publicExposureId,
                new Date(System.currentTimeMillis()));

        List<PostDto> postDtos = new ArrayList<>();

        for (PostEntity postEntity : postEntities) {
            PostDto postDto = mapper.map(postEntity, PostDto.class);
            postDtos.add(postDto);
        }

        return postDtos;
    }

    @Override
    public PostDto getPost(String postId) {
        PostEntity postEntity = postRepository.findByPostId(postId);
        PostDto postDto = mapper.map(postEntity, PostDto.class);

        return postDto;
    }

    @Override
    public void deletePost(String postId, long userId) {
        PostEntity postEntity = postRepository.findByPostId(postId);

        if(postEntity == null){
            throw new RuntimeException("No se ha encontrado el post");
        }

        if (postEntity.getUser().getId() != userId) {
            throw new RuntimeException("No puedes borrar un post que no es tuyo");
        }

        postRepository.delete(postEntity);

    }

    @Override
    public PostDto updatePost(String postId, long userId, PostCreationDto postUpdateDto) {
        PostEntity postEntity = postRepository.findByPostId(postId);

        if(postEntity == null){
            throw new RuntimeException("No se ha encontrado el post");
        }

        if (postEntity.getUser().getId() != userId) {
            throw new RuntimeException("No puedes actualizar un post que no es tuyo");
        }

        ExposureEntity exposureEntity = exposureRepository.findById(postUpdateDto.getExposureId()).get();

        postEntity.setExposure(exposureEntity);
        postEntity.setTitle(postUpdateDto.getTitle());
        postEntity.setContent(postUpdateDto.getContent());
        postEntity.setExpiredAt(new Date(System.currentTimeMillis() + (postUpdateDto.getExpirationTime() * 60000)));

        PostEntity updatePost = postRepository.save(postEntity);

        PostDto postDto = mapper.map(updatePost, PostDto.class);

        return postDto;
    }

}
