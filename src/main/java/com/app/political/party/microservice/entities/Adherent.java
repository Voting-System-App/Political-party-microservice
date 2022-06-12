package com.app.political.party.microservice.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotEmpty;

@Document(collection = "adherent")
@Data
public class Adherent {
    @Id
    private String id;
    @NotEmpty
    private String name;
    @NotEmpty
    @Field(name = "last_name")
    private String lastName;
    private Boolean status;
}
