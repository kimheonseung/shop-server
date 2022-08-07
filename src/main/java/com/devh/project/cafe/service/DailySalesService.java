package com.devh.project.cafe.service;

import com.devh.project.cafe.entity.DailySales;
import com.devh.project.cafe.entity.Menu;
import com.devh.project.cafe.repository.DailySalesRepository;
import com.devh.project.cafe.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DailySalesService {
    private final DailySalesRepository dailySalesRepository;
    private final MenuRepository menuRepository;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


    public boolean update(Long id, Long amount) {
        final String nowDate = sdf.format(new Date());
        Menu menu = menuRepository.findById(id).orElseThrow();
//        DailySales dailySales = dailySalesRepository.findByDateAndMenu(nowDate, menu);
//        DailySales.builder()
//                .date(nowDate)
//                .menu(menu)
//                .sales(0L)
//                .build();
//        dailySales.addSales(amount);
        return true;
    }
}
