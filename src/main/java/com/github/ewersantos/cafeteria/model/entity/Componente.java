package com.github.ewersantos.cafeteria.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Componente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, name = "codigoProduto")
    private Integer idProduto;

    @Column(nullable = false, name = "codigoIngrediente")
    private Integer idIngrediente;

    @Column(nullable = false, name = "quantidade")
    private Integer quantidade;
}
