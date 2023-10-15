package org.lessons.java.spring.controllers;

import java.util.List;

import org.lessons.java.spring.models.Pizza;
import org.lessons.java.spring.models.SpecialOffer;
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

import jakarta.validation.Valid;

@Controller
@RequestMapping("/offers")
public class SpecialOfferController {

	@Autowired
	private SpecialOfferService specialOfferService;

	@Autowired
	private PizzaService pizzaService;

	@GetMapping("/")
	public String index(Model model) {
		List<SpecialOffer> specialOffers = specialOfferService.findAll();
		model.addAttribute("specialOffers", specialOffers);

		return "special_offer/index.html";
	}

	@GetMapping("/{id}")
	public String show(@PathVariable long id, Model model) {
		SpecialOffer specialOffer = specialOfferService.findById(id);
		List<Pizza> freePizzas = freePizzas(pizzaService.findAll(), id);
		List<Pizza> allPizzas = pizzaService.findAll();

		model.addAttribute("specialOffer", specialOffer);
		model.addAttribute("freePizzas", freePizzas);
		model.addAttribute("allPizzas", allPizzas);
		model.addAttribute("stringDTO", new StringDTO());

		return "special_offer/show.html";
	}

	@GetMapping("/create")
	public String create(Model model) {
		model.addAttribute("specialOffer", SpecialOffer.createEmptySpecialOffer());

		return "special_offer/create.html";
	}

	@PostMapping("/store")
	public String store(@Valid @ModelAttribute SpecialOffer specialOffer, BindingResult bindingResult) {
		if (!bindingResult.hasErrors())
			specialOfferService.save(specialOffer);

		return bindingResult.hasErrors() ? "special_offer/create.html" : "redirect:/offers/" + specialOffer.getId();
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable long id, Model model) {
		SpecialOffer specialOffer = specialOfferService.findById(id);

		model.addAttribute("specialOffer", specialOffer);
		model.addAttribute("id", id);
		return "special_offer/edit.html";
	}

	@PostMapping("/update/{id}")
	public String update(@Valid @ModelAttribute SpecialOffer specialOffer, BindingResult bindingResult,
			@PathVariable long id) {

		if (!bindingResult.hasErrors()) {
			SpecialOffer specialOfferToUpdate = specialOfferService.findById(id);
			specialOfferToUpdate.set(specialOffer);
			specialOfferService.save(specialOfferToUpdate);
		}

		return bindingResult.hasErrors() ? "special_offer/edit.html" : "redirect:/offers/" + id;
	}

	@PostMapping("/delete/{id}")
	public String delete(@PathVariable long id) {
		specialOfferService.deleteById(id);

		return "redirect:/offers/";
	}

	@PostMapping("/plug/{id}")
	public String plug(@ModelAttribute StringDTO idsStr, @PathVariable long id) {
		String[] strIds = idsStr.getValue().split(",");
		for (String strId : strIds)
			attach(id, Long.parseLong(strId));

		return "redirect:/offers/" + id;
	}

	@PostMapping("/unplug/{id}")
	public String unplug(@ModelAttribute StringDTO idsStr, @PathVariable long id) {
		String[] strIds = idsStr.getValue().split(",");
		for (String strId : strIds)
			detach(id, Long.parseLong(strId));

		return "redirect:/offers/" + id;
	}

	private List<Pizza> freePizzas(List<Pizza> pizzas, long id) {
		return pizzas.stream().filter(pizza -> pizza.getSpecialOffer() == null
				|| (pizza.getSpecialOffer() != null && pizza.getSpecialOffer().getId() != id)).toList();
	}

	private void attach(long specialOfferId, long pizzaIdToAttach) {
		Pizza pizza = pizzaService.findById(pizzaIdToAttach);
		pizza.setSpecialOffer(null);
		pizza.setSpecialOffer(specialOfferService.findById(specialOfferId));
		pizzaService.save(pizza);
	}

	private void detach(long specialOfferId, long pizzaIdToDetach) {
		Pizza pizza = pizzaService.findById(pizzaIdToDetach);
		pizza.setSpecialOffer(null);
		pizzaService.save(pizza);
	}
}
