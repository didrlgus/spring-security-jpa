package com.sprint_security_jpa.controller;

import com.sprint_security_jpa.domain.Member;
import com.sprint_security_jpa.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MainController {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/")
    public ResponseEntity<?> init(HttpServletRequest request) throws RuntimeException {
        // 유지, 관리자 계정 생성 (hard-code)
        memberRepository.save(Member.builder().email("user@naver.com").password(passwordEncoder.encode("1234")).roles("ROLE_USER").build());
        memberRepository.save(Member.builder().email("admin@naver.com").password(passwordEncoder.encode("1234")).roles("ROLE_USER,ROLE_ADMIN").build());

        log.info("init() : #### {}", request.getSession().getAttribute("SPRING_SECURITY_CONTEXT"));

        return ResponseEntity.ok("Welcome!");
    }

    @GetMapping("/user")
    public ResponseEntity<?> userAuthenticate(SecurityContextHolder contextHolder, Authentication authentication, HttpServletRequest request) {
        log.info("#### {}", Thread.currentThread().getId());
        log.info("#### {}", authentication.hashCode());
        log.info("#### {}", SecurityContextHolder.getContext().getAuthentication().hashCode());
        log.info("#### {}", request.getSession().getAttribute("SPRING_SECURITY_CONTEXT").hashCode());
        // security context holder의 해시코드, 요청마다 새롭게 생성됨.
        log.info("#### {}", contextHolder.hashCode());
        log.info("#### {}", contextHolder);

        return ResponseEntity.ok("User Authentication success!");
    }

    @GetMapping("/admin")
    public ResponseEntity<?> adminAuthenticate(SecurityContextHolder contextHolder, Authentication authentication, HttpServletRequest request) {
        /*log.info("#### {}", Thread.currentThread().getId());
        log.info("#### {}", authentication.hashCode());
        log.info("#### {}", SecurityContextHolder.getContext().getAuthentication().hashCode());
        log.info("#### {}", request.getSession().getAttribute("SPRING_SECURITY_CONTEXT").hashCode());
        // security context holder의 해시코드, 요청마다 새롭게 생성됨.
        log.info("#### {}", contextHolder.hashCode());
        log.info("#### {}", contextHolder);*/

        return ResponseEntity.ok("Admin Authentication success!");
    }

    @GetMapping("/authentication")
    public ResponseEntity<?> getAuthentication(Authentication authentication) {
        return ResponseEntity.ok(authentication);
    }
}
