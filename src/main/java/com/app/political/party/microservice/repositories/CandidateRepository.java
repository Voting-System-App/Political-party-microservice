package com.app.political.party.microservice.repositories;

import com.app.political.party.microservice.entities.Candidate;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface CandidateRepository extends ReactiveMongoRepository<Candidate,String> {
    Flux<Candidate> findByPoliticalParty_Id(String id);
}
