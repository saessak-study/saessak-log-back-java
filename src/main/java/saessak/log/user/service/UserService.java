package saessak.log.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import saessak.log.user.User;
import saessak.log.user.dto.UserDto;
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
    public Long join(UserDto userDto){
        User user = userDto.toEntity();
        userRepository.save(user);
        return userDto.getId();

    }

    // profileId 중복검사
    public void duplicateUser(User user) {
        List<User> findUser = (List<User>) userRepository.findByProfileId(user.getProfileId());
        if(!findUser.isEmpty()){
            throw new IllegalStateException("중복된 아이디입니다.");
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
