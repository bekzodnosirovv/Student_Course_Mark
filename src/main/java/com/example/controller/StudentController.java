package com.example.controller;

import com.example.dto.StudentDTO;
import com.example.dto.StudentFilterDTO;
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


    /**
     * 1. Create student
     */
    @PostMapping(value = "/create")
    public ResponseEntity<?> create(@RequestBody StudentDTO dto) {
        studentService.save(dto);
        return ResponseEntity.ok("Success");
    }


    /**
     * 2. Get Student List. return all.
     */
    @GetMapping(value = "/getAll")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(studentService.getAll());
    }


    /**
     * 3. Get student by id.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(studentService.getById(id));
    }


    /**
     * 4. Update student.
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody StudentDTO dto) {
        studentService.update(id, dto);
        return ResponseEntity.ok("Student update.");
    }


    /**
     * 5. Delete Student by id.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        studentService.delete(id);
        return ResponseEntity.ok("Student delete.");
    }


    /**
     * 6. Get method by each field. (getByName, getBySurname, getByLevel, getByAge, getByGender)
     */
    /**
     * By name
     */
    @GetMapping(value = "/byName/{name}")
    public ResponseEntity<?> getByName(@PathVariable("name") String name) {
        return ResponseEntity.ok(studentService.getByName(name));
    }


    /**
     * By surname
     */
    @GetMapping(value = "/bySurname/{surname}")
    public ResponseEntity<?> getBySurname(@PathVariable("surname") String surname) {
        return ResponseEntity.ok(studentService.getBySurname(surname));
    }


    /**
     * By level
     */
    @GetMapping(value = "/byLevel/{level}")
    public ResponseEntity<?> getByLevel(@PathVariable("level") Integer level) {
        return ResponseEntity.ok(studentService.getByLevel(level));
    }


    /**
     * By age
     */
    @GetMapping(value = "/byAge/{age}")
    public ResponseEntity<?> getByAge(@PathVariable("age") Integer age) {
        return ResponseEntity.ok(studentService.getByAge(age));
    }


    /**
     * By gender
     */
    @GetMapping(value = "/byGender/{gender}")
    public ResponseEntity<?> getByGender(@PathVariable("gender") Gender gender) {
        return ResponseEntity.ok(studentService.getByGender(gender));
    }


    /**
     * 7. Get StudentList by Given Date.
     */
    @GetMapping(value = "/byDate/{date}")
    public ResponseEntity<?> getByDate(@PathVariable("date") LocalDate date) {
        return ResponseEntity.ok(studentService.getByDate(date));
    }


    /**
     * 8. Get StudentList  between given dates.
     */
    @GetMapping(value = "/byBetweenDate")
    public ResponseEntity<?> getByBetweenDate(@RequestParam("fromDate") LocalDate fromDate,
                                              @RequestParam("toDate") LocalDate toDate) {
        return ResponseEntity.ok(studentService.getByBetweenDate(fromDate, toDate));
    }


    /**
     * 9. Student Pagination;
     */
    @GetMapping(value = "/pagination")
    public ResponseEntity<?> getPagination(@RequestParam(value = "page", defaultValue = "1") int page,
                                           @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(studentService.getPagination(page - 1, size));
    }


    /**
     * 10. Student Pagination by given Level. List should be sorted by id.
     */
    @GetMapping(value = "/pagination/byLevel")
    public ResponseEntity<?> getPaginationByLevel(@RequestParam(value = "page", defaultValue = "1") int page,
                                                  @RequestParam(value = "size", defaultValue = "10") int size,
                                                  @RequestParam("level") Integer level) {
        return ResponseEntity.ok(studentService.getPaginationByLevel(page - 1, size, level));
    }


    /**
     * 11. Pagination by given gender.  List should be sorted by createdDate.
     */
    @GetMapping(value = "/pagination/byGender")
    public ResponseEntity<?> getPaginationByGender(@RequestParam(value = "page", defaultValue = "1") int page,
                                                   @RequestParam(value = "size", defaultValue = "10") int size,
                                                   @RequestParam("gender") Gender gender) {
        return ResponseEntity.ok(studentService.getPaginationByGender(page - 1, size, gender));
    }


    /**
     * 12. Filter with pagination. Filter items (id,name,surname,level,age,Gender, createdDateFrom,createdDateTo)
     */
    @GetMapping(value = "/pagination/filter")
    public ResponseEntity<?> getPaginationFilter(@RequestParam(value = "page", defaultValue = "1") int page,
                                                 @RequestParam(value = "size", defaultValue = "10") int size,
                                                 @RequestBody StudentFilterDTO filter) {
        return ResponseEntity.ok(studentService.getPaginationFilter(page - 1, size, filter));
    }
}
