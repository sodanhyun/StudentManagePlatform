package com.codehows.smp.entity;

import com.codehows.smp.dto.StudentDto;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="student")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Student extends BaseEntity {

    @Id
    @Column(name="student_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String birth;

    @Column(nullable = false)
    private String phone;

    private String classAB;

    private String residence;

    private String belong;

    private String major;

    private String graduate;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private LocalDate start;

    @Column(nullable = false)
    private Boolean status;

    @Column(length = 400)
    private String note;

    public static Student createStudent(StudentDto studentDto) {
        return Student.builder()
                .name(studentDto.getName())
                .birth(studentDto.getBirth())
                .phone(studentDto.getPhone())
                .classAB(studentDto.getClassAB())
                .residence(studentDto.getResidence())
                .belong(studentDto.getBelong())
                .major(studentDto.getMajor())
                .graduate(studentDto.getGraduate())
                .email(studentDto.getEmail())
                .start(studentDto.getStart())
                .status(studentDto.getStatus())
                .note(studentDto.getNote())
                .build();
    }

    @Builder
    public Student(String name, String birth, String phone,
                   String classAB, String residence, String belong,
                   String major, String graduate, String email,
                   LocalDate start, Boolean status, String note) {
        this.name = name;
        this.birth = birth;
        this.phone = phone;
        this.classAB = classAB;
        this.residence = residence;
        this.belong = belong;
        this.major = major;
        this.graduate = graduate;
        this.email = email;
        this.start = start;
        this.status = status;
        this.note = note;
    }

    public void updateStudent(StudentDto studentDto) {
        this.name = studentDto.getName();
        this.birth = studentDto.getBirth();
        this.phone = studentDto.getPhone();
        this.classAB = studentDto.getClassAB();
        this.residence = studentDto.getResidence();
        this.belong = studentDto.getBelong();
        this.major = studentDto.getMajor();
        this.graduate = studentDto.getGraduate();
        this.email = studentDto.getEmail();
        this.start = studentDto.getStart();
        this.status = studentDto.getStatus();
        this.note = studentDto.getNote();
    }
}
