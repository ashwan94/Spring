package kr.or.nextit.xml;

import org.springframework.stereotype.Component;

@Component
public class Driver {
    public void printBrand(Car car) {
        String tireBrand = car.getTireBrand();
        System.out.println(tireBrand);
    }
}
