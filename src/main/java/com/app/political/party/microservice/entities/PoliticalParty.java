package com.app.political.party.microservice.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Document(collection = "political_party")
@Data
public class PoliticalParty {
    @Id
    private String id;
    private String name;
    @NotEmpty
    private String description;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date creationDate;
    private Boolean status;
}
