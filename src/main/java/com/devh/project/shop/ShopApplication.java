package com.devh.project.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

import com.devh.project.BasePackage;

@SpringBootApplication
@ComponentScan(basePackageClasses = {BasePackage.class})
@EntityScan(basePackageClasses = {BasePackage.class})
public class ShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopApplication.class, args);
    }

}
