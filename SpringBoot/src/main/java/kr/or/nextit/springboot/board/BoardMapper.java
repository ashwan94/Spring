package kr.or.nextit.springboot.board;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    List<BoardVO> selectBoards();
    BoardVO selectBoard(long boardNo);
    void updateHits(long boardNo);
    void registerBoard(BoardVO board);
    void modifyBoard(BoardVO board);
    void removeBoard(long boardNo);
}
