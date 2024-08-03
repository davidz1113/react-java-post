package dev.project.backendcursojava.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dev.project.backendcursojava.entities.PostEntity;

@Repository
public interface PostRepositoryInterface extends PagingAndSortingRepository<PostEntity, Long> {

    List<PostEntity> getByUserIdOrderByCreatedAtDesc(Long userId);

    PostEntity save(PostEntity postEntity);

    @Query(value = "SELECT * FROM posts p WHERE p.exposure_id = :exposure and p.expired_at > :now ORDER BY p.created_at DESC LIMIT 20", nativeQuery = true)
    List<PostEntity> getLastPublicPosts(@Param("exposure") long exposureId, @Param("now") Date now);

    PostEntity findByPostId(String postId);

    void delete(PostEntity postEntity);
}
