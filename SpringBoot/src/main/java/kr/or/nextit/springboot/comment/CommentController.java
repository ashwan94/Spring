package kr.or.nextit.springboot.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

// @Controller + @ResponseBody
@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService service;

    @PostMapping("/comment/register")
    public CommentVO register(CommentVO comment) {
        return service.registerComment(comment);
    }
}
