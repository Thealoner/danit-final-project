package com.danit.services;

import com.danit.dto.service.CardListRequestDto;
import com.danit.models.Card;
import org.springframework.stereotype.Service;

@Service
public class CardService extends AbstractBaseEntityService<Card, CardListRequestDto> {
}
