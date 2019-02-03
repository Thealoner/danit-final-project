package com.danit.utils.serializers;

import com.danit.models.BaseEntity;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.List;

public class CustomBaseEntityListSerializer extends JsonSerializer<List<BaseEntity>> {

  @Override
  public void serialize(List<BaseEntity> entityList, JsonGenerator jsonGenerator,
                        SerializerProvider serializerProvider) throws IOException {
    jsonGenerator.writeObject(entityList.stream().map(baseEntity -> String.valueOf(baseEntity.getId())).toArray());
  }

}