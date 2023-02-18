package saessak.log.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import saessak.log.user.repository.UserRepository;


@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired UserService userService;
    @Autowired UserRepository userRepository;

//    @Test
//    void 회원가입() {
//       // given
//        UserDto userDto = UserDto.builder()
//                .profileId("sassac")
//                .password("1234")
//                .email("email@a.com")
//                .name("gunhee").build();
//       // when
//       // Long saveId = userService.join(userDto.toEntity());
//        // then
//        User findUser = userService.findOne(saveId).get();
//        assertThat(userDto.getProfileId()).isEqualTo(findUser.getProfileId());
//    }
//
//
//    @Test
//    void 중복검사_예외() {
//        // given
//        User user1 = User.builder()
//                .profileId("saessac3")
//                .password("1234")
//                .email("saessac@email.com")
//                .name("gunhee1").build();
//
//        User user2 = User.builder()
//                .profileId("saessac3")
//                .password("1234")
//                .email("saessac@email.com")
//                .name("gunhee1").build();
//        // when
//        userService.join(user1);
//        //then
//        try {
//            userService.join(user2);
//            fail("예외가 발생해야 합니다.");
//        }catch (IllegalStateException e){
//            assertThat(e.getMessage()).isEqualTo("중복된 아이디입니다.");
//        }
//    }
//
//    @Test
//    void findOne() {
//    }
//
//    @Test
//    void findAll() {
//    }
}