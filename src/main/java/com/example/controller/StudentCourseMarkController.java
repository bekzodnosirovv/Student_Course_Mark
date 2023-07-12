package com.example.controller;

import com.example.dto.StudentCourseMarkDto;
import com.example.dto.StudentCourseMarkFilterDTO;
import com.example.dto.StudentFilterDTO;
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

    /**
     * 12. Studentni olgan eng katta 3ta baxosi.
     */
    @GetMapping(value = "/getBigMark/{id}")
    public ResponseEntity<?> getBigMark(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(studentCourseMarkService.getBigMark(id));
    }

    /**
     * 13. Studentni eng birinchi olgan baxosi.
     */
    @GetMapping(value = "/getFirstMark/{id}")
    public ResponseEntity<?> getFirstMark(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(studentCourseMarkService.getFirstMark(id));
    }

    /**
     * 14. Studenti  berilgan course dan olgan birinchi baxosi.
     */
    @GetMapping(value = "/getByCourseFirstMark")
    public ResponseEntity<?> getByCourseFirstMark(@RequestParam("studentId") Integer studentId,
                                                  @RequestParam("courseId") Integer courseId) {
        return ResponseEntity.ok(studentCourseMarkService.getByCourseFirstMark(studentId, courseId));
    }

    /**
     * 15. Studentni berilgan cuorse dan olgan eng baland baxosi.
     */
    @GetMapping(value = "/getByCourseBigMark")
    public ResponseEntity<?> getByCourseBigMark(@RequestParam("studentId") Integer studentId,
                                                @RequestParam("courseId") Integer courseId) {
        return ResponseEntity.ok(studentCourseMarkService.getByCourseBigMark(studentId, courseId));
    }


    /**
     * 16. Studentni o'rtacha olgan baxolari.
     */
    @GetMapping(value = "/getAverageMark/{id}")
    public ResponseEntity<?> getAverageMark(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(studentCourseMarkService.getAverageMark(id));
    }

    /**
     * 17. Studentni berilgan curse dan olgan o'rtacha baxolari.
     */
    @GetMapping(value = "/getAverageMarkByCourse")
    public ResponseEntity<?> getAverageMarkByCourse(@RequestParam("studentId") Integer studentId,
                                                    @RequestParam("courseId") Integer courseId) {
        return ResponseEntity.ok(studentCourseMarkService.getAverageMarkByCourse(studentId, courseId));
    }

    /**
     * 18. Studentni berilgan baxodan katta bo'lgan baxolari soni.
     */
    @GetMapping(value = "/getByMarkBigCount")
    public ResponseEntity<?> getByMarkBigCount(@RequestParam("studentId") Integer studentId,
                                               @RequestParam("mark") Integer mark) {
        return ResponseEntity.ok(studentCourseMarkService.getByMarkBigCount(studentId, mark));
    }

    /**
     * 19. Berilgan Cursdan eng baland baxo.
     */
    @GetMapping(value = "/getCourseBigMark/{id}")
    public ResponseEntity<?> getCourseBigMark(@PathVariable("id") Integer course) {
        return ResponseEntity.ok(studentCourseMarkService.getCourseBigMark(course));
    }

    /**
     * 20. Berilgan Cursdan o'lingan o'rtacha baxo.
     */
    @GetMapping(value = "/getCourseAverageMark/{id}")
    public ResponseEntity<?> getCourseAverageMark(@PathVariable("id") Integer course) {
        return ResponseEntity.ok(studentCourseMarkService.getCourseAverageMark(course));
    }

    /**
     * 21. Berilgan Course dan olingan baxolar soni.
     */
    @GetMapping(value = "/getByCourseMarkCount/{id}")
    public ResponseEntity<?> getByCourseMarkCount(@PathVariable("id") Integer course) {
        return ResponseEntity.ok(studentCourseMarkService.getByCourseMarkCount(course));
    }

    /**
     * 22. StudentCourseMark pagination.
     */
    @GetMapping(value = "/pagination")
    public ResponseEntity<?> getPagination(@RequestParam("page") Integer page,
                                           @RequestParam("size") Integer size) {
        return ResponseEntity.ok(studentCourseMarkService.getPagination(page, size));
    }

    /**
     * 23. StudentCourseMark pagination by given studentId. List should be sorted by createdDate.
     */
    @GetMapping(value = "/paginationByStudent")
    public ResponseEntity<?> getPaginationByStudentId(@RequestParam("id") Integer studentId,
                                                      @RequestParam("page") Integer page,
                                                      @RequestParam("size") Integer size) {
        return ResponseEntity.ok(studentCourseMarkService.getPaginationByStudentId(studentId, page, size));
    }

    /**
     * 24. StudentCourseMark pagination by given courseId.  List should be sorted by createdDate.
     */
    @GetMapping(value = "/paginationByCourse")
    public ResponseEntity<?> getPaginationByCourseId(@RequestParam("id") Integer courseId,
                                                     @RequestParam("page") Integer page,
                                                     @RequestParam("size") Integer size) {
        return ResponseEntity.ok(studentCourseMarkService.getPaginationByCourseId(courseId, page, size));
    }

    /**
     * 25. Filter with pagination. Filter items (studentId,courseId,markFrom, markTo,createdDateFrom,createdDateTo,
     * studentName, courseName)
     */
    @GetMapping(value = "/pagination/filter")
    public ResponseEntity<?> getPaginationFilter(@RequestParam(value = "page", defaultValue = "1") int page,
                                                 @RequestParam(value = "size", defaultValue = "10") int size,
                                                 @RequestBody StudentCourseMarkFilterDTO filter) {
        return ResponseEntity.ok(studentCourseMarkService.getPaginationFilter(page - 1, size, filter));
    }

}
