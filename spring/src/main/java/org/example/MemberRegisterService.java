package org.example;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MemberRegisterService {
    private MemberDao memberDao;

    // 필드를 파라미터로 갖는 생성자를 선언하기만 하면
    // 자동으로 스프링 빈을 주입해준다.
    public MemberRegisterService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public Long regist(RegisterRequest request) {
        Member member = memberDao.selectByEmail(request.getEmail());
        if (member != null) {
            throw new DuplicateMemberException("duplicate email: " + request.getEmail());
        }
        Member newMember = new Member(request.getEmail(), request.getPassword(), request.getName(), LocalDateTime.now());
        memberDao.insert(newMember);
        return newMember.getId();
    }
}
