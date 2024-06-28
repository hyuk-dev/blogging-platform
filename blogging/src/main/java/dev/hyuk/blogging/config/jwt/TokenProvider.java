package dev.hyuk.blogging.config.jwt;

import java.time.Duration;
import java.util.Date;

import org.springframework.security.web.header.Header;
import org.springframework.stereotype.Service;

import dev.hyuk.blogging.domain.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service

public class TokenProvider {
    
    private final JwtProperties jwtProperties;

    public String generateToken(User user, Duration expiredAt) {
        Date now = new Date();
        return makeToken(new Date(now.getTime() + expiredAt.toMillis()), user);
    }

    // 1) JWT 토큰 생성 메서드
    private String makeToken(Date expiry, User user) {
        Date now = new Date();

        return Jwts.builder()
          .setHeaderParams(Header.TYPE, Header.JWT_TYPE) // 헤더 typ : JWT
          // 내용 iss : ajufresh@gmail.com (propertise 파일에서 설정한 값)
          .setIssuer(jwtProperties.getIssuer())
          .setIssuedAt(now) // 내용 iat : 현재 시간
          .setExpiration(expiry) // 내용 exp : expiry 멤버 변숫값
          .setSubject(user.getEmail()) // 내용 sub : 유저의 이메일
          .claim("id", user.getId()) // 클레임 id : 유저 ID
          // 서명 : 비밀 값과 함께 해시값을 HS256 방식으로 암호화
          .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
          .compact();

          // 2. JWT 토큰 유효성 검증 메서드

}
