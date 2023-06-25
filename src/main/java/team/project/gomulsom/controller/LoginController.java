package team.project.gomulsom.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import team.project.gomulsom.dto.MemberDTO;
import team.project.gomulsom.security.service.MemberUserDetailService;

import javax.validation.Valid;
import java.util.Map;


@Controller
@RequiredArgsConstructor
public class LoginController {

    private final MemberUserDetailService memberUserDetailService;

    // 로그인 기본 화면
    @PreAuthorize("permitAll()")
    @GetMapping("/login_cat")
    public String logincat(){

        return "login_cat";
    }

    // 회원가입 화면
    @GetMapping("/signup")
    public void signup(MemberDTO memberDTO){

    }

    // 회원가입 등록
    @PostMapping("/signup")
    public String signup(@Valid MemberDTO memberDTO, Errors errors, Model model){
        if(errors.hasErrors()){
            model.addAttribute("memberDTO",memberDTO);

            Map<String, String> validatorResult = memberUserDetailService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }
            return "/signup";
        }
        memberUserDetailService.Id(memberDTO);

        return "redirect:/login_cat";
    }
}
