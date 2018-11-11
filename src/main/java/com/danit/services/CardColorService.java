package com.danit.services;

import com.danit.models.CardColor;

import java.util.List;

public interface CardColorService {

  List<CardColor> getAllCardColors();

  CardColor getCardColorById(long id);

  List<CardColor> saveCardColors(List<CardColor> cards);

  void deleteCardColorById(long id);

  void deleteCardColors(List<CardColor> cards);

}
