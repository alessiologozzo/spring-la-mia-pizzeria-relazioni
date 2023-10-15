package org.lessons.java.spring;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.lessons.java.spring.models.Ingredient;
import org.lessons.java.spring.models.Pizza;
import org.lessons.java.spring.models.SpecialOffer;
import org.lessons.java.spring.services.IngredientService;
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

	@Autowired
	private IngredientService ingredientService;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringLaMiaPizzeriaCrudApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception { //    ingredients.get(0), ingredients.get(1), ingredients.get(2), ingredients.get(4), ingredients.get(5), ingredients.get(11), ingredients.get(12), ingredients.get(13), ingredients.get(18)
		List<Ingredient> ingredients = Arrays.asList(
				new Ingredient("Farina"),			//0
				new Ingredient("Pomodoro"),			//1
				new Ingredient("Mozzarella"),		//2
				new Ingredient("Basilico"),			//3
				new Ingredient("Sale"),				//4
				new Ingredient("Olio"),				//5
				new Ingredient("Gorgonzola"),		//6
				new Ingredient("Fontina"),			//7
				new Ingredient("Parmigiano"),		//8
				new Ingredient("Tonno"),			//9
				new Ingredient("Cipolla"),			//10
				new Ingredient("Prosciutto cotto"),	//11
				new Ingredient("Carciofini"),		//12
				new Ingredient("Olive"),			//13
				new Ingredient("Salmone"),			//14
				new Ingredient("Aglio"),			//15
				new Ingredient("Origano"),			//16
				new Ingredient("Salame"),			//17
				new Ingredient("Funghi"));			//18
		
		ingredientService.saveAll(ingredients); //Pomodoro, olio, origano, funghi, basilico, prosciutto cotto, olive, fior di latte, salame
		
		List<SpecialOffer> specialOffers = Arrays.asList(
				new SpecialOffer("MAXI Offerta!", 75, LocalDate.now(), LocalDate.now().plusDays(3)),
				new SpecialOffer("Sottocosto", 30, LocalDate.now(), LocalDate.now().plusDays(24)));
		specialOfferService.saveAll(specialOffers);

		List<Pizza> pizzas = Arrays.asList(
				new Pizza("Pizza Margherita", "/images/margherita.jpg", 6f, specialOffers.get(0), ingredients.get(0), ingredients.get(1), ingredients.get(2), ingredients.get(3), ingredients.get(4), ingredients.get(5)),
				new Pizza("Pizza Quattro Formaggi", "/images/quattro-formaggi.jpg", 7.5f, specialOffers.get(1), ingredients.get(0), ingredients.get(1), ingredients.get(2), ingredients.get(4), ingredients.get(5), ingredients.get(6), ingredients.get(7), ingredients.get(8)),
				new Pizza("Pizza Capricciosa", "/images/capricciosa.jpg", 8.5f, specialOffers.get(1), ingredients.get(0), ingredients.get(1), ingredients.get(2), ingredients.get(4), ingredients.get(5), ingredients.get(11), ingredients.get(12), ingredients.get(13), ingredients.get(18)),
				new Pizza("Pizza Tonno e Cipolla", "/images/tonno-cipolla.jpg", 9f, null, ingredients.get(0), ingredients.get(1), ingredients.get(2), ingredients.get(4), ingredients.get(5), ingredients.get(9), ingredients.get(10)),
				new Pizza("Pizza ai Funghi", "/images/funghi.jpg", 8f, null, ingredients.get(0), ingredients.get(1), ingredients.get(2), ingredients.get(4), ingredients.get(5), ingredients.get(18)),
				new Pizza("Pizza al Salmone", "/images/salmone.webp", 9f, specialOffers.get(0), ingredients.get(0), ingredients.get(1), ingredients.get(2), ingredients.get(4), ingredients.get(5), ingredients.get(14)),
				new Pizza("Pizza alla Marinara", "/images/marinara.jpg", 7.f, null, ingredients.get(0), ingredients.get(1), ingredients.get(2), ingredients.get(4), ingredients.get(5), ingredients.get(15), ingredients.get(16)),
				new Pizza("Pizza Quattro Stagioni", "/images/quattro_stagioni.jpg", 9.5f, specialOffers.get(1), ingredients.get(0), ingredients.get(1), ingredients.get(2), ingredients.get(3), ingredients.get(4), ingredients.get(5), ingredients.get(11), ingredients.get(13), ingredients.get(17)));
		pizzaService.saveAll(pizzas);
	}
}
