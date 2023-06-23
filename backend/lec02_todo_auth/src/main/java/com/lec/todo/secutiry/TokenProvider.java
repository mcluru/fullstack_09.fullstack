package com.lec.todo.secutiry;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;import javax.print.attribute.standard.Chromaticity;

import org.springframework.stereotype.Service;

import com.lec.todo.model.UserEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TokenProvider {
	
	private static final String SECRET_KEY = "rkGU45258GGhiolLO2465TFY5345kGU45258GGhiolLO2465TFY5345";
	
	public String create(UserEntity userEntity) {
		
		System.out.println(SECRET_KEY.length());
		
		
		// token�� ��ȿ�Ⱓ����
	    Date expiryDate = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));
	    
	    // jwt token�� ����
	    return Jwts.builder()
	    		//.signWith(SignatureAlgorithm.HS512, SECRET_KEY)
	    		.signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY))) // header�� ����� ���� �� ������ ���� SECRET_KEY
	    		.setSubject(userEntity.getId()) // payload�� �� ����
	    		.setIssuer("Todo Application")  // token �߱���
	    		.setIssuedAt(new Date())        // token �߱���
	    		.setExpiration(expiryDate)      // token ��ȿ�Ⱓ
	    		.compact();
	}
	
	public String validateAndGetUserId(String token) {
		// ����� ���̷ε带 setSigningKey�� �Ѿ�� ��ũ��Ű�� �̿��� ������ ��
		// token�� ����� ��, ������ ���� �ʾҴٸ� ���̷ε�(claims)�� ����.
		// �ƴ϶�� ���ܸ� �߻�����, ��������� �� userId�� �ʿ��ϱ� ������ getBody()�� ȣ��
		Claims claims = Jwts.parserBuilder()
				.setSigningKey(Decoders.BASE64.decode(SECRET_KEY))
				.build()
				.parseClaimsJws(token)
				.getBody();
//		Claims claims = Jwts.parser()
//				.setSigningKey(SECRET_KEY)
//				.parseClaimsJws(token)
//				.getBody();
		
		return claims.getSubject();
	}
	
}
