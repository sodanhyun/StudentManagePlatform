package com.codehows.smp.entity;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class SeatUid implements Serializable {
    private Long seatId;
    private String classAB;
}
