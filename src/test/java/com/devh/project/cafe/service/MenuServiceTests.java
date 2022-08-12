package com.devh.project.cafe.service;

import com.devh.project.cafe.dto.MenuDTO;
import com.devh.project.cafe.entity.Menu;
import com.devh.project.cafe.repository.MenuRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.doNothing;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class MenuServiceTests {
    @Mock
    private MenuRepository menuRepository;
    @InjectMocks
    private MenuService menuService;

    @Test
    public void create() {
        // given
        given(menuRepository.saveAll(anyList())).willAnswer(i -> i.getArguments()[0]);
        final List<MenuDTO> givenMenuDTOList = new ArrayList<>();
        givenMenuDTOList.add(MenuDTO.create("Americano", 2000, true, true));
        givenMenuDTOList.add(MenuDTO.create("Americano", 1500, false, true));
        givenMenuDTOList.add(MenuDTO.create("Latte", 2500, true, true));
        givenMenuDTOList.add(MenuDTO.create("Latte", 2000, true, true));
        // when
        int resultCount = menuService.create(givenMenuDTOList);
        // then
        assertEquals(resultCount, givenMenuDTOList.size());
    }
    
    @Test
    public void search() {
    	// given
    	final int givenPage = 1;
    	final int givenSize = 4;
    	final int givenTotal = 10;
    	List<Menu> givenMenuList = List.of(
    	        Menu.create(1L, "Americano", 1500, false, true),
                Menu.create(2L, "Americano", 2000, true, true),
                Menu.create(3L, "Latte", 2000, false, true),
                Menu.create(4L, "Latte", 2500, true, true)
        );
        Pageable givenPageable = PageRequest.of(givenPage-1, givenSize);
        PageImpl<Menu> givenMenuPage = new PageImpl<>(givenMenuList, givenPageable, givenTotal);
        given(menuRepository.findAll(givenPageable)).willReturn(givenMenuPage);
    	// when
        Page<MenuDTO> menuDTOPage = menuService.search(givenPage, givenSize);
        menuDTOPage.getContent().forEach(dto -> {
            System.out.printf("[%s] %s: %d\n", dto.isIce() ? "ICE" : "HOT", dto.getName(), dto.getPrice());
        });
    	// then
        assertEquals(menuDTOPage.getTotalElements(), givenTotal);
        assertEquals(menuDTOPage.getContent().size(), givenSize);
    }

    @Test
    public void modify() {
        // given
        final Long givenId = 1L;
        given(menuRepository.findById(givenId)).willReturn(Optional.of(Menu.create(givenId, "name", 1111, true, true)));
        final MenuDTO givenMenuDTO = MenuDTO.create(givenId, "modified", 1111, true, true);
        // when
        boolean result = menuService.modify(givenMenuDTO);
        // then
        assertTrue(result);
    }

    @Test
    public void delete() {
        // given
        final List<Long> givenIdList = List.of(1L, 2L, 3L);
        doNothing().when(menuRepository).deleteAllById(givenIdList);
        // when
        boolean result = menuService.delete(givenIdList);
        // then
        assertTrue(result);
    }
    
//    @Test
//    public void searchTop() {
//    	// given
//    	final int givenDays = 7;
//    	final int givenTop = 3;
//    	// when
//    	menuService.searchTop(givenDays, givenTop);
//    	// then
//    }
}
