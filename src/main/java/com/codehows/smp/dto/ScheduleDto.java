package com.codehows.smp.dto;

import com.codehows.smp.entity.Seats;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ScheduleDto {
    private Long id;

    private String title;

    private Date start;

    private Date end;

    private String backgroundColor;

    private String borderColor;

    private Boolean allDay;

    private static ModelMapper modelMapper = new ModelMapper();
    public static SeatDto of(Seats seats) {
        return modelMapper.map(seats, SeatDto.class);
    }
}
