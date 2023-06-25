package team.project.gomulsom.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import team.project.gomulsom.dto.MemberDTO;
import team.project.gomulsom.repository.MemberRepository;

import javax.validation.ConstraintViolation;
import javax.validation.executable.ExecutableValidator;
import javax.validation.metadata.BeanDescriptor;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class CheckUsernameValidator extends AbstractValidator<MemberDTO> {

    private final MemberRepository repository;
    @Override
    protected void doValidate(MemberDTO dto, Errors errors) {
        if (repository.existsById(dto.toEntity().getId())) {
            errors.rejectValue("id", "아이디 중복 오류", "이미 사용중인 아이디 입니다.");
        }
    }

}