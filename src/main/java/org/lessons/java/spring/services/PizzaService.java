package org.lessons.java.spring.services;

import java.util.List;
import java.util.Optional;

import org.lessons.java.spring.models.Pizza;
import org.lessons.java.spring.repositories.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PizzaService {
	@Autowired
	private PizzaRepository pizzaRepository;

	public List<Pizza> findAll() {
		return pizzaRepository.findAll();
	}

	public Pizza findById(long id) {
		Optional<Pizza> oPizza = pizzaRepository.findById(id);
		Pizza pizza = null;

		if (oPizza.isPresent())
			pizza = oPizza.get();

		return pizza;
	}

	public void save(Pizza pizza) {
		pizzaRepository.save(pizza);
	}

	public void saveAll(List<Pizza> pizzas) {
		pizzaRepository.saveAll(pizzas);
	}

	public List<Pizza> findByNameContaining(String name) {
		return pizzaRepository.findByNameContaining(name);
	}

	public void deleteById(long id) {
		pizzaRepository.deleteById(id);
	}
}
