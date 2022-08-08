package com.devh.project.shop.configuration.vo;

import lombok.Data;

@Data
public class DatabaseConfigVO {
    private String database;
    private String driverClassName;
    private String url;
    private String username;
    private String password;
}
