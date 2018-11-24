package com.parfait.reactorstudy.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parfait.reactorstudy.api.model.BannerListResponse;
import com.parfait.reactorstudy.api.model.MenuListResponse;
import com.parfait.reactorstudy.main.model.Banner;
import com.parfait.reactorstudy.main.model.Menu;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

public class ReactiveApiClient {

    private final ObjectMapper objectMapper;
    private final WebClient client;
    private final List<Menu> menuList;
    private final List<Banner> bannerList;

    public ReactiveApiClient(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.client = WebClient.create("http://httpbin.org");
        this.menuList = EnhancedRandom.randomListOf(3, Menu.class);
        this.bannerList = EnhancedRandom.randomListOf(3, Banner.class);
    }

    public Mono<List<Menu>> menuList(Duration delay) {

        return client.post()
                     .uri("/delay/{sec}", delay.getSeconds())
                     .syncBody(menuList)
                     .retrieve()
                     .bodyToMono(MenuListResponse.PARAMETERIZED_TYPE_REFERENCE)
                     .map(response -> response.toObject(objectMapper));
    }

    public Mono<List<Banner>> bannerList(Duration delay) {

        return client.post()
                     .uri("/delay/{sec}", delay.getSeconds())
                     .syncBody(bannerList)
                     .retrieve()
                     .bodyToMono(BannerListResponse.PARAMETERIZED_TYPE_REFERENCE)
                     .map(response -> response.toObject(objectMapper));
    }
}
