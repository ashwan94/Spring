package kr.or.nextit.springboot.comment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {
    @Mock
    private CommentMapper mapper;

    @InjectMocks
    private CommentService service;

    @Test
    void registerComment() {
        CommentVO comment = CommentVO.builder().boardNo(1).writer("a001").content("첫 댓글").build();
        BDDMockito.given(service.registerComment(comment))
                .willReturn(CommentVO.builder()
                        .id(1).boardNo(1).writer("a001").content("첫 댓글")
                        .createDate(LocalDateTime.of(2024, 5, 29, 15, 50, 52))
                        .build());

        CommentVO newComment = service.registerComment(comment);

        assertEquals(newComment.getId(), comment.getId());
    }
}