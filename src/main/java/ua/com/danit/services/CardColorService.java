package ua.com.danit.services;

import ua.com.danit.models.Card;

import java.util.List;

public interface CardColorService {

  List<Card> getAllCardColors();

  Card getCardColorById(long id);

  List<Card> saveCardColors(List<Card> cards);

  void deleteCardColorById(long id);

  void deleteCardColors(List<Card> cards);

}
