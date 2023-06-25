package team.project.gomulsom.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import team.project.gomulsom.dto.MemberDTO;
import team.project.gomulsom.repository.MemberRepository;

@RequiredArgsConstructor
@Component
public class CheckEmailValidator extends AbstractValidator<MemberDTO>{
    private final MemberRepository repository;
    @Override
    protected void doValidate(MemberDTO dto, Errors errors) {
        if (repository.existsByEmail(dto.toEntity().getEmail())) {
            errors.rejectValue("email", "이메일 중복 오류", "이미 사용중인 이메일 입니다.");
        }
    }
}
