package com.app.political.party.microservice.services;

import com.app.political.party.microservice.entities.Candidate;
import com.app.political.party.microservice.entities.PoliticalParty;
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
public class CandidateTestController {
    @Autowired
    private WebTestClient webClient;
    @MockBean
    private CandidateService candidateService;
    private static Candidate candidateMock = new Candidate();
    private static PoliticalParty partyMock = new PoliticalParty();
    private final static String ID = "1";
    private final static String NAME = "Walter";
    private final static String LAST_NAME = "Molina";
    private final static String EMAIL = "w7asdd@gmail.com";
    private final static String DNI = "71984678";
    private final static Boolean GENDER = true;

    @BeforeAll
    static void setUp(){
        candidateMock.setId(ID);
        candidateMock.setName(NAME);
        candidateMock.setLastName(LAST_NAME);
        candidateMock.setEmail(EMAIL);
        candidateMock.setDni(DNI);
        candidateMock.setGender(GENDER);
        candidateMock.setPoliticalParty(partyMock);
    }
    @Test
    @DisplayName("Find the candidate by id")
    public void findById(){
        when(candidateService.findById(ID)).thenReturn(Mono.just(candidateMock));
        webClient.get()
                .uri("/candidate/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Candidate.class)
                .value(data -> data.getId(), equalTo("1"));
    }
    @Test
    @DisplayName("Delete a candidate by id")
    public void deleteById(){
        when(candidateService.delete(ID)).thenReturn(Mono.empty());
        webClient.delete()
                .uri("/candidate/1")
                .exchange()
                .expectStatus().isNotFound();
    }
    @Test
    @DisplayName("Create a candidate")
    public void create(){
        when(candidateService.save(candidateMock)).thenReturn(Mono.just(candidateMock));
        webClient.post()
                .uri("/candidate")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(candidateMock), Candidate.class)
                .exchange()
                .expectStatus().isOk();
    }
    @Test
    @DisplayName("Update a candidate")
    public void update(){
        when(candidateService.update(candidateMock,ID)).thenReturn(Mono.just(candidateMock));
        webClient.put()
                .uri("/candidate/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(candidateMock), Candidate.class)
                .exchange()
                .expectStatus().isOk();
    }
}
