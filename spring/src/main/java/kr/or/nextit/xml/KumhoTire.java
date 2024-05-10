package kr.or.nextit.xml;

import org.springframework.stereotype.Component;

@Component
public class KumhoTire implements Tire {
    @Override
    public String getBrand() {
        return "금호 타이어";
    }
}
