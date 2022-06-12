package com.app.political.party.microservice.services.impl;

import com.app.political.party.microservice.entities.PoliticalParty;
import com.app.political.party.microservice.repositories.PoliticalPartyRepository;
import com.app.political.party.microservice.services.PoliticalPartyService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PoliticalPartyServiceImpl implements PoliticalPartyService {

    private final PoliticalPartyRepository partyRepository;

    public PoliticalPartyServiceImpl(PoliticalPartyRepository partyRepository) {
        this.partyRepository = partyRepository;
    }

    @Override
    public Flux<PoliticalParty> findAll() {
        return partyRepository.findAll();
    }

    @Override
    public Mono<PoliticalParty> findById(String id) {
        return partyRepository.findById(id);
    }

    @Override
    public Mono<PoliticalParty> save(PoliticalParty politicalParty) {
        return partyRepository.save(politicalParty);
    }

    @Override
    public Mono<PoliticalParty> update(PoliticalParty politicalParty, String id) {
        return partyRepository.findById(id).flatMap(result->{
            result.setDescription(politicalParty.getDescription());
            result.setDate(politicalParty.getDate());
            return partyRepository.save(result);
        });
    }
}
