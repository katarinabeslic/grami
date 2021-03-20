package com.njt.projekat.service;

import com.njt.projekat.entity.CardInformation;
import com.njt.projekat.entity.User;

public interface CardInformationService {
	
	CardInformation save(CardInformation cardInformation);

	CardInformation findByUser(User user);

}
