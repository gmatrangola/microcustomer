package com.matrangola.microcustomer.data.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.matrangola.microcustomer.serialization.RequestDeserializer;
import com.matrangola.microcustomer.serialization.RequestSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@UserDefinedType
@JsonDeserialize(using = RequestDeserializer.class)
@JsonSerialize(using = RequestSerializer.class)
public class Request {
    String countryCode;
    String indexCode;
}
