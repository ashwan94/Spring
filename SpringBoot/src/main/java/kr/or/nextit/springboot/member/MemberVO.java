package kr.or.nextit.springboot.member;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class MemberVO {
    private String id;
    private String name;
    private String password;
    private String email;
    private LocalDate createDate;
    private LocalDate modifyDate;
    private List<AuthorityVO> authList;
}
