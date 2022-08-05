package com.devh.project.security.userdetails;

import com.devh.project.common.entity.BaseMember;
import com.devh.project.common.repository.MemberRepository;
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
		return toUserDetails(
				memberRepository
						.findByUsername(username)
						.orElseThrow(() -> new UsernameNotFoundException(username))
		);
	}

	public UserDetails toUserDetails(BaseMember member) {
		return UserDetailsImpl.builder()
				.id(member.getId())
				.username(member.getUsername())
				.password(member.getPassword())
				.build();
	}
}
