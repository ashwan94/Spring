package kr.or.nextit.springboot.comment;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper {
    CommentVO selectComment(long id);
    void registerComment(CommentVO comment);
}
