package kr.or.nextit.xml;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
//@ComponentScan(basePackages = "kr.or.nextit.xml")
public class AppCtx {
    @Bean
    public Driver driver() {
        return new Driver();
    }
    @Bean
    public Tire hankookTire() {
        return new HankookTire();
    }
    @Bean
    public Car car() {
        return new Car(hankookTire());
    }
}
