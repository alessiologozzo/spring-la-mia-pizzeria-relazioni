package org.lessons.java.spring.controllers;

import java.util.List;

import org.lessons.java.spring.models.Ingredient;
import org.lessons.java.spring.models.Pizza;
import org.lessons.java.spring.services.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/ingredients")
public class IngredientController {
	@Autowired
	private IngredientService ingredientService;

	@GetMapping("/")
	public String index(Model model) {
		List<Ingredient> ingredients = ingredientService.findAll();
		model.addAttribute("ingredients", ingredients);

		return "ingredient/index.html";
	}

	@GetMapping("/{id}")
	public String show(@PathVariable long id, Model model) {
		Ingredient ingredient = ingredientService.findById(id);
		model.addAttribute("ingredient", ingredient);

		return "ingredient/show.html";
	}

	@GetMapping("/create")
	public String create(Model model) {
		model.addAttribute("ingredient", Ingredient.createEmptyIngredient());

		return "ingredient/create.html";
	}

	@PostMapping("/store")
	public String store(@Valid @ModelAttribute Ingredient ingredient, BindingResult bindingResult, Model model) {
		if (!bindingResult.hasErrors())
			ingredientService.save(ingredient);

		return bindingResult.hasErrors() ? "ingredient/create.html" : "redirect:/ingredients/" + ingredient.getId();
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable long id, Model model) {
		model.addAttribute("ingredient", ingredientService.findById(id));

		return "ingredient/edit.html";
	}

	@PostMapping("/update/{id}")
	public String update(@Valid @ModelAttribute Ingredient ingredient, BindingResult bindingResult,
			@PathVariable long id, Model model) {
		if (!bindingResult.hasErrors()) {
			Ingredient ingredientToUpdate = ingredientService.findById(id);
			ingredientToUpdate.setName(ingredient.getName());
			ingredientService.save(ingredientToUpdate);
		}

		return bindingResult.hasErrors() ? "ingredient.edit.html" : "redirect:/ingredients/" + id;
	}

	@PostMapping("/delete/{id}")
	public String delete(@PathVariable long id) {
		Ingredient ingredient = ingredientService.findById(id);
		List<Pizza> pizzas = ingredient.getPizzas();

		for (Pizza pizza : pizzas)
			pizza.removeIngredient(id);

		ingredientService.deleteById(id);

		return "redirect:/ingredients/";
	}
}
