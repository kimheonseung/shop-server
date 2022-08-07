package com.devh.project.cafe.service;

import com.devh.project.cafe.dto.MenuDTO;
import com.devh.project.cafe.entity.Menu;
import com.devh.project.cafe.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;

    public int create(List<MenuDTO> menuDTOList) {
        StringBuilder sbAudit = new StringBuilder();
        menuDTOList.forEach(dto -> sbAudit.append("\n\t").append(dto.toString()));
        log.info("try to create cafe menu... "+sbAudit.toString());

        List<Menu> savedMenuList = menuRepository.saveAll(createMenuList(menuDTOList));

        StringBuilder sbSavedAudit = new StringBuilder();
        savedMenuList.forEach(e -> sbSavedAudit.append("\n\t").append(e.toString()));
        log.info("success to create cafe menu... "+sbSavedAudit.toString());

        return savedMenuList.size();
    }

    public boolean modify(MenuDTO menuDTO) {
        final Long id = menuDTO.getId();
        Menu menu = menuRepository.findById(id).orElseThrow();
        update(menu, menuDTO);
        return true;
    }

    public boolean delete(List<Long> idList) {
        log.info("try to delete... "+idList);
        menuRepository.deleteAllById(idList);
        return true;
    }

    private void update(Menu entity, MenuDTO dto) {
        log.info(String.format("try to modify... \n\tbefore: %s\n\tafter : %s", entity, dto));
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
