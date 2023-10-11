package org.lessons.java.spring.repositories;

import java.util.List;

import org.lessons.java.spring.models.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzaRepository extends JpaRepository<Pizza, Long> {

	public List<Pizza> findByNameContaining(String name);
}