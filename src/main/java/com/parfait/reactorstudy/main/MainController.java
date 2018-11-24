package com.parfait.reactorstudy.main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parfait.reactorstudy.api.ApiClient;
import com.parfait.reactorstudy.api.ReactiveApiClient;
import com.parfait.reactorstudy.main.model.Banner;
import com.parfait.reactorstudy.main.model.HomeDto;
import com.parfait.reactorstudy.main.model.Menu;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/main")
public class MainController {

    private final ApiClient apiClient;
    private final ReactiveApiClient reactiveApiClient;

    public MainController(ObjectMapper objectMapper) {
        this.apiClient = new ApiClient(objectMapper);
        this.reactiveApiClient = new ReactiveApiClient(objectMapper);
    }

    @GetMapping(params = "reactive!=true")
    public HomeDto home(@RequestParam(defaultValue = "PT1S") Duration delay) {
        List<Banner> banners = apiClient.bannerList(delay);
        List<Menu> menus = apiClient.menuList(delay);
        return new HomeDto(banners, menus);
    }

    @GetMapping(params = "reactive=true")
    public Mono<HomeDto> reactiveHome(@RequestParam(defaultValue = "PT1S") Duration delay) {

        Mono<List<Banner>> banners = reactiveApiClient.bannerList(delay);
        Mono<List<Menu>> menus = reactiveApiClient.menuList(delay)
                                                  .timeout(Duration.ofSeconds(3))
                                                  .doOnError(e -> log.warn(e.getMessage(), e))
                                                  .onErrorReturn(Collections.singletonList(Menu.DEFAULT));
//                                                  .onErrorResume((e) -> {
//                                                      log.error(e.getMessage(), e);
//                                                      return Mono.just(Collections.singletonList(Menu.DEFAULT));
//                                                  });


        return Mono.zip(banners, menus, HomeDto::new);
    }
}
