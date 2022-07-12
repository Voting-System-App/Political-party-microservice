package com.app.political.party.microservice.services;

import com.app.political.party.microservice.entities.Candidate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CandidateService {
    Flux<Candidate> findAll();
    Flux<Candidate> findByPoliticalParty(String id);
    Mono<Candidate> findById(String id);
    Mono<Candidate> save(Candidate candidate);
    Mono<Candidate> update(Candidate candidate,String id);
    Mono<Void> delete(String id);
}
