package com.codehows.smp.dto;

import com.codehows.smp.constant.Role;
import com.codehows.smp.entity.Member;
import com.codehows.smp.entity.Student;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberDto {

    private Long id;

    private String name;

    private String email;

    private String password;

    private Role role;

    private static ModelMapper modelMapper = new ModelMapper();

    public static MemberDto of(Member member) {
        return modelMapper.map(member, MemberDto.class);
    }

}
