package com.codehows.smp.repository;

import com.codehows.smp.dto.StudentImgDto;
import com.codehows.smp.entity.Student;
import com.codehows.smp.entity.StudentImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentImgRepository extends JpaRepository<StudentImg, Long> {

    @Query("select i from StudentImg i where i.student.id = :studentId")
    StudentImg findByStudentId(@Param("studentId")Long studentId);

}
