package com.app.political.party.microservice.repositories;

import com.app.political.party.microservice.entities.Adherent;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdherentRepository extends ReactiveMongoRepository<Adherent,String> {
}
