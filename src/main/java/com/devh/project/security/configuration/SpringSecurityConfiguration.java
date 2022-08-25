package com.devh.project.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.devh.project.common.constant.AuthType;
import com.devh.project.common.filter.JwtFilter;
import com.devh.project.common.helper.JwtHelper;

@Configuration
public class SpringSecurityConfiguration {

//	@Autowired
//	private AuthenticationFailureHandler authenticationFailureHandler;
//	@Autowired
//	private AuthenticationSuccessHandler authenticationSuccessHandler;
	@Autowired
	private AccessDeniedHandler accessDeniedHandler;
	@Autowired
	private AuthenticationEntryPoint authenticationEntryPoint;
	@Autowired
	private AuthenticationProvider authenticationProvider;
	@Autowired
	private JwtHelper jwtHelper;
	
	// Configuring HttpSecurity
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.cors().disable()
			.csrf().disable()
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
//			.formLogin()
//				.usernameParameter("email")
//				.passwordParameter("password")
//				.loginPage("/login")
//				.failureHandler(authenticationFailureHandler)
//				.successHandler(authenticationSuccessHandler)
//				.permitAll()
//				.and()
			.authenticationProvider(authenticationProvider)
            .exceptionHandling()
            	.accessDeniedHandler(accessDeniedHandler)
            	.authenticationEntryPoint(authenticationEntryPoint)
            	.and()
            .authorizeRequests()
            	.antMatchers("/signup", "/login").permitAll()
            	.antMatchers("/cafe").hasAnyAuthority(AuthType.CAFE_USER.toString())
            	.antMatchers("/test").authenticated()
            	.and()
            .addFilterBefore(new JwtFilter(jwtHelper), UsernamePasswordAuthenticationFilter.class);
//			.authorizeHttpRequests((authz) -> authz
//					.antMatchers("/logout", "/refresh", "/admin").authenticated()
//					.antMatchers("/cafe").hasRole(Role.ROLE_CAFE_USER.toString())
//					.anyRequest().permitAll());
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
