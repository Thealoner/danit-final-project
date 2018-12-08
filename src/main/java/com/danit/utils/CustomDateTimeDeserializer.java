package com.danit.utils;

import com.danit.exceptions.IllegalFormatException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomDateTimeDeserializer extends JsonDeserializer<Date> {

  @Value("${global.date-time.pattern}")
  private String dateTimePattern;

  @Autowired
  private SimpleDateFormat simpleDateFormat;

  @Override
  public Date deserialize(JsonParser paramJsonParser,
                          DeserializationContext paramDeserializationContext)
      throws IOException, JsonProcessingException {
    String str = paramJsonParser.getText();
    simpleDateFormat.applyPattern(dateTimePattern);
    try {
      return simpleDateFormat.parse(str);
    } catch (ParseException e) {
      throw new IllegalFormatException("To date conversion error: " + str);
    }
  }
}
