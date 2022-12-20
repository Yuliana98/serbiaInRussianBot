package com.backend.serbiaInRussianBot.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "catalog")
public class Catalog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CITY")
    private String city;

    @Column(name = "TYPE")
    private String type; //(service/product)

    /*@Column(name = "SERVICE")
    private String service;

    @Column(name = "PRODUCT")
    private String product;*/

    @Column(name = "PRICE")
    private BigDecimal price; //rsd

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "PHONE")
    private BigDecimal phone;

}