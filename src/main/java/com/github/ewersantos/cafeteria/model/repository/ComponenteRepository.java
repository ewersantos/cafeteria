package com.github.ewersantos.cafeteria.model.repository;

import com.github.ewersantos.cafeteria.model.entity.Componente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComponenteRepository extends JpaRepository<Componente, Integer> {
}
