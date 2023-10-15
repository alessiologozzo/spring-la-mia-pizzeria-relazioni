package org.lessons.java.spring.controllers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.lessons.java.spring.models.Ingredient;
import org.lessons.java.spring.models.Pizza;
import org.lessons.java.spring.models.SpecialOffer;
import org.lessons.java.spring.services.IngredientService;
import org.lessons.java.spring.services.PizzaService;
import org.lessons.java.spring.services.SpecialOfferService;
import org.lessons.java.spring.utils.StringDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pizzas")
public class PizzaController {
	@Autowired
	private PizzaService pizzaService;

	@Autowired
	private SpecialOfferService specialOfferService;

	@Autowired
	private IngredientService ingredientService;

	@GetMapping("/")
	public String index(Model model, @RequestParam(required = false) String name,
			@RequestParam(required = false) String orderBy, @RequestParam(required = false) Long specialOfferId) {
		List<Pizza> pizzas = null;
		boolean search = false, isEmpty = false;
		String oldOrderBy = "default";

		if (name == null || name.equals(""))
			pizzas = pizzaService.findAll();
		else {
			pizzas = pizzaService.findByNameContaining(name);
			search = true;
		}

		if (pizzas == null || pizzas.size() < 1)
			isEmpty = true;

		if (orderBy != null) {
			oldOrderBy = orderBy;

			if (orderBy.equals("asc"))
				pizzas = orderPizzasAsc(pizzas);

			else if (orderBy.equals("desc"))
				pizzas = orderPizzasDesc(pizzas);
		}

		if (specialOfferId != null && specialOfferId > 0)
			pizzas = pizzasOfSpecialOffer(pizzas, specialOfferId);

		List<SpecialOffer> specialOffers = specialOfferService.findAll();

		model.addAttribute("pizzas", pizzas);
		model.addAttribute("isEmpty", isEmpty);
		model.addAttribute("search", search);
		model.addAttribute("oldName", name);
		model.addAttribute("oldOrderBy", oldOrderBy);
		model.addAttribute("specialOffers", specialOffers);
		model.addAttribute("oldSpecialOfferId", specialOfferId);

		return "pizza/index.html";
	}

	@GetMapping("/{id}")
	public String show(@PathVariable long id, Model model) {
		model.addAttribute("pizza", pizzaService.findById(id));

		return "pizza/show.html";
	}

	@GetMapping("/create")
	public String create(Model model) {
		List<Ingredient> ingredients = ingredientService.findAll();

		model.addAttribute("pizza", Pizza.createEmptyPizza());
		model.addAttribute("ingredients", ingredients);
		model.addAttribute("stringDTO", new StringDTO());

		return "pizza/create.html";
	}

	@PostMapping("/store")
	public String store(@Valid @ModelAttribute Pizza pizza, BindingResult bindingResult, Model model,
			@ModelAttribute StringDTO stringDTO) {

		if (!bindingResult.hasErrors()) {
			List<Ingredient> ingredients = new ArrayList<>();
			String[] strIngredients = stringDTO.getValue().replace(" ", "").split(",");

			if (strIngredients.length == 1 && strIngredients[0].isBlank()) {
				ingredients = null;
				strIngredients = new String[0];
			}
			for (int i = 0; i < strIngredients.length; i++)
				ingredients.add(ingredientService.findByName(strIngredients[i]));

			pizza.setIngredients(ingredients);
			pizzaService.save(pizza);
		}

		return bindingResult.hasErrors() ? "pizza/create.html" : "redirect:/pizzas/" + pizza.getId();
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable long id, Model model) {
		Pizza pizza = pizzaService.findById(id);
		List<Ingredient> ingredients = ingredientService.findAll();

		model.addAttribute("pizza", pizza);
		model.addAttribute("ingredients", ingredients);
		model.addAttribute("stringDTO", new StringDTO());

		return "pizza/edit.html";
	}

	@PostMapping("/update/{id}")
	public String update(@Valid @ModelAttribute Pizza pizza, BindingResult bindingResult, @PathVariable long id,
			Model model, @ModelAttribute StringDTO stringDTO) {

		if (!bindingResult.hasErrors()) {
			Pizza pizzaToUpdate = pizzaService.findById(id);
			pizzaToUpdate.setName(pizza.getName());
			pizzaToUpdate.setPrice(pizza.getPrice());

			List<Ingredient> ingredients = new ArrayList<>();
			String[] strIngredients = stringDTO.getValue().split(",");

			if (strIngredients.length == 1 && strIngredients[0].isBlank()) {
				ingredients = null;
				strIngredients = new String[0];
			}
			for (int i = 0; i < strIngredients.length; i++)
				ingredients.add(ingredientService.findByName(strIngredients[i].strip()));

			pizzaToUpdate.setIngredients(ingredients);
			pizzaService.save(pizzaToUpdate);
		}

		return bindingResult.hasErrors() ? "pizza/edit.html" : "redirect:/pizzas/" + id;
	}

	@PostMapping("/delete/{id}")
	public String delete(@PathVariable long id) {
		pizzaService.deleteById(id);
		return "redirect:/pizzas/";
	}

	private List<Pizza> orderPizzasAsc(List<Pizza> pizzas) {
		return pizzas.stream().sorted(Comparator.comparingDouble(Pizza::getPrice)).toList();
	}

	private List<Pizza> orderPizzasDesc(List<Pizza> pizzas) {
		return pizzas.stream().sorted(Comparator.comparingDouble(Pizza::getPrice).reversed()).toList();
	}

	private List<Pizza> pizzasOfSpecialOffer(List<Pizza> pizzas, Long specialOfferId) {
		return pizzas.stream()
				.filter(pizza -> pizza.getSpecialOffer() != null && pizza.getSpecialOffer().getId() == specialOfferId)
				.toList();
	}
}
