package kr.or.nextit.springboot.comment;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CommentMapperTest {
    @Autowired
    private CommentMapper mapper;

    @Test
    void registerComment() {
        CommentVO comment = CommentVO.builder().boardNo(830).writer("a001").content("첫 댓글").build();
        mapper.registerComment(comment);
        CommentVO newComment = mapper.selectComment(comment.getId());
        log.info("comment: {}", comment);
        log.info("newComment: {}", newComment);
        assertEquals(comment.getId(), newComment.getId());
    }
}