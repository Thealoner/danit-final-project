package com.danit.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class CustomDateSerializer extends JsonSerializer<Date> {

  @Value("${global.date.pattern}")
  private String datePattern;

  @Autowired
  private SimpleDateFormat simpleDateFormat;

  @Override
  public void serialize(Date date, JsonGenerator jsonGenerator,
                        SerializerProvider serializerProvider) throws IOException {
    simpleDateFormat.applyPattern(datePattern);
    jsonGenerator.writeString(simpleDateFormat.format(date));
  }

}