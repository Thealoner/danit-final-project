package com.danit.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomDateSerializer extends StdSerializer<Date> {

  private SimpleDateFormat formatter
      = new SimpleDateFormat("dd-MM-yy");

  public CustomDateSerializer() {
    this(null);
  }

  public CustomDateSerializer(Class myDateClass) {
    super(myDateClass);
  }

  @Override
  public void serialize(Date value, JsonGenerator gen, SerializerProvider arg2)
      throws IOException, JsonProcessingException {
    gen.writeString(formatter.format(value));
  }

}