package com.codehows.smp.controller;

import com.codehows.smp.dto.SeatDto;
import com.codehows.smp.dto.StudentDto;
import com.codehows.smp.dto.StudentImgDto;
import com.codehows.smp.entity.Student;
import com.codehows.smp.service.ClassService;
import com.codehows.smp.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequestMapping(value = "/class")
@Controller
@RequiredArgsConstructor
@Slf4j
public class ClassManageController {

    private final ClassService classService;
    private final StudentService studentService;

    @Value("${uploadPath}")
    private String uploadPath;

    @GetMapping(value = "/info")
    public String infoManage() {
        return "pages/classManage/classInfo";
    }

    @GetMapping(value = "/info/{classab}")
    public String getClassCard(@PathVariable("classab") String classAB) {
        String returnPath = null;
        if(classAB.equals("A")) {
            returnPath = "pages/classManage/classACard";
        }else if(classAB.equals("B")) {
            returnPath = "pages/classManage/classBCard";
        }
        return returnPath;
    }

    @ResponseBody
    @PostMapping(value = "/seats")
    public Map<String, Object> getSeats() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("dataA", classService.getSeatsList("A"));
        result.put("dataB", classService.getSeatsList("B"));
        return result;
    }

    @ResponseBody
    @GetMapping(value = "/detail/{id}")
    public ResponseEntity getDetail(@PathVariable("id")Long id) {
        HashMap<String, Object> map = new HashMap<>();
        StudentDto studentDto = studentService.getStudent(id);
        map.put("student", studentDto);
        StudentImgDto studentImgDto = classService.getProfileImg(id);
        if(studentImgDto==null) {
            map.put("image", "gray.png");
        }else{
            map.put("image", studentImgDto.getOriImgName());
        }
        return new ResponseEntity<Object>(map, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "/profileImg/{studentId}")
    public ResponseEntity<?> profileImage(@PathVariable Long studentId) {
        try{
            return ResponseEntity.ok().body(classService.profileImage(studentId));
        }catch (EntityNotFoundException e) {
            return new ResponseEntity<>("해당 이미지를 찾을 수 없습니다.", HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @ResponseBody
    @PostMapping(value = "/profileImg/{studentId}")
    public StudentImgDto addImg(@RequestParam("img") MultipartFile img, @PathVariable("studentId") Long studentId) throws IOException {
        UUID uuid = UUID.randomUUID();
        File destDir = new File(uploadPath);
        if(!destDir.exists()) {
            if(!destDir.mkdirs()) {
                log.info("이미지 저장 디렉터리 생성 실패");
                throw new RuntimeException();
            }
        }
        String imgFileName = uuid + "_" + img.getOriginalFilename();
        Path imgPath = Paths.get(uploadPath + File.separator +imgFileName);
        img.transferTo(imgPath);
        StudentImgDto studentImgDto = new StudentImgDto(imgFileName, uploadPath);
        classService.addProfileImg(studentImgDto, studentId);
        return studentImgDto;
    }

    @GetMapping(value = "/edit/{classab}")
    public String getEditCard(@PathVariable("classab") String classAB, Model model) {
        List<StudentDto> studentDtoList = studentService.getClassList(classAB);
        model.addAttribute("students", studentDtoList);
        if(classAB.equals("A")) {
            model.addAttribute("class", "A");
        }else if(classAB.equals("B")) {
            model.addAttribute("class", "B");
        }

        return "pages/classManage/editCard";
    }

    @ResponseBody
    @PatchMapping(value = "/update")
    public ResponseEntity updateSeats(@RequestBody SeatDto[] seatDtos) {
        Map<String, Object> map = new HashMap<String, Object>();
        classService.updateSeats(seatDtos);
        map.put("result", "ok");
        return new ResponseEntity<Object>(map, HttpStatus.OK);
    }

}
