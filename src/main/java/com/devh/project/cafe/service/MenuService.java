package com.devh.project.cafe.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.devh.project.cafe.dto.MenuDTO;
import com.devh.project.cafe.entity.Menu;
import com.devh.project.cafe.repository.MenuRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;

    public int create(List<MenuDTO> menuDTOList) {
        StringBuilder sbAudit = new StringBuilder();
        menuDTOList.forEach(dto -> sbAudit.append("\n\t").append(dto.toString()));

        List<Menu> savedMenuList = menuRepository.saveAll(createMenuList(menuDTOList));

        StringBuilder sbSavedAudit = new StringBuilder();
        savedMenuList.forEach(e -> sbSavedAudit.append("\n\t").append(e.toString()));

        return savedMenuList.size();
    }

    public boolean modify(MenuDTO menuDTO) {
        final Long id = menuDTO.getId();
        Menu menu = menuRepository.findById(id).orElseThrow();
        update(menu, menuDTO);
        return true;
    }

    public boolean delete(List<Long> idList) {
        menuRepository.deleteAllById(idList);
        return true;
    }

    private void update(Menu entity, MenuDTO dto) {
        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        entity.setIce(dto.isIce());
        entity.setOnSale(dto.isOnSale());
    }

    private List<Menu> createMenuList(List<MenuDTO> dtoList) {
        List<Menu> menuList = new ArrayList<>();
        dtoList.forEach(dto -> menuList.add(
                Menu.create(dto.getName(), dto.getPrice(), dto.isIce(), dto.isOnSale())
        ));
        return menuList;
    }
}
