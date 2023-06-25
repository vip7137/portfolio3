package team.project.gomulsom.entity;


import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import team.project.gomulsom.dto.MemberDTO;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "roleSet")
public class Member extends BaseEntity{

    @Id
    private String id;

    private String pw;

    private String name;

    private String email;

    private boolean fromSocial;

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<MemberRole> roleSet = new HashSet<>();

    public void addMemberRole(MemberRole memberRole){
        roleSet.add(memberRole);
    }

}
