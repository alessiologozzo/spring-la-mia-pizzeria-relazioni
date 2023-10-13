package org.lessons.java.spring.validators;

import org.lessons.java.spring.models.SpecialOffer;
import org.lessons.java.spring.validators.annotations.ValidDates;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateValidator implements ConstraintValidator< ValidDates, 
    SpecialOffer> {

    @Override
    public void initialize(ValidDates constraintAnnotation) {
    }

    @Override
    public boolean isValid(SpecialOffer specialOffer, ConstraintValidatorContext context) {
    	boolean result = true;
    	
    	if(specialOffer.getStartDate().isAfter(specialOffer.getEndDate()))
        	result = false;

        return result;
    }

}