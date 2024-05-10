package egovframework.example.login.service;

import egovframework.example.login.service.LoginRequest;
import egovframework.example.member.Member;

public interface LoginService {
    Member findMember(LoginRequest login);
}
