package com.devh.project.item.service;

import com.devh.project.item.dto.CoffeeCreateRequestDTO;
import com.devh.project.item.dto.CoffeeCreateResponseDTO;
import com.devh.project.item.dto.CoffeeSearchRequestDTO;
import com.devh.project.item.dto.CoffeeSearchResponseDTO;
import com.devh.project.item.entity.Coffee;
import com.devh.project.item.repository.CoffeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@Transactional
public class CoffeeServiceTests {
    @Mock
    private CoffeeRepository coffeeRepository;
    @InjectMocks
    private CoffeeService coffeeService;
    private final List<Coffee> givenCoffeeList = new ArrayList<>();

    @BeforeEach
    public void beforeEach() {
        givenCoffeeList.clear();
        givenCoffeeList.add(Coffee.builder()
                .id(1L)
                .name("Americano")
                .price(1500)
                .stockQuantity(3000)
                .ice(true)
                .build());
        givenCoffeeList.add(Coffee.builder()
                .id(2L)
                .name("Americano")
                .price(2000)
                .stockQuantity(3000)
                .ice(false)
                .build());

        givenCoffeeList.add(Coffee.builder()
                .id(3L)
                .name("Latte")
                .price(2500)
                .stockQuantity(1700)
                .ice(true)
                .build());
        givenCoffeeList.add(Coffee.builder()
                .id(4L)
                .name("Latte")
                .price(3000)
                .stockQuantity(1700)
                .ice(false)
                .build());

        givenCoffeeList.add(Coffee.builder()
                .id(5L)
                .name("Mocha")
                .price(2500)
                .stockQuantity(1400)
                .ice(true)
                .build());
        givenCoffeeList.add(Coffee.builder()
                .id(6L)
                .name("Mocha")
                .price(3000)
                .stockQuantity(1400)
                .ice(false)
                .build());

        givenCoffeeList.add(Coffee.builder()
                .id(7L)
                .name("DolceLatte")
                .price(3000)
                .stockQuantity(1000)
                .ice(true)
                .build());
        givenCoffeeList.add(Coffee.builder()
                .id(8L)
                .name("DolceLatte")
                .price(3500)
                .stockQuantity(1000)
                .ice(false)
                .build());
    }

    @Test
    public void search() {
        // given
        final int givenPage = 1;
        final int givenSize = 3;
        final CoffeeSearchRequestDTO givenCoffeeSearchRequestDTO = CoffeeSearchRequestDTO.builder()
                .page(givenPage)
                .size(givenSize)
                .build();
        given(coffeeRepository.findAll(PageRequest.of(givenPage - 1, givenSize))).willReturn(new PageImpl<>(givenCoffeeList.subList(0, 3)));
        // when
        CoffeeSearchResponseDTO coffeeSearchResponseDTO = coffeeService.search(givenCoffeeSearchRequestDTO);
        coffeeSearchResponseDTO.getItemList().forEach(System.out::println);
        // then
        assertEquals(coffeeSearchResponseDTO.getPaging().getPage(), givenPage);
        assertEquals(coffeeSearchResponseDTO.getItemList().size(), givenSize);
    }

    @Test
    public void create() {
        // given
        final CoffeeCreateRequestDTO givenCoffeeRequestDTO = CoffeeCreateRequestDTO.builder()
                .itemList(givenCoffeeList)
                .build();
        given(coffeeRepository.saveAll(any())).willAnswer(i -> i.getArguments()[0]);
        // when
        CoffeeCreateResponseDTO coffeeCreateResponseDTO = coffeeService.create(givenCoffeeRequestDTO);
        // then
        assertTrue(coffeeCreateResponseDTO.isResult());
        assertEquals(coffeeCreateResponseDTO.getCount(), givenCoffeeList.size());
    }
}
