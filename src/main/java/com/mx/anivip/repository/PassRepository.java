package com.mx.anivip.repository;

import com.mx.anivip.domain.Pass;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Pass entity.
 */
@SuppressWarnings("unused")
public interface PassRepository extends MongoRepository<Pass,String> {

}
