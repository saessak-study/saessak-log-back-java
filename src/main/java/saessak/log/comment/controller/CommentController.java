package saessak.log.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import saessak.log.comment.Comment;
import saessak.log.comment.dto.CommentSaveDto;
import saessak.log.comment.dto.CommentViewDto;
import saessak.log.comment.service.CommentService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/save-comment")
    public Object saveComment(@RequestBody CommentSaveDto commentSaveDto) {
        commentService.saveComment(commentSaveDto);
        return ResponseEntity.ok("Success");
    }

//    @GetMapping("/comments")
//    public Object fetchComment() {
//        List<Comment> list = commentService.fetchCommentList();
//        return ResponseEntity.ok(list);
//    }

    @RequestMapping("/infinite-comments")
    @GetMapping(params = {"post", "limit", "page"})
    public Object infiniteComments(@RequestParam("post") Long post,
                                   @RequestParam("limit") int limit,
                                   @RequestParam("page") int page) {
        List<CommentViewDto> list = commentService.infiniteComments(post);
        return ResponseEntity.ok(list);

    }


}
