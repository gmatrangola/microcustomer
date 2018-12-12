package com.matrangola.microcustomer.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.matrangola.microcustomer.data.model.Request;

import java.io.IOException;

public class RequestDeserializer extends JsonDeserializer<Request> {

    @Override
    public Request deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        String req = node.get("req").textValue();
        String[] split = req.split(":");
        return new Request(split[0], split[1]);
    }
}

