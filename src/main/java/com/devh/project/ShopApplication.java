package com.devh.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.devh.project.cafe.CafeBasePackage;
import com.devh.project.common.CommonBasePackage;
import com.devh.project.security.SecurityBasePackage;

@SpringBootApplication
@ComponentScan(basePackageClasses = {CafeBasePackage.class, CommonBasePackage.class, SecurityBasePackage.class})
@EntityScan(basePackageClasses = {CafeBasePackage.class, CommonBasePackage.class, SecurityBasePackage.class})
@EnableJpaRepositories(basePackageClasses = {CafeBasePackage.class, CommonBasePackage.class, SecurityBasePackage.class})
public class ShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopApplication.class, args);
    }

}
