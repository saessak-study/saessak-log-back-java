package saessak.log.subscription.controller;


import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import saessak.log.post.service.PostService;
import saessak.log.subscription.dto.SubscriptionDto;
import saessak.log.subscription.service.SubscriptionService;
import saessak.log.user.service.UserService;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private final UserService userService;
    private final PostService postService;

    @ApiOperation(value = "구독")
    @GetMapping("/subscribe/{post}")
    public Object subscribe(@PathVariable(value = "post") Long post,
                            @RequestBody SubscriptionDto subscriptionDto) {
        Long toUserId = postService.findUserIndexByPostId(post);
        Long fromUserId = subscriptionDto.getUser();
        subscriptionService.subscribe(fromUserId,toUserId);
        return ResponseEntity.ok("Success");
    }


}
