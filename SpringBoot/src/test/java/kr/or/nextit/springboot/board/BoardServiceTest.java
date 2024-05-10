package kr.or.nextit.springboot.board;

import kr.or.nextit.springboot.file.FileMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class BoardServiceTest {
    @Mock
    private BoardMapper mapper;

    @InjectMocks
    private BoardService boardService;

    @Test
    void selectBoard() {
        // BDD(Behavior Driven Development)
        // given / when / then

        // 804,a001,이미지,첨부파일,2024-05-20 17:24:47,,29
        // Builder Pattern: 디자인 패턴 중 하나
        // 객체에 값을 저장할 때 사용하는 패턴 중의 하나
        // 1. Telescoping Constructor pattern
        // 2. Java Beans pattern
        // 3. Builder pattern: 메소드 체인 방식으로 메소드에 값을 추가하는 방식으로 사용
//        new BoardVO(804, "a001", "이미지", "첨부파일", LocalDate.of(2024, 5, 20), 29);
        // given
        BDDMockito.given(boardService.selectBoard(804))
                .willReturn(BoardVO.builder().no(804).writer("a001").title("이미지").content("첨부파일").createDate(LocalDate.of(2024, 5, 20)).hits(30).build());
        // when
        BoardVO board = boardService.selectBoard(804);
        log.info("board: {}", board.toString());
        // then
//        assertNotNull(board);
        BoardVO build = BoardVO.builder().no(804).writer("a001").title("이미지").content("첨부파일").createDate(LocalDate.of(2024, 5, 20)).hits(30).build();
        assertEquals(build, board);
//        assertEquals(30, board.getHits());
//        BDDMockito.verify(mapper).selectBoard(804);
    }

    @Test
    void registerBoard() {
    }
}