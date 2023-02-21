package saessak.log.user.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import saessak.log.user.User;
import saessak.log.user.dto.*;
import saessak.log.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    // 회원가입
    @Transactional
    public Long join(UserJoinDto userJoinDto) {
        User user = userJoinDto.toEntity();
        userRepository.save(user);
        return user.getId();
    }

    // profileId 중복검사
    public void duplicateUser(UserDuplicateDto userDuplicateDto) {
        userRepository.findOptionalByProfileId(userDuplicateDto.getProfileId())
                .ifPresent(u -> {
                    throw new IllegalStateException("중복된 아이디입니다.");
                });
    }

    // 로그인
    public Boolean login(UserLoginDto userLoginDto) {
        User findUser = userRepository.findOptionalByProfileId(userLoginDto.getProfileId())
                .orElseThrow();
        if (findUser.getPassword().equals(userLoginDto.getPassword())) {
            return true;
        }
        return false;
    }

    // 아이디 찾기
    public ResponseFindIdDto findProfileId(UserFindIdDto userFindIdDto) {
        User findUser = userRepository
                .findByEmailAndName(userFindIdDto.getEmail(), userFindIdDto.getName())
                .orElseThrow(() ->
                        new IllegalStateException("등록되지 않은 회원입니다.")
                );
        ResponseFindIdDto responseFindIdDto = new ResponseFindIdDto();
        responseFindIdDto.setProfileId(findUser.getProfileId());

        return responseFindIdDto;
    }

    // 비밀번호 찾기
    @Transactional
    public ResponseResetPasswordDto findPassword(UserFindPasswordDto userFindPasswordDto) {
        User findUser = userRepository.findByUserInfo(
                userFindPasswordDto.getEmail(),
                userFindPasswordDto.getName(),
                userFindPasswordDto.getProfileId())
                .orElseThrow(()->
                        new IllegalStateException("등록되지 않은 회원입니다."));
        String resetPassword = RandomStringUtils.randomAlphabetic(8);
        findUser.changeTempPassword(resetPassword);
        ResponseResetPasswordDto responseResetPasswordDto = new ResponseResetPasswordDto();
        responseResetPasswordDto.setResetPassword(resetPassword);

        return responseResetPasswordDto;
    }

    // 회원정보 수정
    public void update(UserDto userDto) {
    }

    public Optional<User> findOne(Long userId) {
        return userRepository.findById(userId);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

}
