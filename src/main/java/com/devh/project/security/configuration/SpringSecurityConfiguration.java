package com.devh.project.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.devh.project.common.constant.Role;

@Configuration
public class SpringSecurityConfiguration {

	@Autowired
	private AuthenticationFailureHandler authenticationFailureHandler;
	@Autowired
	private AuthenticationSuccessHandler authenticationSuccessHandler;
	@Autowired
	private AccessDeniedHandler accessDeniedHandler;
	@Autowired
	private AuthenticationEntryPoint authenticationEntryPoint;
	@Autowired
	private AuthenticationProvider authenticationProvider;
	
	// Configuring HttpSecurity
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.formLogin()
				.usernameParameter("email")
				.passwordParameter("password")
				.loginPage("/login")
				.failureHandler(authenticationFailureHandler)
				.successHandler(authenticationSuccessHandler)
				.permitAll()
				.and()
			.authenticationProvider(authenticationProvider)
            .exceptionHandling()
            	.accessDeniedHandler(accessDeniedHandler)
            	.authenticationEntryPoint(authenticationEntryPoint)
            	.and()
			.authorizeHttpRequests((authz) -> authz
					.antMatchers("/logout", "/refresh", "/admin").authenticated()
					.antMatchers("/cafe").hasRole(Role.CAFE_USER.toString())
					.anyRequest().permitAll());
		return http.build();
	}
	
//	// Configuring WebSecurity
//	@Bean
//	public WebSecurityCustomizer webSecurityCustomizer() {
//		return (web) -> web
//				.ignoring().antMatchers("/login/**", "/signup/**");
//	}
//	
//	// In-Memory Authentication
//	@Bean
//	public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
//		UserDetails testUser = User.withDefaultPasswordEncoder()
//				.username("test")
//				.password("test")
//				.roles("USER")
//				.build();
//		UserDetails adminUser = User.withDefaultPasswordEncoder()
//				.username("admin")
//				.password("admin")
//				.roles("ADMIN")
//				.build();
//		return new InMemoryUserDetailsManager(testUser, adminUser);
//	}
	
	
}
