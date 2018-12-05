package com.danit.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomDateTimeSerializer extends JsonSerializer<Date> {

  @Value("${global.date-time.pattern}")
  private String dateTimePattern;

  @Override
  public void serialize(Date date, JsonGenerator jsonGenerator,
                        SerializerProvider serializerProvider) throws IOException {
    jsonGenerator.writeString(getSimpleDateFormat().format(date));
  }

  private SimpleDateFormat getSimpleDateFormat() {
    return new SimpleDateFormat(dateTimePattern);
  }

}