package com.devh.project.security.components;

import com.devh.project.common.entity.Member;
import com.devh.project.common.repository.MemberRepository;
import com.devh.project.security.model.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	private final MemberRepository memberRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("findByUsername... "+username);
		Member member = memberRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
		UserDetailsImpl userDetails = UserDetailsImpl.builder()
				.id(member.getId())
				.username(member.getUsername())
				.password(member.getPassword())
				.build();
		member.getAuthorities().forEach(authority -> userDetails.addAuthType(authority.getAuthType()));
		return userDetails;
	}
}
