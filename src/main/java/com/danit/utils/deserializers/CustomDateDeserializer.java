package com.danit.utils.deserializers;

import com.danit.exceptions.IllegalDateConversionException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class CustomDateDeserializer extends JsonDeserializer<Date> {

  @Value("${global.date.pattern}")
  private String datePattern;

  @Autowired
  private SimpleDateFormat simpleDateFormat;

  @Override
  public Date deserialize(JsonParser paramJsonParser,
                          DeserializationContext paramDeserializationContext)
      throws IOException, JsonProcessingException {
    String str = paramJsonParser.getText();
    simpleDateFormat.applyPattern(datePattern);
    try {
      return simpleDateFormat.parse(str);
    } catch (ParseException e) {
      throw new IllegalDateConversionException("To date conversion error: " + str);
    }
  }
}
