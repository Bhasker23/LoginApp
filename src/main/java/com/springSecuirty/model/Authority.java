package com.springSecuirty.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer authID;
    private String name;

    @JsonIgnore
    @ManyToOne
    private Customer customer;
}
