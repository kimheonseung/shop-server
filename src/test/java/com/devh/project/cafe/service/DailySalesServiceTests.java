package com.devh.project.cafe.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.devh.project.cafe.entity.DailySales;
import com.devh.project.cafe.entity.Menu;
import com.devh.project.cafe.repository.DailySalesRepository;
import com.devh.project.cafe.repository.MenuRepository;

@ExtendWith(MockitoExtension.class)
public class DailySalesServiceTests {
	@Mock
	private MenuRepository menuRepository;
	@Mock
	private DailySalesRepository dailySalesRepository;
	@InjectMocks
	private DailySalesService dailySalesService;
	
	@Test
	public void update_newRecord() {
		// given
		final Long givenId = 1L;
		final int givenAmount = 3;
		final Menu givenMenu = Menu.create(givenId, "Americano", 2000, true, true);
		given(menuRepository.findById(givenId)).willReturn(Optional.of(givenMenu));
		given(dailySalesRepository.findByDateAndMenu(any(String.class), eq(givenMenu))).willReturn(Optional.empty());
		given(dailySalesRepository.save(any(DailySales.class))).willAnswer(i -> i.getArguments()[0]);
		// when
		boolean result = dailySalesService.update(givenId, givenAmount);
		// then
		assertTrue(result);
	}
}
