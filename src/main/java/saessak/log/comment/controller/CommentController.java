package saessak.log.comment.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import saessak.log.comment.dto.CommentSaveDto;
import saessak.log.comment.service.CommentService;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CommentController {

    private final CommentService commentService;

    @ApiOperation(value = "댓글작성")
    @PostMapping("/save-comment")
    public Object saveComment(@RequestBody CommentSaveDto commentSaveDto) {
        commentService.saveComment(commentSaveDto);
        return ResponseEntity.ok("성공");
    }
}
