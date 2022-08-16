package com.devh.project.security.components;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.devh.project.common.entity.Member;
import com.devh.project.common.repository.MemberRepository;
import com.devh.project.security.constant.Role;
import com.devh.project.security.model.UserDetailsImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	private final MemberRepository memberRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("findByUsername... "+username);
		Member member = memberRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
		UserDetails userDetails = UserDetailsImpl.builder()
				.id(member.getId())
				.username(member.getUsername())
				.password(member.getPassword())
				.build();
		
//		userDetails.getAuthorities().add(new SimpleGrantedAuthority(Role.CAFE_USER.toString()));
		
		return UserDetailsImpl.builder()
				.id(member.getId())
				.username(member.getUsername())
				.password(member.getPassword())
				.build();
	}
}
