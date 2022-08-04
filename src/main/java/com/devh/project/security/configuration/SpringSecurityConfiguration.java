package com.devh.project.security.configuration;

import com.devh.project.common.constant.ApiStatus.AuthError;
import com.devh.project.common.dto.ApiResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletResponse;

@Configuration
public class SpringSecurityConfiguration {

	private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
	
	@Autowired
	private AuthenticationFailureHandler authenticationFailureHandler;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, e) -> {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(ApiResponseDTO.authError(AuthError.ACCESS_DENIED)));
            response.getWriter().flush();
            response.getWriter().close();
        };
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, e) -> {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(ApiResponseDTO.authError(AuthError.UNAUTHORIZED)));
            response.getWriter().flush();
            response.getWriter().close();
        };
    }
	
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
//				.loginProcessingUrl("/login")
				.permitAll()
				.and()
//            .exceptionHandling()
//            	.accessDeniedHandler(accessDeniedHandler())
//            	.authenticationEntryPoint(authenticationEntryPoint())
//            	.and()
			.authorizeHttpRequests((authz) -> authz
					.antMatchers("/logout", "/refresh", "/admin").authenticated()
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
