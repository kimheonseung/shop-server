package com.devh.project.common.controller;

import com.devh.project.common.constant.ApiStatus;
import com.devh.project.common.dto.ApiResponseDTO;
import com.devh.project.common.dto.MemberDTO;
import com.devh.project.common.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
public class MemberController {
    private final MemberService memberService;

    @GetMapping
    public ApiResponseDTO<MemberDTO> getMember(@RequestParam("memberId") Long id) {
        log.info("[GET] /member ... "+id);
        return ApiResponseDTO.success(ApiStatus.Success.OK, memberService.getMember(id));
    }
}
