package team.project.gomulsom.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {

    // 페이지 권한 부여
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.formLogin()
                .loginPage("/login_cat")
                .defaultSuccessUrl("/main");
        http.csrf().disable();
        http.logout()
                .logoutSuccessUrl("/login_cat")
                .invalidateHttpSession(true);

        http.oauth2Login()
                .loginPage("/login_cat")
                .defaultSuccessUrl("/main")
                .authorizationEndpoint()
                .baseUri("/login/oauth2/authorization");


        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
