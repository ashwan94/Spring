package org.example.aop;

import org.springframework.stereotype.Component;

@Component("gunhee")
public class Gunhee implements Student{

    @Override
    public void doSomething() throws Exception {
            System.out.println("게임을 한다.");
            throw new Exception("불이야!");
    }
}
