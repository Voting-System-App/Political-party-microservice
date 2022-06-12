package com.app.political.party.microservice.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "adherent")
@Data
public class Adherent {
    @Id
    private String id;
    private String name;
    private String lastName;
    private Boolean status;
}
