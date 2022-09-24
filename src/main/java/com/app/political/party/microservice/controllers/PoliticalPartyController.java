package com.app.political.party.microservice.controllers;

import com.app.political.party.microservice.entities.Adherent;
import com.app.political.party.microservice.entities.PoliticalParty;
import com.app.political.party.microservice.services.PoliticalPartyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@Tag(name = "Political Party Api", description = "Api for testing the endpoint for political party")
@RequestMapping("/party")
public class PoliticalPartyController {

    private final PoliticalPartyService partyService;

    private final String tempDirectory;

    public PoliticalPartyController(PoliticalPartyService partyService,@Value("${adherent.directory}") String tempDirectory) {
        this.partyService = partyService;
        this.tempDirectory = tempDirectory;
    }

    @GetMapping
    public ResponseEntity<Flux<PoliticalParty>> findAll(){
        Flux<PoliticalParty> total = partyService.findAll();
        return ResponseEntity.ok(total);
    }
    @GetMapping("/adherents/{id}")
    public ResponseEntity<Flux<Adherent>> findAllAdherents(@PathVariable String id){
        Flux<Adherent> total = partyService.findAllAdherents(id);
        return ResponseEntity.ok(total);
    }
    @GetMapping("/{id}")
    public Mono<ResponseEntity<PoliticalParty>> findById(@PathVariable String id){
        return partyService.findById(id).map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }
    @PostMapping("/save/adherent/list")
    public Mono<String> saveAdherentFile(@RequestPart FilePart file) {
        String tempPath = UUID.randomUUID() + "-" + file.filename()
                .replace(" ","")
                .replace(":","")
                .replace("\\","");
        return file.transferTo(new File(tempDirectory+tempPath)).thenReturn(tempPath);
    }
    @PostMapping
    public ResponseEntity<Mono<PoliticalParty>> save(@RequestBody PoliticalParty party) throws IOException {
        return ResponseEntity.ok(partyService.save(party));
    }
    @PutMapping("/{id}")
    public Mono<ResponseEntity<PoliticalParty>> update(@RequestBody PoliticalParty party,@PathVariable String id){
        return partyService.update(party,id).map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}/file/path/{path}")
    public Mono<ResponseEntity<PoliticalParty>> updateAdherentStatus(@PathVariable String id,@PathVariable String path){
        return partyService.updateAdherentStatus(id,path).map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<String>> delete(@PathVariable String id){
        return partyService.delete(id).map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
