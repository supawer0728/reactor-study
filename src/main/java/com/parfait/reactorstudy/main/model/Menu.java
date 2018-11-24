package com.parfait.reactorstudy.main.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Menu {

    public static final Menu DEFAULT = new Menu("DEFAULT", "http://parfait.com/default");

    private String name;
    private String url;
}
