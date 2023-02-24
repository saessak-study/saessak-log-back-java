package saessak.log.reaction.Controller;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Example;
import io.swagger.annotations.ExampleProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import saessak.log.common.BaseResponse;
import saessak.log.reaction.dto.ReactionDto;
import saessak.log.reaction.service.ReactionService;
import saessak.log.user.User;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ReactionController {

    private final ReactionService reactionService;

    @ApiOperation(
            value = "좋아요 Toggle 기능"
    )
    @ApiResponse(code = 200, message = "좋아요 기능을 키고 끄는 기능입니다.", response = BaseResponse.class)
    @PostMapping("/like/{post}")
    public Object reaction(@PathVariable(value = "post") Long post,
                           @RequestBody ReactionDto reactionDto) {
        boolean like = reactionService.reaction(post, reactionDto.getUserId());
        String responseMessage;
        if (like) responseMessage = "해당 게시글을 좋아합니다.";
        else responseMessage = "해당 게시글의 좋아요를 해제합니다.";
        return ResponseEntity.ok().body(BaseResponse.builder().responseMessage(responseMessage).build());
    }

}
