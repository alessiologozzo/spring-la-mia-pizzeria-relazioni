package org.lessons.java.spring;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.lessons.java.spring.models.Pizza;
import org.lessons.java.spring.models.SpecialOffer;
import org.lessons.java.spring.services.PizzaService;
import org.lessons.java.spring.services.SpecialOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringLaMiaPizzeriaCrudApplication implements CommandLineRunner {

	@Autowired
	private PizzaService pizzaService;

	@Autowired
	private SpecialOfferService specialOfferService;

	public static void main(String[] args) {
		SpringApplication.run(SpringLaMiaPizzeriaCrudApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		List<SpecialOffer> specialOffers = Arrays.asList(
				new SpecialOffer("MAXI Offerta!", 75, LocalDate.now(), LocalDate.now().plusDays(3)),
				new SpecialOffer("Sottocosto", 30, LocalDate.now(), LocalDate.now().plusDays(24)));
		specialOfferService.saveAll(specialOffers);
		
		List<Pizza> pizzas = Arrays.asList(
				new Pizza("Pizza Margherita", "Pomodoro, fior di latte, basilico fresco, sale, olio",
						"/images/margherita.jpg", 6f, specialOffers.get(0)),
				new Pizza("Pizza Quattro Formaggi",
						"Farina, sale, olio, pomodoro, mozzarella, gorgonzola, fontina, parmigiano reggiano",
						"/images/quattro-formaggi.jpg", 7.5f, specialOffers.get(1)),
				new Pizza("Pizza Capricciosa", "Pomodoro, mozzarella, prosciutto cotto, funghi, olive, carciofini",
						"/images/capricciosa.jpg", 8.5f, specialOffers.get(1)),
				new Pizza("Pizza Tonno e Cipolla", "Farina, sale, olio, pomodoro, mozzarella, tonno, cipolla",
						"/images/tonno-cipolla.jpg", 9f, null),
				new Pizza("Pizza ai Funghi", "Pomodoro, farina, sale, olio, fior di latte, funghi",
						"/images/funghi.jpg", 8.5f, null),
				new Pizza("Pizza al Salmone", "Pomodoro, farina, sale, olio, fior di latte, salmone",
						"/images/salmone.webp", 9f, specialOffers.get(0)),
				new Pizza("Pizza alla Marinara", "Farina, pomodoro, olio, aglio, origano", "/images/marinara.jpg", 7.f, null),
				new Pizza("Pizza Quattro Stagioni",
						"Pomodoro, olio, origano, funghi, basilico, prosciutto cotto, olive, fior di latte, salame",
						"/images/quattro_stagioni.jpg", 9.5f, specialOffers.get(1)));
		pizzaService.saveAll(pizzas);
	}
}
