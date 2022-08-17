package com.devh.project.security.model;

import com.devh.project.common.constant.AuthType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 5847157464990380735L;

	private Long id;
	private String username;
	private String password;
	@Builder.Default
	private final Set<AuthType> authTypes = new HashSet<>();

	public void addAuthType(AuthType authType) {
		this.authTypes.add(authType);
	}

	// 권한 목록
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<SimpleGrantedAuthority> simpleGrantedAuthorities = new HashSet<>();
		authTypes.forEach(authType -> simpleGrantedAuthorities.add(new SimpleGrantedAuthority(authType.toString())));
		return simpleGrantedAuthorities;
	}

	// 비밀번호
	@Override
	public String getPassword() {
		return this.password;
	}

	// 식별값
	@Override
	public String getUsername() {
		return this.username;
	}

	// 계정 만료 여부
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	// 계정 잠김 여부
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	// 비밀번호 만료 여부
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	// 계정 활성 여부
	@Override
	public boolean isEnabled() {
		return true;
	}
}