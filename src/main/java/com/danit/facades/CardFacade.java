package com.danit.facades;

import com.danit.dto.CardDto;
import com.danit.dto.service.CardListRequestDto;
import com.danit.models.Card;
import org.springframework.stereotype.Component;

@Component
public class CardFacade extends AbstractDtoFacade<CardDto, Card, CardListRequestDto> {
}
