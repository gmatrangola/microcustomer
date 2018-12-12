package com.matrangola.microcustomer.validation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceErrorResponse {
    private String id;
    private String className;
    private String message;
}
