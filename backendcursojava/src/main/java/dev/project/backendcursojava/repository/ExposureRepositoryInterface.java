package dev.project.backendcursojava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import dev.project.backendcursojava.entities.ExposureEntity;

@Repository
public interface ExposureRepositoryInterface extends CrudRepository<ExposureEntity, Long> {

}
