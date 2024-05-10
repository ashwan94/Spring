package kr.or.nextit.springmvc.common;

import kr.or.nextit.springmvc.member.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Slf4j
@Component
public class AuthCheckInterceptor implements HandlerInterceptor {

    // return : true => 사용자가 원래 가려던 사이트로 이동
    // return : false => 개발자가 유도하는 사이트로 이동시켜줌(redirect 필요)
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Member member = (Member) session.getAttribute("member");

//        log.debug("가져와진 queryString : {}", queryString);
//        log.debug("요청 정보 : {}", boardNo);
        String requestURI = request.getRequestURI(); // 사용자의 이전 경로값 확인
        String queryString = request.getQueryString();
        String redirectURL = requestURI + (queryString != null ? "?" + queryString : "");
// parameter 가 추가로 들어가야할 때 & 를 붙여줘야한다면 어떻게 처리할 것인가?

        log.debug("인터셉터 조회된 회원정보 : {}", member);
        if (member == null) {
            session.setAttribute("redirectURL", redirectURL);
            response.sendRedirect("/login");
            return false;
        }
        log.debug("성공적으로 interceptor 접근 : {}", redirectURL);
        session.removeAttribute("redirectURL");
        return true;
    }
}
