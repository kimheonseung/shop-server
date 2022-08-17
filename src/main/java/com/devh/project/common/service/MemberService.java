package com.devh.project.common.service;

import org.springframework.stereotype.Service;

import com.devh.project.common.constant.AuthType;
import com.devh.project.common.dto.MemberDTO;
import com.devh.project.common.entity.Member;
import com.devh.project.common.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberDTO getMember(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(member.getId());
        memberDTO.setUsername(member.getUsername());
        member.getAuthorities().forEach(authority -> memberDTO.getAuthTypes().add(AuthType.valueOf(authority.getAuthType().toString())));
        return memberDTO;
    }
}
