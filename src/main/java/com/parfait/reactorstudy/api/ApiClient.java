package com.parfait.reactorstudy.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parfait.reactorstudy.api.model.BannerListResponse;
import com.parfait.reactorstudy.api.model.MenuListResponse;
import com.parfait.reactorstudy.main.model.Banner;
import com.parfait.reactorstudy.main.model.Menu;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.time.Duration;
import java.util.List;

public class ApiClient {

    private final ObjectMapper objectMapper;
    private final List<Menu> menuList;
    private final List<Banner> bannerList;

    public ApiClient(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.menuList = EnhancedRandom.randomListOf(3, Menu.class);
        this.bannerList = EnhancedRandom.randomListOf(3, Banner.class);
    }

    public List<Menu> menuList(Duration delay) {

        try {
            String resultJson = Request.Post("http://httpbin.org/delay/" + delay.getSeconds())
                                       .bodyString(objectMapper.writeValueAsString(menuList), ContentType.APPLICATION_JSON)
                                       .execute()
                                       .returnContent()
                                       .asString();

            return objectMapper.readValue(resultJson, MenuListResponse.class).toObject(objectMapper);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public List<Banner> bannerList(Duration delay) {

        try {
            String resultJson = Request.Post("http://httpbin.org/delay/" + delay.getSeconds())
                                       .bodyString(objectMapper.writeValueAsString(bannerList), ContentType.APPLICATION_JSON)
                                       .execute()
                                       .returnContent()
                                       .asString();

            return objectMapper.readValue(resultJson, BannerListResponse.class).toObject(objectMapper);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
