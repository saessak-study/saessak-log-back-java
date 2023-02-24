package saessak.log.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import saessak.log.user.dto.*;
import saessak.log.user.service.UserService;

import javax.servlet.http.HttpServletResponse;

@RestController //  RestController 데이터 전송
@RequiredArgsConstructor // 생성자
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/join")
    public ResponseEntity createUser(@RequestBody UserJoinDto userJoinDto) {
        userService.join(userJoinDto);
        return ResponseEntity.ok().body("회원가입이 성공 하였습니다.");
    }

    // 아이디 중복검사
    @PostMapping("/duplicate")
    public ResponseEntity duplicateProfileId(@RequestBody UserDuplicateDto userDuplicateDto) {
        try {
            userService.duplicateUser(userDuplicateDto);
            return ResponseEntity.ok().body("가입 가능한 아이디입니다.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("이미 가입하신 회원입니다.");
        }

    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserLoginDto userLoginDto) {
        if (userService.login(userLoginDto)) {
            return ResponseEntity.ok().body("로그인 성공");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("비밀번호가 틀렸습니다.");
    }

    // 아이디 찾기
    @PostMapping("/findId")
    public ResponseEntity<ResponseFindIdDto> findProfileId(@RequestBody UserFindIdDto userFindIdDto) {
        ResponseFindIdDto profileId = userService.findProfileId(userFindIdDto);
        return ResponseEntity.status(HttpStatus.OK).body(profileId);
    }

    // 비밀번호 찾기
    @PostMapping("/resetPassword")
    public ResponseEntity<ResponseResetPasswordDto> findPassword(@RequestBody UserFindPasswordDto userFindPasswordDto) {
        ResponseResetPasswordDto resetPassword = userService.findPassword(userFindPasswordDto);
        return ResponseEntity.status(HttpStatus.OK).body(resetPassword);
    }

    // 비밀번호 변경
    @PatchMapping("/update")
    public ResponseEntity update(@RequestBody ChangePasswordDto changePasswordDto) {
        userService.update(changePasswordDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 마이페이지 유저정보(미완)
    @PostMapping("/information")
    public ResponseEntity<UserInformationDto> userInformation(@RequestBody UserInformationDto userInformationDto){
        userService.userInformation(userInformationDto);
        return ResponseEntity.ok().body(userInformationDto);
    }
}
