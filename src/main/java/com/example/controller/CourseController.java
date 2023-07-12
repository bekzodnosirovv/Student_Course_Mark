package com.example.controller;

import com.example.dto.CourseDTO;
import com.example.dto.CourseFilterDTO;
import com.example.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    /**
     * 1. Create Course
     */
    @PostMapping(value = "/create")
    public ResponseEntity<?> create(@RequestBody CourseDTO dto) {
        courseService.save(dto);
        return ResponseEntity.ok("Success");
    }

    /**
     * 2. Get Course by id.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(courseService.getById(id));
    }

    /**
     * 3. Get Course list. return all.
     */
    @GetMapping(value = "/getAll")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(courseService.getAll());
    }

    /**
     * 4. Update Course.
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody CourseDTO dto) {
        courseService.update(id, dto);
        return ResponseEntity.ok("Course update.");
    }

    /**
     * 5. Delete Course
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        courseService.delete(id);
        return ResponseEntity.ok("Course delete.");
    }


    /**
     6. Get method by each field. (getByName, getByPrice,getByDuration)
     */

    /**
     * By name
     */
    @GetMapping(value = "/byName/{name}")
    public ResponseEntity<?> getByName(@PathVariable("name") String name) {
        return ResponseEntity.ok(courseService.getByName(name));
    }

    /**
     * By price
     */
    @GetMapping(value = "/byPrice/{price}")
    public ResponseEntity<?> getByPrice(@PathVariable("price") Double price) {
        return ResponseEntity.ok(courseService.getByPrice(price));
    }

    /**
     * By duration
     */
    @GetMapping(value = "/byDuration/{duration}")
    public ResponseEntity<?> getByDuration(@PathVariable("duration") Integer duration) {
        return ResponseEntity.ok(courseService.getByDuration(duration));
    }

    /**
     * 7. Get Course list between given 2 prices.
     */
    @GetMapping(value = "/byBetweenPrice")
    public ResponseEntity<?> getByPriceBetween(@RequestParam("price1") Double price1,
                                               @RequestParam("price2") Double price2) {
        return ResponseEntity.ok(courseService.getByPriceBetween(price1, price2));
    }

    /**
     * 8. Get Course list between 2 createdDates
     */
    @GetMapping(value = "/byBetweenDate")
    public ResponseEntity<?> getByBetweenDate(@RequestParam("fromDate") LocalDate fromDate,
                                              @RequestParam("toDate") LocalDate toDate) {
        return ResponseEntity.ok(courseService.getByBetweenDate(fromDate, toDate));
    }

    /**
     * 9. Course Pagination.
     */
    @GetMapping(value = "/pagination")
    public ResponseEntity<?> getPagination(@RequestParam(value = "page", defaultValue = "1") int page,
                                           @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(courseService.getPagination(page - 1, size));
    }

    /**
     * 10. Course Pagination. List should be sorted by createdDate.
     */
    @GetMapping(value = "/pagination/sorted")
    public ResponseEntity<?> getPaginationSort(@RequestParam(value = "page", defaultValue = "1") int page,
                                               @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(courseService.getPaginationSortCreatedDate(page - 1, size));
    }

    /**
     * 11. Course Pagination by price. List should be sorted by createdDate.
     */
    @GetMapping(value = "/pagination/byPrice")
    public ResponseEntity<?> getPaginationByPrice(@RequestParam(value = "page", defaultValue = "1") int page,
                                                  @RequestParam(value = "size", defaultValue = "10") int size,
                                                  @RequestParam("price") Double price) {
        return ResponseEntity.ok(courseService.getPaginationByPrice(page - 1, size, price));
    }

    /**
     * 12. Course Pagination by price between. List should be sorted by createdDate.
     */
    @GetMapping(value = "/pagination/betweenPrice")
    public ResponseEntity<?> getPaginationByPriceBetween(@RequestParam(value = "page", defaultValue = "1") int page,
                                                         @RequestParam(value = "size", defaultValue = "10") int size,
                                                         @RequestParam("from") Double from,
                                                         @RequestParam("to") Double to) {
        return ResponseEntity.ok(courseService.getPaginationByPriceBetween(page - 1, size, from, to));
    }

    /**
     * 13. Filter with pagination. Filter items (id,name,price,duration,createdDateFrom,createdDateTo)
     */
    @GetMapping(value = "/pagination/filter")
    public ResponseEntity<?> getPaginationFilter(@RequestParam(value = "page", defaultValue = "1") int page,
                                                 @RequestParam(value = "size", defaultValue = "10") int size,
                                                 @RequestBody CourseFilterDTO filter) {
        return ResponseEntity.ok(courseService.getPaginationFilter(page - 1, size, filter));
    }
}
