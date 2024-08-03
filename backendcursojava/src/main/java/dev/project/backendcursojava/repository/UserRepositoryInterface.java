package dev.project.backendcursojava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import dev.project.backendcursojava.entities.UserEntity;

@Repository
public interface UserRepositoryInterface extends CrudRepository<UserEntity, Long> {

    UserEntity findByEmail(String email);
}
