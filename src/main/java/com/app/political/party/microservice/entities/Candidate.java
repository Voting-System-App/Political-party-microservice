package com.app.political.party.microservice.entities;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Document(collection = "candidate")
@Data
public class Candidate {
    @Id
    private String id;
    @NotEmpty
    private String name;
    @NotEmpty
    @Field(name = "last_name")
    private String lastName;
    @Email
    private String email;
    @Size(min = 8,max =8)
    private String dni;
    private Boolean gender;
    private Integer age;
    @ManyToOne
    private PoliticalParty politicalParty;
}
