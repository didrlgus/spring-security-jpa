package com.sprint_security_jpa.controller;

import com.sprint_security_jpa.domain.Member;
import com.sprint_security_jpa.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MainController {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/")
    public ResponseEntity<?> init() {
        // 유지, 관리자 계정 생성 (hard-code)
        memberRepository.save(Member.builder().email("user@naver.com").password(passwordEncoder.encode("1234")).roles("ROLE_USER").build());
        memberRepository.save(Member.builder().email("admin@naver.com").password(passwordEncoder.encode("1234")).roles("ROLE_USER,ROLE_ADMIN").build());

        return ResponseEntity.ok("Welcome!");
    }

    @GetMapping("/user")
    public ResponseEntity<?> userAuthenticate() {

        return ResponseEntity.ok("User Authentication success!");
    }

    @GetMapping("/admin")
    public ResponseEntity<?> adminAuthenticate() {

        return ResponseEntity.ok("Admin Authentication success!");
    }

    @GetMapping("/authentication")
    public ResponseEntity<?> getAuthentication(Authentication authentication) {
        return ResponseEntity.ok(authentication);
    }
}
