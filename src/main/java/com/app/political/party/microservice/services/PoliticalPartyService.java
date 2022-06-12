package com.app.political.party.microservice.services;

import com.app.political.party.microservice.entities.PoliticalParty;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PoliticalPartyService {
    Flux<PoliticalParty> findAll();
    Flux<PoliticalParty> findById(String id);
    Mono<PoliticalParty> save(PoliticalParty politicalParty);
    Mono<PoliticalParty> update(PoliticalParty politicalParty,String id);
}
