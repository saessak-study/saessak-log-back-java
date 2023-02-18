package saessak.log.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import saessak.log.jwt.dto.TokenDto;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // 일단 access 도 하루로.
    private final long accessTokenValidityInMilliseconds = 8640000;

    // 만료기간은 일단 하루.
    private final long refreshTokenValidityInMilliseconds = 86400000;

    //payload 에 뭐로 할지. 유저의 등록한 아이디??
    //service 에서 로그인시 토큰을 만들고 TokenDto 로 return. 혹은 재발행 (재발행시 validate 실행하고 토큰 다시 생성). refresh 토큰을 갖고있어야함.
    //그럼 게시글 생성, 댓글 생성 등에서 계속 token 을 갖고있는지 확인해야하나?
    public TokenDto createToken(String payload) {

        long now = (new Date()).getTime();

        String accessToken = Jwts.builder()
            .setSubject(payload) //토큰에 담을 데이터?
            .setIssuedAt(new Date(now))
            .setExpiration(new Date(now + accessTokenValidityInMilliseconds))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();

        String refreshToken = Jwts.builder()
            .setSubject(payload) //토큰에 담을 데이터?
            .setIssuedAt(new Date(now))
            .setExpiration(new Date(now + refreshTokenValidityInMilliseconds))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();

        //jwtToken 생성해줌.
        return TokenDto.of(accessToken, refreshToken);
    }

    //토큰을 다시 payload 로 반환.
    public String getPayload(String token) {
        return tokenToJws(token).getBody().getSubject();
    }

    //catch 부분은 throw 로 예외를 발생시키면 controller 나 service 에서 받아서 response 에 에러코드로 보내줘도될듯.
    public boolean validateAbleToken(String token) {
        try {
            tokenToJws(token);
            return true;
        } catch (MalformedJwtException error) {
            log.error("잘못된 서명");
        } catch (ExpiredJwtException error) {
            log.error("유효기간이 만료된 jwt 토큰");
        } catch (UnsupportedJwtException error) {
            log.error("다른 형식의 jwt 토큰");
        } catch (IllegalArgumentException error) {
            log.error("잘못된 jwt 토큰");
        }

        return false;
    }

    private Jws<Claims> tokenToJws(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token);
    }

}