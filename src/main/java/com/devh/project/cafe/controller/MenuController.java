package com.devh.project.cafe.controller;

import com.devh.project.cafe.dto.MenuCreateRequestDTO;
import com.devh.project.cafe.dto.MenuCreateResponseDTO;
import com.devh.project.cafe.dto.MenuDTO;
import com.devh.project.cafe.dto.MenuDeleteRequestDTO;
import com.devh.project.cafe.dto.MenuDeleteResponseDTO;
import com.devh.project.cafe.dto.MenuModifyRequestDTO;
import com.devh.project.cafe.dto.MenuModifyResponseDTO;
import com.devh.project.common.constant.ApiStatus;
import com.devh.project.common.dto.ApiResponseDTO;
import com.devh.project.common.dto.PagingDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devh.project.cafe.service.MenuService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/cafe/menu")
@RestController
@RequiredArgsConstructor
@Slf4j
public class MenuController {
	private final MenuService menuService;

	@GetMapping
    public ApiResponseDTO<MenuDTO> getCafeMenu(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "4") int size) {
	    log.info("[GET] /cafe/menu ... "+"page: "+page+", size: "+size);
        Page<MenuDTO> menuDTOPage = menuService.search(page, size);
	    return ApiResponseDTO.success(ApiStatus.Success.OK, menuDTOPage.getContent(), PagingDTO.build(page, size, menuDTOPage.getTotalElements()));
    }

    @PostMapping("/create")
	public ApiResponseDTO<MenuCreateResponseDTO> createCafeMenu(@RequestBody MenuCreateRequestDTO menuCreateRequestDTO) {
		return ApiResponseDTO.success(ApiStatus.Success.OK, MenuCreateResponseDTO.builder()
				.count(menuService.create(menuCreateRequestDTO.getCafeMenuList()))
				.result(true)
				.build()
		);
	}

	@PostMapping("/delete")
	public ApiResponseDTO<MenuDeleteResponseDTO> deleteCafeMenu(@RequestBody MenuDeleteRequestDTO menuDeleteRequestDTO) {
		return ApiResponseDTO.success(ApiStatus.Success.OK, MenuDeleteResponseDTO.builder()
				.result(menuService.delete(menuDeleteRequestDTO.getIdList()))
				.build()
		);
	}

	@PostMapping("/modify")
	public ApiResponseDTO<MenuModifyResponseDTO> modifyCafeMenu(@RequestBody MenuModifyRequestDTO menuModifyRequestDTO) {
		return ApiResponseDTO.success(ApiStatus.Success.OK, MenuModifyResponseDTO.builder()
				.result(menuService.modify(menuModifyRequestDTO.getMenuDTO()))
				.build()
		);
	}
}
