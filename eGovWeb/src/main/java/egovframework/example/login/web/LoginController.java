package egovframework.example.login.web;

import egovframework.example.exception.MemberNotFoundException;
import egovframework.example.login.service.LoginRequest;
import egovframework.example.login.service.LoginService;
import egovframework.example.login.service.impl.LoginServiceImpl;
import egovframework.example.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    private final LoginService service;

    @GetMapping("login")
    public String loginView() {
//        model.addAttribute("location", location); // 사용자의 이전 URI location 정보
//        model.addAttribute("boardNo", boardNo); // 사용자의 이전 게시판 번호
        return "common/login";
    }

    @PostMapping("login")
    public String login(LoginRequest login, HttpSession session, Model model) {
        Member member = service.findMember(login);

        if (member == null) {
            throw new MemberNotFoundException(); // 예외 던지기
        }
        session.setAttribute("member", member);
        return "redirect:/";

//        model.addAttribute("msg", "로그인 실패");
//        return "common/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate(); // session 의 모든 data 삭제
        return "redirect:/";
    }

    @PostMapping("/ajaxLogin") // 보안을 위해 Get 아니고 Post
    @ResponseBody
    public Map<String, String> ajaxLogin(LoginRequest login, @RequestParam(required=false) String location, @RequestParam(required=false) String boardNo, HttpSession session){
        // 응답 예
        // 성공할 경우 - {"msg" : "success"}
        // 실패할 경우 - {"msg" : "failed"}
        log.debug("LoginController 의 location : {}", login);
        log.debug("LoginController 의 boardNo : {}", boardNo);
        HashMap<String, String> map = new HashMap<>();
        Member member = service.findMember(login);
        log.debug("LoginController 조회된 멤버 정보 : {}", member);
        log.debug("LoginController 에서 감지된 session 의 URL 정보 : {}" ,session.getAttribute("redirectURL"));
        location = (String)session.getAttribute("redirectURL");
        if (member == null) {
            throw new MemberNotFoundException();
        }
        session.setAttribute("member", member);
        if (location != null && !"".equals(location)) {
            map.put("msg", location); // interceptor 를 통해 로그인하는 경우
        }else{
            map.put("msg", "/"); // 로그인 버튼으로 직접 접근하는 경우
        }
        return map;
    }

    @ExceptionHandler(MemberNotFoundException.class)
    @ResponseBody
    public Map<String, String> memberNotFound(Model model) {
        HashMap<String, String> map = new HashMap<>();
        map.put("msg", "failure");
        return map;
    }
}