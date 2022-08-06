package com.devh.project.cafe.service;

import com.devh.project.cafe.dto.CafeMenuCreateRequestDTO;
import com.devh.project.cafe.dto.CafeMenuCreateResponseDTO;
import com.devh.project.cafe.dto.CafeMenuDTO;
import com.devh.project.cafe.dto.CafeMenuModifyRequestDTO;
import com.devh.project.cafe.dto.CafeMenuModifyResponseDTO;
import com.devh.project.cafe.entity.CafeMenu;
import com.devh.project.cafe.repository.CafeMenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CafeMenuService {
    private final CafeMenuRepository cafeMenuRepository;

    public CafeMenuCreateResponseDTO create(CafeMenuCreateRequestDTO cafeMenuCreateRequestDTO) {
        List<CafeMenu> cafeMenuList = new ArrayList<>();
        cafeMenuCreateRequestDTO.getCafeMenuList().forEach(dto -> cafeMenuList.add(toEntity(dto)));
        List<CafeMenu> savedCafeMenuList = cafeMenuRepository.saveAll(cafeMenuList);
        return CafeMenuCreateResponseDTO.builder()
                .count(savedCafeMenuList.size())
                .result(savedCafeMenuList.size() == cafeMenuList.size())
                .build();
    }

    public CafeMenuModifyResponseDTO modify(CafeMenuModifyRequestDTO cafeMenuModifyRequestDTO) {
        final Long id = cafeMenuModifyRequestDTO.getCafeMenuDTO().getId();
        CafeMenu cafeMenu = cafeMenuRepository.findById(id).orElseThrow();
        changeValue(cafeMenu, cafeMenuModifyRequestDTO.getCafeMenuDTO());
        return CafeMenuModifyResponseDTO.builder()
                .result(true)
                .build();
    }

    private void changeValue(CafeMenu entity, CafeMenuDTO dto) {
        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        entity.setOnSale(dto.isOnSale());
    }

    private CafeMenu toEntity(CafeMenuDTO dto) {
        return CafeMenu.builder()
                .id(dto.getId())
                .name(dto.getName())
                .price(dto.getPrice())
                .onSale(dto.isOnSale())
                .build();
    }
}
