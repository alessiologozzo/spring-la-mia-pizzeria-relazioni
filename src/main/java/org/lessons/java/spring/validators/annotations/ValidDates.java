package org.lessons.java.spring.validators.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.lessons.java.spring.validators.DateValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.TYPE}) 
@Retention(RetentionPolicy.RUNTIME) 
@Constraint(validatedBy=DateValidator.class) 
public @interface ValidDates {
    String message() default "{message.id}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}