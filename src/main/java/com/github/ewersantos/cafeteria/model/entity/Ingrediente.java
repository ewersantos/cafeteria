package com.github.ewersantos.cafeteria.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
public class Ingrediente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 150, name = "nome")
    private String nome;

    @Column(name = "precoUnitario")
    private BigDecimal precoUnitario;

    @Column(name = "unidadeMedida")
    private String unidadeMedida;

    @Column(name = "quantidade")
    private String quantidade;

    @Column
    @Lob
    private byte[] imagem;
}
