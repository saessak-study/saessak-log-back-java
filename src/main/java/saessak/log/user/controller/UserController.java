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
        return ResponseEntity.ok().build();
    }

    // 아이디 중복검사
    @PostMapping("/duplicate")
    public ResponseEntity duplicateProfileId(@RequestBody UserDuplicateDto userDuplicateDto){
        try{
            userService.duplicateUser(userDuplicateDto);
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e){
            return ResponseEntity.badRequest().build();
        }

    }
    // 회원정보 수정
    @PatchMapping("/update/{id}")
    public ResponseEntity<UserDto> update(@PathVariable("id") long id, @RequestBody UserDto userDto) {
        userService.update(userDto);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserLoginDto userLoginDto) {
        if(userService.login(userLoginDto)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    // 아이디 찾기
    @PostMapping("/findId")
    public ResponseEntity<ResponseFindIdDto> findProfileId(@RequestBody UserFindIdDto userFindIdDto){
        ResponseFindIdDto profileId = userService.findProfileId(userFindIdDto);
        return ResponseEntity.status(HttpStatus.OK).body(profileId);
    }

    // 비밀번호 찾기
    @PostMapping("/resetPassword")
    public ResponseEntity<ResponseResetPasswordDto> findPassword(@RequestBody UserFindPasswordDto userFindPasswordDto) {
        ResponseResetPasswordDto resetPassword = userService.findPassword(userFindPasswordDto);
        return ResponseEntity.status(HttpStatus.OK).body(resetPassword);
    }
}
