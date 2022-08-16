package com.devh.project.common.service;

import com.devh.project.common.constant.Role;
import com.devh.project.common.dto.MemberDTO;
import com.devh.project.common.entity.Member;
import com.devh.project.common.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberDTO getMember(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(member.getId());
        memberDTO.setUsername(member.getUsername());
        member.getAuthorities().forEach(authority -> memberDTO.getRoles().add(Role.valueOf(authority.getRole().toString())));
        return memberDTO;
    }
}
