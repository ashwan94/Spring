package org.example.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

// AOP 설정 파일이라고 스프링에게 알려주는 어노테이션
@Aspect
@Component
public class StudentAspect {
    @Pointcut("execution(void org.example.aop.Student.doSomething())")
    // 실제 실행할 method 를 정의해준다
    public void studentTarget(){}

    @Before("studentTarget()")
    public void before(JoinPoint joinPoint){
        String className = joinPoint.getTarget().getClass().getSimpleName();
        // parameter 로 들어온 class 의 이름을 가져옴

        System.out.println();
        System.out.println(className + "님이 실행함");
        System.out.println("현관문을 열고 집에 들어온다");
    }

    @After("studentTarget()")
    public void after(JoinPoint joinPoint){
        String className = joinPoint.getTarget().getClass().getSimpleName();
        // parameter 로 들어온 class 의 이름을 가져옴

        System.out.println("잠을 잔다");
        System.out.println("현관문을 닫고 집을 나온다");
    }

    // 모든 Advice 를 여기서 처리할 수 있다
    @Around("studentTarget()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
        before(joinPoint);

        try {
            joinPoint.proceed(); // doSomething() 이라는 method 를 갖고 있다
        } catch (Exception e) {
            if(e.getMessage().equals("불이야!")){
                System.out.println("119에 신고합니다.");
            }
        }

        after(joinPoint);

        return null;
    }
}
