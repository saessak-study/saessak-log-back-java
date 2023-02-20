package saessak.log.user.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import saessak.log.user.User;
import saessak.log.user.dto.UserDuplicateDto;
import saessak.log.user.dto.UserJoinDto;
import saessak.log.user.repository.UserRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired UserService userService;
    @Autowired UserRepository userRepository;


    @Test
    void 회원가입() {
       // given
        UserJoinDto userJoinDto = new UserJoinDto();
        userJoinDto.setProfileId("gunhee2");
       // when
        Long saveId = userService.join(userJoinDto);
        // then
        User findUser = userService.findOne(saveId).get();
        assertThat(userJoinDto.getProfileId())
                .isEqualTo(findUser.getProfileId());
        System.out.println(userJoinDto.getProfileId());
    }

    @Test
    void 중복검사_예외() {
        // given
        User user1 = User.builder()
                .profileId("gunhee3")
                .password("1234")
                .email("saessac@email.com")
                .name("gunhee1").build();
        userRepository.save(user1);
        // when
        UserDuplicateDto userDuplicateDto = new UserDuplicateDto();
        userDuplicateDto.setProfileId("gunhee3");

        assertThatThrownBy(() -> userService.duplicateUser(userDuplicateDto))
                .isInstanceOf(IllegalStateException.class);
        //then

    }

    @Test
    void findOne() {
    }

    @Test
    void findAll() {
    }
}