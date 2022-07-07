package com.fillkie;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitDb {

    /*private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    @PostConstruct
    public void init(){

        Role role1 = new Role();
        role1.setRole("USER");
        roleRepository.save(role1);

        Role user = roleRepository.findByRole("USER");

        Member member1 = Member.builder()
                .email("cabookis@google.com")
                .password("1234")
                .provider("google")
                .providerId("cabookisId")
                .build();
        System.out.println(user.getRole());
        member1.setRoles(new HashSet<>(Arrays.asList(user)));


        Member member2 = Member.builder()
                .email("wodnjs471@naver.com")
                .password("asdf")
                .provider("naver")
                .providerId("wodnjs471Id")
                .build();
        memberRepository.save(member1);
        memberRepository.insert(member2);
    }*/

}
