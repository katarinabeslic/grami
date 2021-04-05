package com.njt.projekat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.njt.projekat.dao.CardInformationRepository;
import com.njt.projekat.entity.CardInformation;
import com.njt.projekat.entity.User;
import com.njt.projekat.service.CardInformationService;

@Service
public class CardInformationServiceImpl implements CardInformationService {
	
	@Autowired
	private CardInformationRepository cardInformationRepository;

	@Override
	public void save(CardInformation cardInformation) {
		cardInformationRepository.save(cardInformation);
	}

	@Override
	public CardInformation findByUser(User user) {
		return cardInformationRepository.findByUser(user);
	}

}
