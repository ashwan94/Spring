package kr.or.nextit.springmvc.common;

import kr.or.nextit.springmvc.exception.MemberNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice("kr.or.nextit.springmvc")
public class CommonExceptionHandler {

    // throw new MemberNotFoundException 을 감지해서 작동함
    @ExceptionHandler(MemberNotFoundException.class)
    public String handleMemberNotFoundException() {
        return "error/memberNotFound";
    }
}
