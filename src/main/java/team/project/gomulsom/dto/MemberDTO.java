package team.project.gomulsom.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import team.project.gomulsom.entity.Member;
import team.project.gomulsom.entity.MemberRole;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberDTO {

    @NotEmpty(message = "아이디(닉네임)을 입력해주세요.")
    @Size(min = 2, max = 10, message = "아이디(닉네임)은 2자 이상 10자 이하로 입력해주세요")
    private String id;

    @NotEmpty(message = "비밀번호를 입력해주세요")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요.")
    private String pw;

    private String name;

    @NotEmpty(message = "이메일 주소를 입력해주세요.")
    @Email(message = "올바른 이메일 주소를 입력해주세요.")
    private String email;

    public Member toEntity(){
        Member member = Member.builder()
                .id(id)
                .pw(pw)
                .name(name)
                .email(email)
                .fromSocial(false)
                .build();

        member.addMemberRole(MemberRole.USER);

        return member;

    }

}
