package com.github.ewersantos.cafeteria.model.repository;

import com.github.ewersantos.cafeteria.model.entity.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredienteRepository extends JpaRepository<Ingrediente, Integer> {
}
