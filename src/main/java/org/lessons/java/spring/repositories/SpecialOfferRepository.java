package org.lessons.java.spring.repositories;

import org.lessons.java.spring.models.SpecialOffer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecialOfferRepository extends JpaRepository<SpecialOffer, Long> {

}
