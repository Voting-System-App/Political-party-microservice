package com.app.political.party.microservice.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Document(collection = "political_party")
@Data
public class PoliticalParty {
    @Id
    private String id;
    @NotEmpty
    private String description;
    @NotEmpty
    private String date;
    private Boolean status;
}
