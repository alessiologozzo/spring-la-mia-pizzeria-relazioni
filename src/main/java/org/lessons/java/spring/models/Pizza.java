package org.lessons.java.spring.models;

import java.text.DecimalFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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

	@Column(length = 255, nullable = false)
	@NotNull
	@NotBlank
	@Size(max = 255)
	private String description;

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
	
	private Pizza() {
	}

	public Pizza(String name, String description, String url, float price, SpecialOffer specialOffer) {
		setName(name);
		setDescription(description);
		setUrl(url);
		setPrice(price);
		setSpecialOffer(specialOffer);
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getDescription() {
		return description;
	}

	public String getUrl() {
		return url;
	}

	public float getPrice() {
		return price;
	}

	public String calculateDiscount() {
		String result = "";
		
		if(specialOffer != null) {
			DecimalFormat formatter = new DecimalFormat("0.00");
			result = formatter.format(price - (price * specialOffer.getDiscount() / 100));
		}
		else
			result = Float.toString(price);
		
		return result;
	}
	
	public SpecialOffer getSpecialOffer() {
		return specialOffer;
	}
	
	public static Pizza createEmptyPizza() {
		return new Pizza();
	}
}
