package com.github.ewersantos.cafeteria.model.repository;

import com.github.ewersantos.cafeteria.model.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

}
