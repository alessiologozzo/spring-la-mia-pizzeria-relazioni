package org.lessons.java.spring.models;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Pizza {
	@Valid

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(length = 255, nullable = false)
	@NotNull
	@NotBlank
	@Size(max = 255)
	private String name;

	@Column(length = 255, nullable = true)
	@Size(max = 255)
	private String url;

	@Column(nullable = false)
	@NotNull
	@Min(0)
	private float price;

	@ManyToOne
	@JoinColumn(nullable = true)
	private SpecialOffer specialOffer;

	@ManyToMany
	private List<Ingredient> ingredients;

	private Pizza() {
	}

	public Pizza(String name, String url, float price, SpecialOffer specialOffer, Ingredient... ingredients) {
		setName(name);
		if (ingredients != null)
			setIngredients(Arrays.asList(ingredients));
		setUrl(url);
		setPrice(price);
		setSpecialOffer(specialOffer);
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public void setSpecialOffer(SpecialOffer specialOffer) {
		this.specialOffer = specialOffer;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}

	public float getPrice() {
		return price;
	}

	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public String calculateDiscount() {
		String result = "";

		if (specialOffer != null) {
			DecimalFormat formatter = new DecimalFormat("0.00");
			result = formatter.format(price - (price * specialOffer.getDiscount() / 100));
		} else
			result = Float.toString(price);

		return result;
	}

	public SpecialOffer getSpecialOffer() {
		return specialOffer;
	}

	public String ingredientsToString() {
		String result = "";
		List<Ingredient> ingredients = getIngredients();

		if (ingredients != null && ingredients.size() > 0) {
			for (Ingredient ingredient : ingredients)
				result += ingredient.getName() + ", ";

			result = result.substring(0, result.length() - 2);
		}

		return result;
	}

	public void removeIngredient(long id) {
		ingredients = ingredients.stream().filter(ingredient -> ingredient.getId() != id).toList();
	}

	public static Pizza createEmptyPizza() {
		return new Pizza();
	}
}
