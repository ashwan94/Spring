package egovframework.example.login.service.impl;

import egovframework.example.login.service.LoginRequest;
import egovframework.example.login.service.LoginService;
import egovframework.example.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final LoginMapper mapper;

    @Override
    public Member findMember(LoginRequest login){
        return mapper.findMember(login);
    }
}
