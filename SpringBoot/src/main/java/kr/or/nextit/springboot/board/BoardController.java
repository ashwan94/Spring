package kr.or.nextit.springboot.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService service;

    @GetMapping("/board/list")
    public String list(Model model) {
        List<BoardVO> list = service.selectBoards();
        model.addAttribute("boards", list);
        return "board/list";
    }
    @GetMapping("/board/view")
    public String view(@RequestParam("no") long boardNo, Model model) {
        BoardVO board = service.selectBoard(boardNo);
        model.addAttribute("board", board);
        return "board/view";
    }
    @GetMapping("/board/register")
    public String viewRegister() {
        return "board/register";
    }
    @PostMapping("/board/register")
    public String register(BoardVO board) {
        service.registerBoard(board);
        return "redirect:/board/list";
    }
    @GetMapping("/board/modify")
    public String viewModify() {
        return "board/modify";
    }
    @PostMapping("/board/modify")
    public String modify(BoardVO board) {
        service.modifyBoard(board);
        return "redirect:/board/list";
    }
    @PostMapping("/board/remove")
    public String remove(@RequestParam(name = "no", defaultValue = "0") long boardNo) {
        service.removeBoard(boardNo);
        return "redirect:/board/list";
    }
}
