package com.devh.project.security.userdetails;

import com.devh.project.common.entity.BaseUser;
import com.devh.project.common.repository.UserRepository;
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

	private final UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("findByUsername... "+username);
		return toUserDetails(
				userRepository
						.findByUsername(username)
						.orElseThrow(() -> new UsernameNotFoundException(username))
		);
	}

	public UserDetails toUserDetails(BaseUser user) {
		return UserDetailsImpl.builder()
				.id(user.getId())
				.username(user.getUsername())
				.password(user.getPassword())
				.build();
	}
}
