package com.app.political.party.microservice.services;

import com.app.political.party.microservice.entities.PoliticalParty;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;

public interface PoliticalPartyService {
    Flux<PoliticalParty> findAll();
    Mono<PoliticalParty> findById(String id);
    Mono<PoliticalParty> save(PoliticalParty politicalParty) throws IOException;
    Mono<PoliticalParty> update(PoliticalParty politicalParty,String id,String path);
}
