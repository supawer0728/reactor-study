package com.parfait.reactorstudy.api.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.UncheckedIOException;

public abstract class ApiResponse<T> {

    private final String data;

    public ApiResponse(String data) {
        this.data = data;
    }

    protected abstract TypeReference<T> getTypeReference();

    public T toObject(ObjectMapper objectMapper) {

        try {
            return objectMapper.readValue(data, getTypeReference());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public String toJson() {
        return data;
    }
}
