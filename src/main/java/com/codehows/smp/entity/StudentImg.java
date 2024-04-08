package com.codehows.smp.entity;

import com.codehows.smp.dto.StudentDto;
import com.codehows.smp.dto.StudentImgDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="student_img")
@NoArgsConstructor
public class StudentImg extends BaseEntity {

    @Id
    @Column(name="student_img_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String oriImgName;

    private String imgUrl;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    public void updateStudentImg(String oriImgName, String imgUrl) {
        this.oriImgName = oriImgName;
        this.imgUrl = imgUrl;
    }
}
