package saessak.log.user.service;

import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
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
    public Long join(UserJoinDto userJoinDto){
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
    public Boolean login(UserLoginDto userLoginDto){
        User findUser = userRepository.findOptionalByProfileId(userLoginDto.getProfileId())
                .orElseThrow();
        if(findUser.getPassword().equals(userLoginDto.getPassword())){
            return true;
        }
        return false;
    }

    // 아이디 찾기
    public String findProfileId(UserFindIdDto userFindIdDto){
        User findName = userRepository.findByName(userFindIdDto.getName());
        User findEmail = userRepository.findByEmail(userFindIdDto.getEmail());

        if(findName==null || findEmail==null){
            throw new RuntimeException("등록되지 않은 회원입니다."); // or return "fail"
        } else {
            String profileId = findName.getProfileId();
            userFindIdDto.setProfileId(profileId);
            return userFindIdDto.getProfileId();
        }
    }

    // 비밀번호 찾기
    public Boolean findPassword(UserFindPasswordDto userFindPasswordDto) {
        User findName = userRepository.findByName(userFindPasswordDto.getName());
        User findProfileId = userRepository.findByProfileId(userFindPasswordDto.getProfileId());
        User findEmail = userRepository.findByEmail(userFindPasswordDto.getEmail());
        if (findName == null || findProfileId == null || findEmail == null) {
            throw new IllegalStateException("등록되지 않은 회원입니다.");
        } else {
           // 랜덤 비밀번호 생성 후 DB저장
            String newPassword = RandomStringUtils.randomAlphabetic(8);
            User resetPassword = userRepository.save(User.builder().password(newPassword).build());
            userFindPasswordDto.setNewPassword(newPassword);
            return true;
        }

    }
    // 회원정보 수정
    public void update(UserDto userDto) {
    }

    public Optional<User> findOne(Long userId){
        return userRepository.findById(userId);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

}
