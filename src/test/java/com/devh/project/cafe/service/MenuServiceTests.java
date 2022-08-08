package com.devh.project.cafe.service;

import com.devh.project.cafe.dto.MenuDTO;
import com.devh.project.cafe.entity.Menu;
import com.devh.project.cafe.repository.MenuRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    	
    	// when
    	
    	// then
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
}
