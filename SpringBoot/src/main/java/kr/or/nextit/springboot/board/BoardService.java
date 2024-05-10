package kr.or.nextit.springboot.board;

import kr.or.nextit.springboot.file.FileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardMapper mapper;
    private final FileMapper fileMapper;

    public List<BoardVO> selectBoards() {
        return mapper.selectBoards();
    }
    @Transactional
    public BoardVO selectBoard(long boardNo) {
        mapper.updateHits(boardNo);
        return mapper.selectBoard(boardNo);
    }
    @Transactional
    public void registerBoard(BoardVO board) {
        mapper.registerBoard(board);
        board.getFileList().forEach(f -> f.setBoardNo(board.getNo()));
        fileMapper.saveFiles(board.getFileList());
    }
    public void modifyBoard(BoardVO board) {
        mapper.modifyBoard(board);
    }
    public void removeBoard(long boardNo) {
        mapper.removeBoard(boardNo);
    }
}
