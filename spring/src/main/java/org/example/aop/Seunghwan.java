package org.example.aop;

import org.springframework.stereotype.Component;

@Component
public class Seunghwan implements Student{
    @Override
    public void doSomething() {
//        System.out.println("현관문을 열고 집에 들어온다");
        try{
            System.out.println("GitHub 를 본다.");
        }catch(Exception e){
            if(e.getMessage().equals("불이야!")){
                System.out.println("119에 신고한다");
            }
        }
//        System.out.println("잠을 잔다");
//        System.out.println("현관문을 닫고 집을 나온다");
    }
}
