package com.matrangola.microcustomer.validation;

public class ResourceException extends Exception {
    private ResourceErrorResponse response;

    public ResourceException(Class<?> aClass, Object id) {
        super("Unable to find " + id + " for " + aClass.getName());
        response = new ResourceErrorResponse(id.toString(), aClass.getName(), "Not Found");
    }

    public ResourceErrorResponse getResponse() {
        return response;
    }
}
