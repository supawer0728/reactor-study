package com.parfait.reactorstudy.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.parfait.reactorstudy.main.model.Menu;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

public class MenuListResponse extends ApiResponse<List<Menu>> {

    public static final ParameterizedTypeReference<MenuListResponse> PARAMETERIZED_TYPE_REFERENCE = new ParameterizedTypeReference<MenuListResponse>() {};
    private static final TypeReference<List<Menu>> TYPE_REFERENCE = new TypeReference<List<Menu>>() {};

    @JsonCreator
    public MenuListResponse(@JsonProperty("data") String data) {
        super(data);
    }

    @Override
    protected TypeReference<List<Menu>> getTypeReference() {
        return MenuListResponse.TYPE_REFERENCE;
    }
}
