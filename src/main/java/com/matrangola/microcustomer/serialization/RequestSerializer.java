package com.matrangola.microcustomer.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.matrangola.microcustomer.data.model.Request;

import java.io.IOException;

public class RequestSerializer extends JsonSerializer<Request> {

    @Override
    public void serialize(Request value, JsonGenerator gen,
                          SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeFieldName("req");
        gen.writeString(
                String.format("%s:%s", value.getCountryCode(), value.getIndexCode()));
        gen.writeEndObject();
    }
}