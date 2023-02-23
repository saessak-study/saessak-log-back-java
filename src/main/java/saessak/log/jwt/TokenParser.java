package saessak.log.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import saessak.log.jwt.dto.TokenDto;
import saessak.log.jwt.dto.TokenToUserDto;
import saessak.log.user.User;
import saessak.log.user.repository.UserRepository;

@RequiredArgsConstructor
@Component
public class TokenParser {

    private final UserRepository userRepository;

    private final JwtTokenProvider jwtTokenProvider;

    public TokenToUserDto parseToken(String accessToken) {
        //일단 payload 를 profileId로 함.
        jwtTokenProvider.validateAbleToken(accessToken);
        String profileId = jwtTokenProvider.getPayload(accessToken);
        User user = userRepository.findByProfileId(profileId);
        TokenToUserDto tokenToUserDto = new TokenToUserDto(user);
        return tokenToUserDto;
    }

    public TokenDto tokenReissue(String refreshToken) {
        jwtTokenProvider.validateAbleToken(refreshToken);
        String payload = jwtTokenProvider.getPayload(refreshToken);
        return jwtTokenProvider.createToken(payload);
    }

}