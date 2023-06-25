package team.project.gomulsom.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import team.project.gomulsom.entity.Member;
import team.project.gomulsom.entity.MemberRole;
import team.project.gomulsom.repository.MemberRepository;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class MemberTests {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void insertDummieAdmin(){

        IntStream.rangeClosed(1, 1).forEach(i -> {
            Member member = Member.builder()
                    .id("admin")
                    .pw(passwordEncoder.encode("admin"))
                    .name("관리자")
                    .email("admin@gmail.com")
                    .fromSocial(false)
                    .build();

            member.addMemberRole(MemberRole.ADMIN);

            memberRepository.save(member);
        });
    }

    @Test
    public void testRead() {

        Optional<Member> result = memberRepository.findByMemberId("admin", false);

        Member member = result.get();

        System.out.println(member);
    }
}
