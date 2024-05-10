package kr.or.nextit.springboot.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("username: {}", username);
        MemberVO member = mapper.findMemberById(username);
        log.info("member: {}", member);
        return User.builder()
                .username(member.getId())
                .password(member.getPassword())
                .roles(member.getAuthList().stream().map(AuthorityVO::getRole).toList().toArray(new String[0]))
                .build();
    }
}
