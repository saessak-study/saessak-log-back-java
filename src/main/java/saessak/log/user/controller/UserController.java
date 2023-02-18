package saessak.log.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import saessak.log.user.dto.UserDto;
import saessak.log.user.service.UserService;

import javax.servlet.http.HttpServletResponse;

@RestController //  RestController 데이터 전송
@RequiredArgsConstructor // 생성자
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/userJoin")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        userService.join(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }

    // 회원정보 수정
    @PatchMapping("/userUpdate")
    public ResponseEntity<UserDto> update(@PathVariable("id") long id, @RequestBody UserDto userDto) {
        userService.update(userDto);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    // 로그인
    @PostMapping("/userLogin")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserDto> login(
            @RequestBody UserDto userDto, HttpServletResponse response) {
        return null;
    }
}
