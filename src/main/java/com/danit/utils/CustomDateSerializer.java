package com.danit.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomDateSerializer extends JsonSerializer<Date> {

  private SimpleDateFormat formatter
      = new SimpleDateFormat("yyyy-MM-dd");

  @Override
  public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, org.codehaus.jackson.JsonProcessingException {
    jsonGenerator.writeString(formatter.format(date));
  }

}