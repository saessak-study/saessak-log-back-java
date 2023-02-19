package saessak.log.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import saessak.log.user.User;
import saessak.log.user.dto.UserDto;
import saessak.log.user.dto.UserDuplicateDto;
import saessak.log.user.dto.UserJoinDto;
import saessak.log.user.dto.UserLoginDto;
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
