package com.devh.project.cafe.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devh.project.cafe.entity.DailySales;
import com.devh.project.cafe.entity.Menu;
import com.devh.project.cafe.repository.DailySalesRepository;
import com.devh.project.cafe.repository.MenuRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class DailySalesService {
    private final DailySalesRepository dailySalesRepository;
    private final MenuRepository menuRepository;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public boolean update(Long id, int amount) {
        final String nowDate = sdf.format(new Date());
        Menu menu = menuRepository.findById(id).orElseThrow();
        
        DailySales dailySales = dailySalesRepository.findByDateAndMenu(nowDate, menu).orElseGet(() -> dailySalesRepository.save(DailySales.create(nowDate, menu)));
        
        final long before = dailySales.getSales();
        dailySales.addSales(amount);
        
        return dailySales.getSales() == before + amount;
    }
}
