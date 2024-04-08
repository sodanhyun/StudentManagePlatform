package com.codehows.smp.repository;

import com.codehows.smp.dto.SeatDto;
import com.codehows.smp.entity.Seats;
import com.codehows.smp.entity.SeatUid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seats, SeatUid> {

    @Query("select new com.codehows.smp.dto.SeatDto(s.student.id, s.seatId, s.classAB, s.name) " +
            "from Seats s " +
            "where s.classAB = :classAB " +
            "order by s.seatId asc")
    List<SeatDto> findSeatDtoList(@Param("classAB") String classAB);

    @Query("select s from Seats s where s.student.id = :studentId")
    Seats findByStudentId(@Param("studentId")Long studentId);

    Optional<Seats> findBySeatIdAndClassAB(Long seatId, String classAB);
}
