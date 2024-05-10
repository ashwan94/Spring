package kr.or.nextit.springmvc.login;

import lombok.Data;
import org.springframework.stereotype.Repository;

@Data
public class LoginRequest {
    private String id;
    private String password;
}