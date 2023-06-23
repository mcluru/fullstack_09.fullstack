package com.lec.todo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.servlet.AuthorizeRequestsDsl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

import com.lec.todo.secutiry.JwtAuthencationFilter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private JwtAuthencationFilter jwtAuthencationFilter;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		// http 시큐리티 빌더
		http.cors().and()			// WebMvcConfig에서 설정했으므로 기본 cors로 설정	// http.cors("http://localhost:3000")
			.csrf().disable()		// csrf 해킹보안, 현재 사용하지 않기 때문에 disable
			.httpBasic().disable()	// token을 사용하기 때문에 basic인증을 disable
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)	//session기반이 아님을 선언
			.and()
			.authorizeRequests().antMatchers("/","/auth/**").permitAll() // root(/), /auth/** 경로는 인증을 안해도 된다.(인증을 허용)
			.anyRequest().authenticated();
		
		// filter에 등록
		// 매 요청마다 CorsFilter 실행한 후 jwtAuthenticationFilter를 실행
		http.addFilterAfter(jwtAuthencationFilter, CorsFilter.class);
		
		return http.build();
	}
}
