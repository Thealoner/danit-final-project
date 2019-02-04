package com.danit.utils.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomDateTimeSerializer extends JsonSerializer<Date> {

  @Value("${global.date-time.pattern}")
  private String dateTimePattern;

  @Autowired
  private SimpleDateFormat simpleDateFormat;

  @Override
  public void serialize(Date date, JsonGenerator jsonGenerator,
                        SerializerProvider serializerProvider) throws IOException {
    simpleDateFormat.applyPattern(dateTimePattern);
    jsonGenerator.writeString(simpleDateFormat.format(date));
  }

}