package com.devh.project.security.components;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.devh.project.common.helper.AES256Helper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticationProviderImpl implements AuthenticationProvider {
	
	private final UserDetailsService userDetailsService;
	private final PasswordEncoder passwordEncoder;
	private final AES256Helper aes256Helper;
	
	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		log.info("try to login... "+authentication);
        final String username = (authentication.getPrincipal() != null) ? authentication.getName() : "";
        String password;
		try {
			password = aes256Helper.decrypt((String) authentication.getCredentials());
		} catch (InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeySpecException
				| BadPaddingException | IllegalBlockSizeException | InvalidAlgorithmParameterException e) {
			throw new BadCredentialsException(e.getMessage());
		}
        
		final UserDetails userDetails = userDetailsService.loadUserByUsername(username);

		if (!passwordEncoder.matches(password, userDetails.getPassword()))
     		throw new BadCredentialsException(username);
     	else if(!userDetails.isEnabled())
     		throw new DisabledException(username);
     	else if(!userDetails.isAccountNonExpired())
     		throw new AccountExpiredException(username);
     	else if(!userDetails.isAccountNonLocked())
     		throw new LockedException(username);
     	else if(!userDetails.isCredentialsNonExpired())
     		throw new CredentialsExpiredException(username);

        final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        token.setDetails(authentication.getDetails());
        return token;
	}
}
