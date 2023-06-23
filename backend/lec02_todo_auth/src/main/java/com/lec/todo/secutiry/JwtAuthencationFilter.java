package com.lec.todo.secutiry;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtAuthencationFilter extends OncePerRequestFilter {	// 시큐리티는 필터
																	// OncePerRequestFilter는 한번만 쓸거.
	// 웹에서 요청이 들어오면 서버에 접근하기 전 여러개의 필터를 줄 수 있음.
	
	@Autowired
	private TokenProvider tokenProvider;
	
	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain)
			throws ServletException, IOException {
		
		try {
			// 요청에서 token 가져오기
			String token = parseBearerToken(req);
			System.out.println("token = " + token);
			
			log.info("Filter is Running...");
			
			// 토큰 검증하기 : JWT이므로 인가서버에 요청하지 않고도 검증 가능
			if(token != null && !token.equalsIgnoreCase("null")) {
				// userId 가져오기, 위변조된 경우에 예외를 발생
				String userId = tokenProvider.validateAndGetUserId(token);
				
				log.info("Authenticated User Id : " + userId);
				
				// 인증완료 : SecurityContextHolder에 등록해야 인증된 사용자로 인식됨.
				AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userId, // 인증된 사용자ID. 문자열이 아니어도 아무거나 저장가능. 통상적으로 UserDetail객체를 저장, 우리는 안들었음.
						null,
						AuthorityUtils.NO_AUTHORITIES);
				
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
				SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
				securityContext.setAuthentication(authentication);
				SecurityContextHolder.setContext(securityContext);
			}
			
		} catch (Exception e) {
			logger.error("Security Context에 사용자인증 정보가 없습니다", e);
		}
		filterChain.doFilter(req, res);
		
	}

	
	
	private String parseBearerToken(HttpServletRequest req) {
		// http요청의 헤더를 파싱해서 Bearer토큰을 리턴
		String bearerToken = req.getHeader("Authorization");
		System.out.println("req.getHeader(Authorization) : " + bearerToken);
		
		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		} else return null;
	}

}
