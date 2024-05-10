package org.example.factorial;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class FactorialAspect {
    // 수용 받는 parameter 에 대한 기호
    // * : 모든
    // .. : 0개 이상

    @Around("execution(long org.example.factorial.Calculator.factorial(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
        long start = System.nanoTime();

        // 실제 factorial() 호출
        Object result = joinPoint.proceed();

        long end = System.nanoTime();
        System.out.println("걸린 시간 : " + (end - start));

        return result;
    }
}

