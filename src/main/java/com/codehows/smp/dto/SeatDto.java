package com.codehows.smp.dto;

import com.codehows.smp.entity.Seats;
import lombok.*;
import org.modelmapper.ModelMapper;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class SeatDto {

    private Long seatId;

    private String classAB;

    private Long studentId;

    private String name;

    private static ModelMapper modelMapper = new ModelMapper();
    public static SeatDto of(Seats seats) {
        return modelMapper.map(seats, SeatDto.class);
    }

    public SeatDto(Long studentId, Long seatId, String classAB, String name) {
        this.studentId = studentId;
        this.seatId = seatId;
        this.classAB = classAB;
        this.name = name;
    }

}
