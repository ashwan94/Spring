package kr.or.nextit.springboot.member;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {
    MemberVO findMemberById(String id);

    List<MemberVO> selectMembers();
    void changePassword(MemberVO member);
}
