package com.example.controller;

import com.example.dto.StudentCourseMarkDto;
import com.example.service.StudentCourseMarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/studentCourseMark")
public class StudentCourseMarkController {

    @Autowired
    private StudentCourseMarkService studentCourseMarkService;

    /**
     * 1. Create StudentCourseMark
     */
    @PostMapping(value = "/create")
    public ResponseEntity<?> create(@RequestBody StudentCourseMarkDto dto) {
        studentCourseMarkService.save(dto);
        return ResponseEntity.ok("Success save.");

    }

    /**
     * 2. Update StudentCourseMark
     */
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody StudentCourseMarkDto dto) {
        studentCourseMarkService.update(dto, id);
        return ResponseEntity.ok("Update");
    }

    /**
     * 3. Get StudentCourseMark by id.
     */
    @GetMapping(value = "/get/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(studentCourseMarkService.getById(id));
    }

    /**
     * 4. Get StudentCourseMark by id with detail.
     */
    @GetMapping(value = "/get/detail/{id}")
    public ResponseEntity<?> getByIdDetail(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(studentCourseMarkService.getByIdDetail(id));
    }

    /**
     * 5. Delete StudentCourseMark by id.
     */
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        studentCourseMarkService.delete(id);
        return ResponseEntity.ok("Delete");
    }

    /**
     * 6. Get List of StudentCourseMark. Return All
     */
    @GetMapping(value = "/get/all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(studentCourseMarkService.getAll());
    }

    /**
     * 7. Studentni berilgan kundagi olgan baxosi
     */
    @GetMapping(value = "/getByDate/mark")
    public ResponseEntity<?> getByDateMark(@RequestParam("id") Integer id,
                                           @RequestParam("date") LocalDate date) {
        return ResponseEntity.ok(studentCourseMarkService.getByDateMarkBetween(id, date, date));
    }

    /**
     * 8. Studentni berilgan 2kun oralig'idagi olgan baxosi.
     */
    @GetMapping(value = "/getByDateBetween/mark")
    public ResponseEntity<?> getByDateMarkBetween(@RequestParam("id") Integer id,
                                                  @RequestParam("from") LocalDate from,
                                                  @RequestParam("to") LocalDate to) {
        return ResponseEntity.ok(studentCourseMarkService.getByDateMarkBetween(id, from, to));
    }

    /**
     * 9. Studentni olgan barcha baxolari vaqt boyicha kamayish tartibida sord qiling.
     */
    @GetMapping(value = "/getMarkSortDate/{id}")
    public ResponseEntity<?> getMarkSortDate(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(studentCourseMarkService.getMarkSortDate(id));
    }

    /**
     * 10. Studentni berilgan curse dan olgan baxolari vaqt boyicha kamayish tartibida sord qiling.
     */
    @GetMapping(value = "/getByCourseMarkSortDate")
    public ResponseEntity<?> getByCourseMarkSortDate(@RequestParam("studentId") Integer studentId,
                                                     @RequestParam("courseId") Integer courseId) {
        return ResponseEntity.ok(studentCourseMarkService.getByCourseMarkSortDate(studentId, courseId));
    }

    /**
     * 11. Studentni eng oxirda olgan baxosi, va curse nomi.
     */
    @GetMapping(value = "/getLastMark/{id}")
    public ResponseEntity<?> getLastMark(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(studentCourseMarkService.getLastMark(id));
    }

    @GetMapping(value = "/getBigMark/{id}")
    public ResponseEntity<?> getBigMark(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(studentCourseMarkService.getBigMark(id));
    }

    @GetMapping(value = "/getFirstMark/{id}")
    public ResponseEntity<?> getFirstMark(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(studentCourseMarkService.getFirstMark(id));
    }

    @GetMapping(value = "/getByCourseFirstMark")
    public ResponseEntity<?> getByCourseFirstMark(@RequestParam("course") Integer course,
                                                  @RequestParam("id") Integer id) {
        return ResponseEntity.ok(studentCourseMarkService.getByCourseFirstMark(course, id));
    }

    @GetMapping(value = "/getByCourseBigMark")
    public ResponseEntity<?> getByCourseBigMark(@RequestParam("course") Integer course,
                                                @RequestParam("id") Integer id) {
        return ResponseEntity.ok(studentCourseMarkService.getByCourseBigMark(course, id));
    }

    @GetMapping(value = "/getAverageMark/{id}")
    public ResponseEntity<?> getAverageMark(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(studentCourseMarkService.getAverageMark(id));
    }

    @GetMapping(value = "/getAverageMarkByCourse")
    public ResponseEntity<?> getAverageMarkByCourse(@RequestParam("id") Integer student,
                                                    @RequestParam("course") Integer course) {
        return ResponseEntity.ok(studentCourseMarkService.getAverageMarkByCourse(student, course));
    }

    @GetMapping(value = "/getByMarkBigCount")
    public ResponseEntity<?> getByMarkBigCount(@RequestParam("id") Integer student,
                                               @RequestParam("mark") Integer mark) {
        return ResponseEntity.ok(studentCourseMarkService.getByMarkBigCount(student, mark));
    }

    @GetMapping(value = "/getCourseBigMark/{id}")
    public ResponseEntity<?> getCourseBigMark(@PathVariable("id") Integer course) {
        return ResponseEntity.ok(studentCourseMarkService.getCourseBigMark(course));
    }

    @GetMapping(value = "/getCourseAverageMark/{id}")
    public ResponseEntity<?> getCourseAverageMark(@PathVariable("id") Integer course) {
        return ResponseEntity.ok(studentCourseMarkService.getCourseAverageMark(course));
    }

    @GetMapping(value = "/getByCourseMarkCount/{id}")
    public ResponseEntity<?> getByCourseMarkCount(@PathVariable("id") Integer course) {
        return ResponseEntity.ok(studentCourseMarkService.getByCourseMarkCount(course));
    }

    @GetMapping(value = "/pagination")
    public ResponseEntity<?> getPagination(@RequestParam("page") Integer page,
                                           @RequestParam("size") Integer size) {
        return ResponseEntity.ok(studentCourseMarkService.getPagination(page, size));
    }

    @GetMapping(value = "/paginationByStudent")
    public ResponseEntity<?> getPaginationByStudentId(@RequestParam("id") Integer studentId,
                                                      @RequestParam("page") Integer page,
                                                      @RequestParam("size") Integer size) {
        return ResponseEntity.ok(studentCourseMarkService.getPaginationByStudentId(studentId, page, size));
    }

    @GetMapping(value = "/paginationByCourse")
    public ResponseEntity<?> getPaginationByCourseId(@RequestParam("id") Integer courseId,
                                                     @RequestParam("page") Integer page,
                                                     @RequestParam("size") Integer size) {
        return ResponseEntity.ok(studentCourseMarkService.getPaginationByCourseId(courseId, page, size));
    }
}
