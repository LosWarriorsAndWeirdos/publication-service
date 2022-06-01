package com.acme.publicacion.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "publication")
@Data @NoArgsConstructor @AllArgsConstructor
public class Publication {
    @Id
    @GeneratedValue
    private long Id;

    @NotNull(message = "El nombre no debe estar vacío.")
    private String title;
    private String body;
    private Date createdAt;
    private Float budget;

}
