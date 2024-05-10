package org.example.aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class StudentExample {
    public static void main(String[] args) throws Exception {
        ApplicationContext context = new AnnotationConfigApplicationContext(StudentApplicationContext.class);

        Student gunhee = context.getBean("gunhee", Student.class);
        gunhee.doSomething();
        // @Aspect 를 Bean 객체로 가져올때 해당 class data 가 아니라 interface 를 가져와야한다

        Student junghyun = context.getBean("junghyun", Student.class);
        junghyun.doSomething();

        Student yongseok = context.getBean("yongseok", Student.class);
        yongseok.doSomething();

        Student seunghwan = context.getBean("seunghwan", Student.class);
        seunghwan.doSomething();
    }
}
