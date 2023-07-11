package com.example.controller;

import com.example.dto.StudentDTO;
import com.example.enums.Gender;
import com.example.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @PostMapping(value = "/create")
    public ResponseEntity<?> create(@RequestBody StudentDTO dto) {
        return ResponseEntity.ok(studentService.save(dto));
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(studentService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(studentService.getById(id));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody StudentDTO dto) {
        studentService.update(id, dto);
        return ResponseEntity.ok("Student update.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        studentService.delete(id);
        return ResponseEntity.ok("Student delete.");
    }

    @GetMapping(value = "/byName/{name}")
    public ResponseEntity<?> getByName(@PathVariable("name") String name) {
        return ResponseEntity.ok(studentService.getByName(name));
    }

    @GetMapping(value = "/bySurname/{surname}")
    public ResponseEntity<?> getBySurname(@PathVariable("surname") String surname) {
        return ResponseEntity.ok(studentService.getBySurname(surname));
    }

    @GetMapping(value = "/byLevel/{level}")
    public ResponseEntity<?> getByLevel(@PathVariable("level") Integer level) {
        return ResponseEntity.ok(studentService.getByLevel(level));
    }

    @GetMapping(value = "/byAge/{age}")
    public ResponseEntity<?> getByAge(@PathVariable("age") Integer age) {
        return ResponseEntity.ok(studentService.getByAge(age));
    }

    @GetMapping(value = "/byGender/{gender}")
    public ResponseEntity<?> getByGender(@PathVariable("gender") Gender gender) {
        return ResponseEntity.ok(studentService.getByGender(gender));
    }

    @GetMapping(value = "/byDate/{date}")
    public ResponseEntity<?> getByDate(@PathVariable("date") LocalDate date) {
        return ResponseEntity.ok(studentService.getByDate(date));
    }

    @GetMapping(value = "/byBetweenDate/{fromDate}/{toDate}")
    public ResponseEntity<?> getByBetweenDate(@PathVariable("fromDate") LocalDate fromDate,
                                              @PathVariable("toDate") LocalDate toDate) {
        return ResponseEntity.ok(studentService.getByBetweenDate(fromDate, toDate));
    }

    @GetMapping(value = "/pagination")
    public ResponseEntity<?> getPagination(@RequestParam(value = "page", defaultValue = "1") int page,
                                           @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(studentService.getPagination(page - 1, size));
    }

    @GetMapping(value = "/pagination/byLevel")
    public ResponseEntity<?> getPaginationByLevel(@RequestParam(value = "page", defaultValue = "1") int page,
                                                  @RequestParam("level") Integer level) {
        return ResponseEntity.ok(studentService.getPaginationByLevel(page, level));
    }

    @GetMapping(value = "/pagination/byGender")
    public ResponseEntity<?> getPaginationByGender(@RequestParam(value = "page", defaultValue = "1") int page,
                                                  @RequestParam("gender") Gender gender) {
        return ResponseEntity.ok(studentService.getPaginationByGender(page,gender));
    }


    @GetMapping(value = "/pagination/filter")
    public ResponseEntity<?> getPaginationFilter(@RequestParam(value = "page", defaultValue = "1") int page,
                                                   @RequestParam("gender") Gender gender) {
        return ResponseEntity.ok(studentService.getPaginationByGender(page,gender));
    }
}
