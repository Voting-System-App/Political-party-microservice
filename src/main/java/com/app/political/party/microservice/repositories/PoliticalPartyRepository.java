package com.app.political.party.microservice.repositories;

import com.app.political.party.microservice.entities.PoliticalParty;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PoliticalPartyRepository extends ReactiveMongoRepository<PoliticalParty,String> {
}
