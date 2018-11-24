package com.parfait.reactorstudy.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.parfait.reactorstudy.main.model.Banner;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

public class BannerListResponse extends ApiResponse<List<Banner>> {

    public static final ParameterizedTypeReference<BannerListResponse> PARAMETERIZED_TYPE_REFERENCE = new ParameterizedTypeReference<BannerListResponse>() {};
    private static final TypeReference<List<Banner>> TYPE_REFERENCE = new TypeReference<List<Banner>>() {};

    @JsonCreator
    public BannerListResponse(@JsonProperty("data") String data) {
        super(data);
    }

    @Override
    protected TypeReference<List<Banner>> getTypeReference() {
        return BannerListResponse.TYPE_REFERENCE;
    }
}
