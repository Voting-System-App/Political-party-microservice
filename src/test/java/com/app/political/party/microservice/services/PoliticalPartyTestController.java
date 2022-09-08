package com.app.political.party.microservice.services;

import com.app.political.party.microservice.controllers.PoliticalPartyController;
import com.app.political.party.microservice.entities.PoliticalParty;
import com.app.political.party.microservice.repositories.PoliticalPartyRepository;
import com.app.political.party.microservice.services.impl.PoliticalPartyServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "20000")
public class PoliticalPartyTestController {
    @Autowired
    private WebTestClient webClient;
    @MockBean
    private PoliticalPartyService politicalPartyService;
    private static PoliticalParty partyMock = new PoliticalParty();
    private final static String ID = "1";
    private final static String NAME = "Fuerza Popular";
    private final static String DESCRIPTION = "Partido para testing";
    private final static Boolean STATUS = false;

    @BeforeAll
    static void setUp(){
        partyMock.setId(ID);
        partyMock.setName(NAME);
        partyMock.setDescription(DESCRIPTION);
        partyMock.setStatus(STATUS);
    }
    @Test
    @DisplayName("Find the political party by id")
    public void findById(){
        when(politicalPartyService.findById(ID)).thenReturn(Mono.just(partyMock));
        webClient.get()
                .uri("/party/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(PoliticalParty.class)
                .value(data -> data.getId(), equalTo("1"));
    }
    @Test
    @DisplayName("Delete a political party by id")
    public void deleteById(){
        when(politicalPartyService.delete(ID)).thenReturn(Mono.empty());
        webClient.delete()
                .uri("/party/1")
                .exchange()
                .expectStatus().isNotFound();
    }
    @Test
    @DisplayName("Create a political party")
    public void create(){
        when(politicalPartyService.save(partyMock)).thenReturn(Mono.just(partyMock));
        webClient.post()
                .uri("/party")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(partyMock), PoliticalParty.class)
                .exchange()
                .expectStatus().isOk();
    }
    @Test
    @DisplayName("Update a political party")
    public void update(){
        when(politicalPartyService.update(partyMock,ID)).thenReturn(Mono.just(partyMock));
        webClient.put()
                .uri("/party/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(partyMock), PoliticalParty.class)
                .exchange()
                .expectStatus().isOk();
    }
}
