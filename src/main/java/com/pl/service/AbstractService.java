package com.pl.service;

import com.pl.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
abstract class AbstractService<R extends JpaRepository<EntityType, Long>, EntityType> {
    protected final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    protected EntityType findEntity(R repository, long id) {
        return repository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("Id not found");
                    return new NotFoundException("Not found with given id " + id);
                });
    }
}
