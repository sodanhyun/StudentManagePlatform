package com.codehows.smp.service;

import com.codehows.smp.dto.MemberDto;
import com.codehows.smp.entity.Member;
import com.codehows.smp.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member saveMember(Member member) throws IllegalStateException {
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member) throws IllegalStateException {
        Member findMember = memberRepository.findByEmail(member.getEmail());
        if(findMember != null) {
            throw new IllegalStateException("이미 사용중인 이메일 주소입니다");
        }
    }

    public List<MemberDto> getMembers() {
        List<MemberDto> memberDtos = new ArrayList<>();
        for(Member m : memberRepository.findAll()) {
            memberDtos.add(MemberDto.of(m));
        }
        return memberDtos;
    }

    public void updateRoles(MemberDto[] memberDtos) {
        for(MemberDto m : memberDtos) {
            Member member = memberRepository.findByEmail(m.getEmail());
            member.updateRole(m.getRole());
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws
            UsernameNotFoundException {

        //테스트용 아이디
        if(email.equals("admin")) {
            return User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("123"))
                    .roles("ADMIN")
                    .build();
        }
        //배포시 삭제

        Member member = memberRepository.findByEmail(email);

        if(member == null) {
            throw new UsernameNotFoundException(email);
        }

        return User.builder()
                .username(member.getName())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }
}