package com.devh.project;

import com.devh.project.token.TokenBasePackage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.devh.project.cafe.CafeBasePackage;
import com.devh.project.common.CommonBasePackage;
import com.devh.project.security.SecurityBasePackage;

@SpringBootApplication
@ComponentScan(basePackageClasses = {
        CafeBasePackage.class,
        CommonBasePackage.class,
        SecurityBasePackage.class,
        TokenBasePackage.class
})
@EntityScan(basePackageClasses = {
        CafeBasePackage.class,
        CommonBasePackage.class,
        SecurityBasePackage.class,
        TokenBasePackage.class
})
@EnableJpaRepositories(basePackageClasses = {
        CafeBasePackage.class,
        CommonBasePackage.class,
        SecurityBasePackage.class,
        TokenBasePackage.class
})
public class ShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopApplication.class, args);
    }

}
