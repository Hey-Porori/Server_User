package porori.backend.user.global.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import porori.backend.user.domain.user.domain.service.UserValidationService;
import porori.backend.user.global.config.security.jwt.JwtAccessDeniedHandler;
import porori.backend.user.global.config.security.jwt.JwtAuthenticationEntryPoint;
import porori.backend.user.global.config.security.jwt.JwtSecurityConfig;
import porori.backend.user.global.config.security.jwt.TokenUtil;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenUtil tokenUtil;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final UserValidationService userValidationService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resource/**", "/css/**", "/js/**", "/img/**", "/lib/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()   //CSRF 보호 비활성화
                .formLogin().disable()  //폼 로그인 비활성화
                .httpBasic().disable()  // HTTP 기본 인증 비활성화
                .exceptionHandling()    //예외 처리 설정
                .authenticationEntryPoint(jwtAuthenticationEntryPoint) //인증되지 않은 사용자가 보호된 리소스에 액세스 할 때 호출되는 JwtAuthenticationEntryPoint 설정
                .accessDeniedHandler(jwtAccessDeniedHandler)    //권한이 없는 사용자가 보호된 리소스에 액세스 할 때 호출되는 JwtAccessDeniedHandler 설정
                .and()
                .headers().frameOptions().sameOrigin()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //Spring Security에서 세션을 사용하지 않도록 설정
                .and()
                .authorizeRequests()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/v3/api-docs").permitAll()
                .antMatchers("/api/users/test/jwt").authenticated()
                .antMatchers("/api/users/token/me").authenticated()
                .antMatchers("/api/users/locations/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .apply(new JwtSecurityConfig(tokenUtil,userValidationService));
    }
}

