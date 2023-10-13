package org.lessons.java.spring.services;

import java.util.List;
import java.util.Optional;

import org.lessons.java.spring.models.Pizza;
import org.lessons.java.spring.models.SpecialOffer;
import org.lessons.java.spring.repositories.SpecialOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpecialOfferService {
	@Autowired
	private SpecialOfferRepository specialOfferRepository;

	@Autowired
	private PizzaService pizzaService;

	public void save(SpecialOffer specialOffer) {
		specialOfferRepository.save(specialOffer);
	}

	public void saveAll(List<SpecialOffer> specialOffers) {
		specialOfferRepository.saveAll(specialOffers);
	}

	public List<SpecialOffer> findAll() {
		return specialOfferRepository.findAll();
	}

	public SpecialOffer findById(long id) {
		Optional<SpecialOffer> oSpecialOffer = specialOfferRepository.findById(id);
		SpecialOffer specialOffer = null;

		if (oSpecialOffer.isPresent())
			specialOffer = oSpecialOffer.get();

		return specialOffer;
	}

	public void deleteById(long id) {
		List<Pizza> pizzas = pizzasOfSpecialOffer(pizzaService.findAll(), id);

		for (Pizza pizza : pizzas) {
			pizza.setSpecialOffer(null);
			pizzaService.save(pizza);
		}

		specialOfferRepository.deleteById(id);
	}

	private List<Pizza> pizzasOfSpecialOffer(List<Pizza> pizzas, Long specialOfferId) {
		return pizzas.stream()
				.filter(pizza -> pizza.getSpecialOffer() != null && pizza.getSpecialOffer().getId() == specialOfferId)
				.toList();
	}
}
