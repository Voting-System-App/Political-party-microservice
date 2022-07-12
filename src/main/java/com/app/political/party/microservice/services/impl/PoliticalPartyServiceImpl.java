package com.app.political.party.microservice.services.impl;

import com.app.political.party.microservice.entities.Adherent;
import com.app.political.party.microservice.entities.PoliticalParty;
import com.app.political.party.microservice.repositories.AdherentRepository;
import com.app.political.party.microservice.repositories.PoliticalPartyRepository;
import com.app.political.party.microservice.services.PoliticalPartyService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PoliticalPartyServiceImpl implements PoliticalPartyService {

    private final PoliticalPartyRepository partyRepository;

    private final AdherentRepository adherentRepository;
    private final String tempDirectory;
    public PoliticalPartyServiceImpl(PoliticalPartyRepository partyRepository, AdherentRepository adherentRepository, @Value("${adherent.directory}") String tempDirectory) {
        this.partyRepository = partyRepository;
        this.adherentRepository = adherentRepository;
        this.tempDirectory = tempDirectory;
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<PoliticalParty> findAll() {
        return partyRepository.findAll();
    }
    @Override
    @Transactional(readOnly = true)
    public Mono<PoliticalParty> findById(String id) {
        return partyRepository.findById(id);
    }

    private Mono<Boolean> saveAdherentFromFile(PoliticalParty party, String path)throws IOException {
        String line = "";
        BufferedReader file = new BufferedReader(new FileReader(tempDirectory+path));
        List<Adherent> adherentList = new ArrayList<>();
        while ((line= file.readLine())!=null){
            String[] data = line.split(";",-1);
            Adherent adherent = new Adherent();
            adherent.setName(data[0]);
            adherent.setLastName(data[1]);
            adherent.setStatus(!data[2].isEmpty());
            adherent.setPoliticalParty(party);
            adherentList.add(adherent);
        }
        Boolean status = adherentList.stream().anyMatch(adherent -> !adherent.getStatus());
        adherentList.forEach(adherent->adherent.setStatus(status));
        return adherentRepository.saveAll(adherentList).then(Mono.just(status));
    }
    @Override
    @Transactional
    public Mono<PoliticalParty> save(PoliticalParty politicalParty){
        politicalParty.setStatus(false);
        return partyRepository.save(politicalParty);
    }

    @Override
    @Transactional
    public Mono<PoliticalParty> update(PoliticalParty politicalParty, String id,String path) {
        Flux<Adherent> adherentFlux = adherentRepository.findByPoliticalParty_Id(id);
        Mono<PoliticalParty> response = partyRepository.findById(id).flatMap(result->{
            result.setDescription(politicalParty.getDescription());
            result.setDate(politicalParty.getDate());
            try {
                return saveAdherentFromFile(result,path).flatMap(status->{
                            result.setStatus(!status);
                            return partyRepository.save(result);
                        });
            } catch (IOException e) {
                return Mono.error(new RuntimeException(e));
            }
        });
        return adherentFlux.hasElements().flatMap(result->result?adherentRepository.deleteAllByPoliticalParty_Id(id).then(response):response);
    }
}
