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
    public ResponseEntity<String> createUser(@RequestBody UserJoinDto userJoinDto) {
        userService.join(userJoinDto);
        return ResponseEntity.ok().body("회원가입이 성공 하였습니다.");
    }

    // 아이디 중복검사
    @PostMapping("/duplicate")
    public ResponseEntity duplicateProfileId(@RequestBody UserDuplicateDto userDuplicateDto) {
        try {
            userService.duplicateUser(userDuplicateDto);
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserLoginDto userLoginDto) {
        if (userService.login(userLoginDto)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
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
}
