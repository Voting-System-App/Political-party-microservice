package com.app.political.party.microservice.services;

import com.app.political.party.microservice.entities.Adherent;
import com.app.political.party.microservice.entities.PoliticalParty;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;

public interface PoliticalPartyService {
    Flux<PoliticalParty> findAll();
    Flux<Adherent> findAllAdherents(String id);
    Mono<PoliticalParty> findById(String id);
    Mono<PoliticalParty> save(PoliticalParty politicalParty);
    Mono<PoliticalParty> update(PoliticalParty politicalParty,String id);
    Mono<PoliticalParty> updateAdherentStatus(String id,String path);
    Mono<String> delete(String id);
}
