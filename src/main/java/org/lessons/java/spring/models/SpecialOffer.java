package org.lessons.java.spring.models;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class SpecialOffer {
	@Valid
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false, length = 255)
	@NotNull
	@NotBlank
	@Size(max = 255)
	private String title;
	
	@Column(nullable = false)
	@Min(0)
	@Max(100)
	private int discount;
	
	@Column(nullable = false)
	@Future
	private LocalDate startDate;
	
	@Column(nullable = false)
	@Future
	private LocalDate endDate;
	
	
	@OneToMany(mappedBy = "specialOffer")
	private List<Pizza> pizzas;
	
	private SpecialOffer() {
	}
	
	public SpecialOffer(String title, int discount, LocalDate startDate, LocalDate endDate) {
		setTitle(title);
		setDiscount(discount);
		setStartDate(startDate);
		setEndDate(endDate);
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setDiscount(int discount) {
		this.discount = discount;
	}
	
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	
	public String getTitle() {
		return title;
	}
	
	public int getDiscount() {
		return discount;
	}
	
	public LocalDate getStartDate( ) {
		return startDate;
	}
	
	public LocalDate getEndDate() {
		return endDate;
	}
	
	public List<Pizza> getPizzas() {
		return pizzas;
	}
	
	public static SpecialOffer createEmptySpecialOffer() {
		return new SpecialOffer();
	}
}
