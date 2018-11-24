package com.parfait.reactorstudy.main.model;

import lombok.Value;

import java.util.List;

@Value
public class HomeDto {

    private List<Banner> banners;
    private List<Menu> menus;
}
