package org.lessons.java.spring.services;

import java.util.List;
import java.util.Optional;

import org.lessons.java.spring.models.Ingredient;
import org.lessons.java.spring.repositories.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IngredientService {
	@Autowired
	private IngredientRepository ingredientRepository;
	
	public void save(Ingredient ingredient) {
		ingredientRepository.save(ingredient);
	}
	
	public void saveAll(List<Ingredient> ingredients) {
		ingredientRepository.saveAll(ingredients);
	}
	
	public Ingredient findById(long id) {
		Ingredient ingredient = null;
		Optional<Ingredient> oIngredient = ingredientRepository.findById(id);
		
		if(oIngredient.isPresent())
			ingredient = oIngredient.get();
		
		return ingredient;
	}
	
	public List<Ingredient> findAll() {
		return ingredientRepository.findAll();
	}
	
	public Ingredient findByName(String name) {
		return ingredientRepository.findByName(name);
	}
	
	public void deleteById(long id) {
		ingredientRepository.deleteById(id);
	}
}
