package com.codehows.smp.service;

import com.codehows.smp.dto.SeatDto;
import com.codehows.smp.dto.StudentImgDto;
import com.codehows.smp.entity.Seats;
import com.codehows.smp.entity.SeatUid;
import com.codehows.smp.entity.Student;
import com.codehows.smp.entity.StudentImg;
import com.codehows.smp.repository.SeatRepository;
import com.codehows.smp.repository.StudentImgRepository;
import com.codehows.smp.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ClassService {

    private final StudentRepository studentRepository;
    private final StudentImgRepository studentImgRepository;
    private final SeatRepository seatRepository;

    public List<SeatDto> getSeatsList(String classAB) {
        List<SeatDto> seatDtos = seatRepository.findSeatDtoList(classAB);
        return seatDtos;
    }

    public void addProfileImg(StudentImgDto studentImgDto, Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(EntityExistsException::new);
        StudentImg studentImg = studentImgRepository.findByStudentId(studentId);
        if(studentImg == null) {
            studentImg = new StudentImg();
            studentImg.setOriImgName(studentImgDto.getOriImgName());
            studentImg.setImgUrl(studentImgDto.getImgUrl());
            studentImg.setStudent(student);
            studentImgRepository.save(studentImg);
        }else {
            studentImg.setOriImgName(studentImgDto.getOriImgName());
            studentImg.setImgUrl(studentImgDto.getImgUrl());
            studentImg.setStudent(student);
        }
    }

    public StudentImgDto getProfileImg(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(EntityExistsException::new);
        StudentImg studentImg = studentImgRepository.findByStudentId(studentId);
        StudentImgDto studentImgDto = StudentImgDto.of(studentImg);
        return studentImgDto;
    }

    public void updateSeats(SeatDto[] seatDtos) {
        for(SeatDto s : seatDtos) {
            if(s.getStudentId()!= null) {
                Student student = studentRepository.findById(s.getStudentId())
                        .orElseThrow(EntityExistsException::new);

                if (seatRepository.findByStudentId(s.getStudentId()) != null) {
                    Seats last = seatRepository.findByStudentId(s.getStudentId());
                    seatRepository.delete(last);
                }
                SeatUid sid = new SeatUid();
                log.info(s.toString());
                sid.setClassAB(s.getClassAB());
                sid.setSeatId(s.getSeatId());
                if(seatRepository.findBySeatIdAndClassAB(s.getSeatId(), s.getClassAB()).orElse(null) != null) {
                    Seats seats = seatRepository.findBySeatIdAndClassAB(s.getSeatId(), s.getClassAB())
                            .orElseThrow(EntityExistsException::new);
                    log.info(seats.toString());
                    seatRepository.delete(seats);
                }
                Seats newSeats = Seats.createSeat(s, student);
                seatRepository.save(newSeats);
            }
        }
    }

    public Resource profileImage(Long studentId) throws EntityNotFoundException {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(EntityExistsException::new);
        StudentImg studentImg = studentImgRepository.findByStudentId(studentId);
        return new FileSystemResource(studentImg.getImgUrl() + studentImg.getOriImgName());
    }

}
