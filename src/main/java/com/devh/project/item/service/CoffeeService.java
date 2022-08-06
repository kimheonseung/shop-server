package com.devh.project.item.service;

import com.devh.project.common.dto.PagingDTO;
import com.devh.project.item.dto.CoffeeCreateRequestDTO;
import com.devh.project.item.dto.CoffeeCreateResponseDTO;
import com.devh.project.item.dto.CoffeeSearchRequestDTO;
import com.devh.project.item.dto.CoffeeSearchResponseDTO;
import com.devh.project.item.dto.CoffeeStockRequestDTO;
import com.devh.project.item.dto.CoffeeStockResponseDTO;
import com.devh.project.item.entity.Coffee;
import com.devh.project.item.exception.NotEnoughStockException;
import com.devh.project.item.repository.CoffeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CoffeeService {
    private final CoffeeRepository coffeeRepository;

    public CoffeeSearchResponseDTO search(CoffeeSearchRequestDTO coffeeSearchRequestDTO) {
        Page<Coffee> coffeePage = coffeeRepository.findAll(
                PageRequest.of(coffeeSearchRequestDTO.getPage() - 1, coffeeSearchRequestDTO.getSize())
        );
        return CoffeeSearchResponseDTO.builder()
                .paging(PagingDTO.build(coffeePage.getNumber()+1, coffeePage.getSize(), coffeePage.getTotalElements()))
                .itemList(coffeePage.getContent())
                .build();
    }

    public CoffeeCreateResponseDTO create(CoffeeCreateRequestDTO coffeeCreateRequestDTO) {
        List<Coffee> coffeeList = coffeeCreateRequestDTO.getItemList();
        List<Coffee> savedList = coffeeRepository.saveAll(coffeeList);
        return CoffeeCreateResponseDTO.builder()
                .count(savedList.size())
                .result(coffeeList.size() == savedList.size())
                .build();
    }

    public void modifyStock(CoffeeStockRequestDTO coffeeStockRequestDTO) throws NotEnoughStockException {
        Coffee coffee = coffeeRepository.findById(coffeeStockRequestDTO.getId()).orElseThrow();
        final int quantity = coffeeStockRequestDTO.getQuantity();
        if(quantity < 0)
            coffee.removeStock(-quantity);
        else
            coffee.addStock(quantity);
    }
}
