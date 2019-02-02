package com.danit.utils;

import com.danit.models.BaseEntity;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class CustomListBaseEntityDeserializer extends JsonDeserializer<Long[]> {

  @Autowired
  ObjectMapper objectMapper;

  @Override
  public Long[] deserialize(JsonParser paramJsonParser,
                            DeserializationContext paramDeserializationContext)
      throws IOException {
    List<BaseEntity> entities = objectMapper.readValue(paramJsonParser.getText(), new TypeReference<List<BaseEntity>>() {
    });
    return (Long[]) entities.stream().map(BaseEntity::getId).toArray();
  }
}