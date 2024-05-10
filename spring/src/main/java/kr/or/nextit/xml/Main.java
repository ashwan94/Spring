package kr.or.nextit.xml;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
//        ApplicationContext context = new AnnotationConfigApplicationContext(AppCtx.class);
        ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");

//        Driver driver = new Driver();
//        HankookTire hankookTire = new HankookTire();
//
//        Car car = new Car(hankookTire);
//
//        driver.printBrand(car);
        Driver driver = context.getBean("driver", Driver.class);
        Car car = context.getBean("car", Car.class);
        driver.printBrand(car);
    }
}
