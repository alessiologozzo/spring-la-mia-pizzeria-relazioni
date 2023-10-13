package org.lessons.java.spring.models;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

import org.lessons.java.spring.validators.annotations.ValidDates;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@ValidDates
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
	@FutureOrPresent
	private LocalDate startDate;

	@Column(nullable = false)
	@FutureOrPresent
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

	public void set(SpecialOffer specialOffer) {
		setTitle(specialOffer.getTitle());
		setDiscount(specialOffer.getDiscount());
		setStartDate(specialOffer.getStartDate());
		setEndDate(specialOffer.getEndDate());
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

	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public int getDiscount() {
		return discount;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public String getFormattedStartDate() {
		DayOfWeek weekDay = startDate.getDayOfWeek();
		Month month = startDate.getMonth();

		return weekDay.getDisplayName(TextStyle.FULL, Locale.getDefault()) + " " + startDate.getDayOfMonth() + " "
				+ month.getDisplayName(TextStyle.FULL, Locale.getDefault()) + " " + startDate.getYear();
	}

	public String getFormattedEndDate() {
		DayOfWeek weekDay = endDate.getDayOfWeek();
		Month month = endDate.getMonth();

		return weekDay.getDisplayName(TextStyle.FULL, Locale.getDefault()) + " " + endDate.getDayOfMonth() + " "
				+ month.getDisplayName(TextStyle.FULL, Locale.getDefault()) + " " + endDate.getYear();
	}

	public List<Pizza> getPizzas() {
		return pizzas;
	}

	public boolean isActive() {
		return (LocalDate.now().isEqual(startDate) || LocalDate.now().isAfter(startDate)
				&& (LocalDate.now().isEqual(endDate) || LocalDate.now().isBefore(endDate)));
	}

	public static SpecialOffer createEmptySpecialOffer() {
		return new SpecialOffer();
	}
}
