package com.codehows.smp.entity;

import com.codehows.smp.dto.SeatDto;
import lombok.*;
import javax.persistence.*;

@Entity
@Table(name="seats")
@NoArgsConstructor
@AllArgsConstructor
@IdClass(SeatUid.class)
@ToString
public class Seats extends BaseEntity {
    @Id
    @Column(nullable = false)
    private Long seatId;

    @Id
    @Column(nullable = false)
    private String classAB;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", unique = true)
    private Student student;


    private String name;

    public static Seats createSeat(SeatDto seatDto, Student student) {
        return Seats.builder()
                .seatId(seatDto.getSeatId())
                .classAB(seatDto.getClassAB())
                .student(student)
                .name(seatDto.getName())
                .build();
    }

    @Builder
    public Seats(Long seatId, String classAB, String name, Student student) {
        this.seatId = seatId;
        this.name = name;
        this.classAB = classAB;
        this.student = student;
    }

    public void updateStudent(Student student) {
        this.name = student.getName();
        this.student = student;
    }
}
