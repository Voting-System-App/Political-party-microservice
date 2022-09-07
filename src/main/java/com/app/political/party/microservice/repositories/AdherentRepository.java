package com.app.political.party.microservice.repositories;

import com.app.political.party.microservice.entities.Adherent;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface AdherentRepository extends ReactiveMongoRepository<Adherent,String> {
    Flux<Adherent> findAllByPoliticalParty_Id(String id);
    Mono<Void> deleteAllByPoliticalParty_Id(String id);
}
