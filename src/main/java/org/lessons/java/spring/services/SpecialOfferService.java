package org.lessons.java.spring.services;

import java.util.List;

import org.lessons.java.spring.models.SpecialOffer;
import org.lessons.java.spring.repositories.SpecialOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpecialOfferService {
	@Autowired
	private SpecialOfferRepository specialOfferRepository;
	
	public void save(SpecialOffer specialOffer) {
		specialOfferRepository.save(specialOffer);
	}
	
	public void saveAll(List<SpecialOffer> specialOffers) {
		specialOfferRepository.saveAll(specialOffers);
	}
	
	public List<SpecialOffer> findAll() {
		return specialOfferRepository.findAll();
	}
}
