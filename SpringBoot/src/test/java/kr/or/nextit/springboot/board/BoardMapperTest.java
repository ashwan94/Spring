package kr.or.nextit.springboot.board;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@MybatisTest
// 실제 데이터베이스에 연결할 경우 설정해줌
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BoardMapperTest {
    @Autowired
    private BoardMapper mapper;

    @Test
    void selectBoards() {
        List<BoardVO> boards = mapper.selectBoards();
        log.debug("boards: {}", boards);
        assertNotNull(boards);
        assertEquals(31, boards.size());
    }

    @Test
    void registerBoard() {
        // TDD(Test Driven Development)
//        BoardVO board = new BoardVO();
//        board.setWriter("a001");
//        board.setTitle("테스트 제목");
//        board.setContent("테스트 내용");
        BoardVO board = BoardVO.builder().writer("a001").title("테스트 제목").content("테스트 내용").build();

        mapper.registerBoard(board);

        List<BoardVO> boards = mapper.selectBoards();
        assertEquals(32, boards.size());
    }

    @Test
    void modifyBoard() {
//        BoardVO board = new BoardVO();
//        board.setNo(830);
//        board.setTitle("테스트 코드");
//        board.setContent("테스트 코드를 실행하여 수정을 진행함");
//        board.setWriter("쵸파");

        new StringBuilder().append("abc").append("def").toString();
        BoardVO board = BoardVO.builder().no(830).writer("쵸파").title("테스트 코드").content("테스트 코드를 실행하여 수정을 진행함").build();
        mapper.modifyBoard(board);
        assertEquals(board.getTitle(), mapper.selectBoard(830).getTitle());
    }

    @Test
    void selectBoard() {
        BoardVO board = mapper.selectBoard(804);
        log.debug("board: {}", board);
    }
}