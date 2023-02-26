package saessak.log.user.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import saessak.log.jwt.TokenProvider;
import saessak.log.jwt.dto.TokenDto;
import saessak.log.user.dto.*;
import saessak.log.user.service.UserService;

@RestController //  RestController 데이터 전송
@RequiredArgsConstructor // 생성자
@RequestMapping(value = "/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "회원가입")
    @PostMapping("/join")
    public ResponseEntity createUser(@RequestBody UserJoinDto userJoinDto) {
        userService.join(userJoinDto);
        return ResponseEntity.ok().body("회원가입이 성공 하였습니다.");
    }

    @ApiOperation(value = "아이디 중복검사")
    @PostMapping("/duplicate")
    public ResponseEntity duplicateProfileId(@RequestBody UserDuplicateDto userDuplicateDto) {
        try {
            userService.duplicateUser(userDuplicateDto);
            return ResponseEntity.ok().body("가입 가능한 아이디입니다.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("이미 가입하신 회원입니다.");
        }

    }

    @ApiOperation(value = "로그인")
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody UserLoginDto userLoginDto) {
        TokenDto token = userService.login(userLoginDto);
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

    @ApiOperation(value = "아이디 찾기")
    @PostMapping("/findId")
    public ResponseEntity<ResponseFindIdDto> findProfileId(@RequestBody UserFindIdDto userFindIdDto) {
        ResponseFindIdDto profileId = userService.findProfileId(userFindIdDto);
        return ResponseEntity.status(HttpStatus.OK).body(profileId);
    }

    @ApiOperation(value = "비밀번호 찾기")
    @PostMapping("/resetPassword")
    public ResponseEntity<ResponseResetPasswordDto> findPassword(@RequestBody UserFindPasswordDto userFindPasswordDto) {
        ResponseResetPasswordDto resetPassword = userService.findPassword(userFindPasswordDto);
        return ResponseEntity.status(HttpStatus.OK).body(resetPassword);
    }

    @ApiOperation(value = "비밀번호 변경")
    @PatchMapping("/update")
    public ResponseEntity update(@RequestBody ChangePasswordDto changePasswordDto) {
        userService.update(changePasswordDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation(value = "마이페이지 미완")
    @PostMapping("/information")
    public ResponseEntity<UserInformationDto> userInformation(@RequestBody UserInformationDto userInformationDto) {
        userService.userInformation(userInformationDto);
        return ResponseEntity.ok().body(userInformationDto);
    }
}
