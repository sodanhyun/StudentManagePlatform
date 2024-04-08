//package com.codehows.smp.service;
//
//import com.codehows.smp.dto.SessionUser;
//import com.codehows.smp.entity.Member;
//import com.codehows.smp.repository.MemberRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
//import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
//import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.stereotype.Service;
//import com.codehows.smp.config.OAuthAttributes;
//
//import javax.servlet.http.HttpSession;
//import java.util.Collections;
//
//@Service
//@RequiredArgsConstructor
//public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
//
//    private final MemberRepository memberRepository;
//    private final HttpSession httpSession;
//
//    @Override
//    public OAuth2User loadUser (OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
//        OAuth2User oAuth2User = delegate.loadUser(userRequest);
//
//        // OAuth2 서비스 id
//        String registrationId = userRequest.getClientRegistration().getRegistrationId();
//        // OAuth2 로그인 진행 시 키가 되는 필드 값(PK)
//        String userNameAttributeName =
//                userRequest
//                        .getClientRegistration()
//                        .getProviderDetails()
//                        .getUserInfoEndpoint()
//                        .getUserNameAttributeName();
//
//        OAuthAttributes attributes =
//                OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
//        Member member = saveOrUpdate(attributes);
//        httpSession.setAttribute("user", new SessionUser(member));
//
//        return new DefaultOAuth2User(
//                Collections.singleton(new SimpleGrantedAuthority(member.getRoleKey())),
//                attributes.getAttributes(),
//                attributes.getNameAttributeKey()
//        );
//
//    }
//
//    private Member saveOrUpdate(OAuthAttributes attributes) {
//        Member member = memberRepository.findByEmail(attributes.getEmail());
//        if(member != null) {
//            return member.update(attributes.getName());
//        }else{
//            member = attributes.toEntity();
//            return memberRepository.save(member);
//        }
//    }
//}