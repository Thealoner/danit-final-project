package com.danit.utils;

import com.danit.exceptions.IllegalFormatException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomDateDeserializer extends JsonDeserializer<Date> {
  private SimpleDateFormat dateFormat = new SimpleDateFormat(
      "yyyy-MM-dd");

  @Override
  public Date deserialize(JsonParser paramJsonParser,
                          DeserializationContext paramDeserializationContext)
      throws IOException, JsonProcessingException {
    String str = paramJsonParser.getText();
    try {
      return dateFormat.parse(str);
    } catch (ParseException e) {
      throw new IllegalFormatException("To date conversion error: " + str);
    }
  }
}
