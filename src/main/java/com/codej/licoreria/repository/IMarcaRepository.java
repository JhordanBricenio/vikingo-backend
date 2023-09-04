package com.codej.licoreria.repository;

import com.codej.licoreria.model.Marca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMarcaRepository extends JpaRepository<Marca, Integer> {
}
