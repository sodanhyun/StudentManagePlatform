package com.codehows.smp.dto;

import com.codehows.smp.entity.StudentImg;
import lombok.*;
import org.modelmapper.ModelMapper;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class StudentImgDto {
    private Long id;

    private String oriImgName;

    private String imgUrl;

    private static ModelMapper modelMapper = new ModelMapper();

    public StudentImgDto(String oriImgName, String imgUrl) {
        this.oriImgName = oriImgName;
        this.imgUrl = imgUrl;
    }
    public static StudentImgDto of(StudentImg studentImg) {
        if(studentImg==null) return null;
        return modelMapper.map(studentImg, StudentImgDto.class);
    }
}
