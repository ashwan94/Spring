package kr.or.nextit.xml;

import org.springframework.stereotype.Component;

@Component
public class HankookTire implements Tire {
    @Override
    public String getBrand() {
        return "한국 타이어";
    }
}
