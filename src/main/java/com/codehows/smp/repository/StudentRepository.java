package com.codehows.smp.repository;

import com.codehows.smp.dto.SeatDto;
import com.codehows.smp.dto.StudentDto;
import com.codehows.smp.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByClassAB(String classAB);
}
