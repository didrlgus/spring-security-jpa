package com.sprint_security_jpa.service;

import com.sprint_security_jpa.domain.CustomUserDetails;
import com.sprint_security_jpa.domain.Member;
import com.sprint_security_jpa.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<Member> member = memberRepository.findByEmail(email);

        member.orElseThrow(() -> new UsernameNotFoundException("Not found: " + email));

        return member.map(CustomUserDetails::new).get();
    }
}
