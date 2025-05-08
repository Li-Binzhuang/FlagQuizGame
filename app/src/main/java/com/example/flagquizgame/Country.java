package com.example.flagquizgame;

/**
 * 表示一个国家的模型类
 */
public class Country {
    private String name; // 国家名称
    private String flagImageName; // 国旗图片资源名称（不包含扩展名）

    public Country(String name, String flagImageName) {
        this.name = name;
        this.flagImageName = flagImageName;
    }

    public String getName() {
        return name;
    }

    public String getFlagImageName() {
        return flagImageName;
    }
}