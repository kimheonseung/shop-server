package com.devh.project.item.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import com.devh.project.item.entity.Coffee;
import com.devh.project.item.repository.CoffeeRepository;
import com.devh.project.item.service.CoffeeService;

@ExtendWith(MockitoExtension.class)
@Transactional
public class ItemControllerTests {
    @InjectMocks
    private ItemController itemController;
    @Mock
    private CoffeeService coffeeService;
    @Mock
    private CoffeeRepository coffeeRepository;

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
    public void getCoffee() throws Exception {
        // given
//        final int givenPage = 1;
//        final int givenSize = 3;
//        CoffeeSearchRequestDTO givenCoffeeSearchRequestDTO = CoffeeSearchRequestDTO.builder()
//                .page(givenPage)
//                .size(givenSize)
//                .build();
//        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
//        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(mockRequest));
//        mockRequest.addParameter("page", "1");
//        mockRequest.addParameter("size", "3");
//        mockRequest.addParameter("itemDiscriminator", "COFFEE");
//        given(coffeeRepository.findAll(PageRequest.of(givenPage - 1, givenSize))).willReturn(new PageImpl<>(givenCoffeeList.subList(0, 3)));
//        given(itemService.search(givenItemSearchRequestDTO).willReturn(new PageImpl<>(givenCoffeeList.subList(0, 3)));
        // when
//        ApiResponseDTO<? extends Item> apiResponseDTO = itemController.getItem(givenItemSearchRequestDTO);
        // then
//        apiResponseDTO.getDataArray().forEach(System.out::println);
    }


}
