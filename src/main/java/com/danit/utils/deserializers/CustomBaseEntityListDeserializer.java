package com.danit.utils.deserializers;

import com.danit.annotations.TargetClass;
import com.danit.exceptions.IllegalAccessReflectionException;
import com.danit.models.BaseEntity;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CustomBaseEntityListDeserializer<T extends BaseEntity> extends JsonDeserializer<List<T>> {

  @SuppressWarnings("unchecked")
  @Override
  public List<T> deserialize(JsonParser jsonParser,
                             DeserializationContext paramDeserializationContext)
      throws IOException {
    ObjectMapper objectMapper = (ObjectMapper) jsonParser.getCodec();
    Long[] values = objectMapper.reader().readValue(jsonParser, Long[].class);
    Object obj = jsonParser.getCurrentValue();
    try {
      Class<T> cl = obj.getClass()
          .getDeclaredField(jsonParser.getCurrentName())
          .getAnnotation(TargetClass.class)
          .value();
      List<T> entities = new ArrayList<>();
      for (Long id : values) {
        T t = cl.newInstance();
        t.setId(id);
        entities.add(t);
      }
      return entities;
    } catch (NoSuchFieldException | InstantiationException | IllegalAccessException e) {
      throw new IllegalAccessReflectionException("can't read information about target class", e);
    }
  }

}