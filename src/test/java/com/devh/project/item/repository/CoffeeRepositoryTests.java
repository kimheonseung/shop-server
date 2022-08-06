package com.devh.project.item.repository;

import com.devh.project.item.entity.Coffee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {"spring.config.location=classpath:application-test.yml"})
public class CoffeeRepositoryTests {
    @Autowired
    private CoffeeRepository coffeeRepository;

    @BeforeEach
    public void beforeEach() {
        List<Coffee> coffeeList = new ArrayList<>();
        coffeeList.add(Coffee.builder()
                .name("Americano")
                .price(1500)
                .stockQuantity(3000)
                .ice(true)
                .build());
        coffeeList.add(Coffee.builder()
                .name("Americano")
                .price(2000)
                .stockQuantity(3000)
                .ice(false)
                .build());

        coffeeList.add(Coffee.builder()
                .name("Latte")
                .price(2500)
                .stockQuantity(1700)
                .ice(true)
                .build());
        coffeeList.add(Coffee.builder()
                .name("Latte")
                .price(3000)
                .stockQuantity(1700)
                .ice(false)
                .build());

        coffeeList.add(Coffee.builder()
                .name("Mocha")
                .price(2500)
                .stockQuantity(1400)
                .ice(true)
                .build());
        coffeeList.add(Coffee.builder()
                .name("Mocha")
                .price(3000)
                .stockQuantity(1400)
                .ice(false)
                .build());

        coffeeList.add(Coffee.builder()
                .name("DolceLatte")
                .price(3000)
                .stockQuantity(1000)
                .ice(true)
                .build());
        coffeeList.add(Coffee.builder()
                .name("DolceLatte")
                .price(3500)
                .stockQuantity(1000)
                .ice(false)
                .build());
        coffeeRepository.saveAll(coffeeList);
    }

    @Test
    public void search() {
        // given
        final int givenPage = 0;
        final int givenSize = 3;
        // when
        Page<Coffee> coffeePage = coffeeRepository.findAll(PageRequest.of(givenPage, givenSize));
        coffeePage.getContent().forEach(System.out::println);
        // then
        assertEquals(coffeePage.getNumber(), givenPage);
        assertTrue(coffeePage.getSize() <= givenSize);
    }
}
