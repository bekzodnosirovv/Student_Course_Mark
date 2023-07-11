package com.example.controller;

import com.example.dto.CourseDTO;
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

    @PostMapping(value = "/create")
    public ResponseEntity<?> create(@RequestBody CourseDTO dto) {
        return ResponseEntity.ok(courseService.save(dto));
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(courseService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(courseService.getById(id));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody CourseDTO dto) {
        courseService.update(id, dto);
        return ResponseEntity.ok("Course update.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        courseService.delete(id);
        return ResponseEntity.ok("Course delete.");
    }

    @GetMapping(value = "/byName/{name}")
    public ResponseEntity<?> getByName(@PathVariable("name") String name) {
        return ResponseEntity.ok(courseService.getByName(name));
    }

    @GetMapping(value = "/byPrice/{price}")
    public ResponseEntity<?> getByPrice(@PathVariable("price") Double price) {
        return ResponseEntity.ok(courseService.getByPrice(price));
    }

    @GetMapping(value = "/byDuration/{duration}")
    public ResponseEntity<?> getByDuration(@PathVariable("duration") Integer duration) {
        return ResponseEntity.ok(courseService.getByDuration(duration));
    }


    @GetMapping(value = "/byBetweenPrice/{price1}/{price2}")
    public ResponseEntity<?> getByPriceBetween(@PathVariable("price1") Double price1,
                                               @PathVariable("price2") Double price2) {
        return ResponseEntity.ok(courseService.getByPriceBetween(price1, price2));
    }

    @GetMapping(value = "/byBetweenDate/{fromDate}/{toDate}")
    public ResponseEntity<?> getByBetweenDate(@PathVariable("fromDate") LocalDate fromDate,
                                              @PathVariable("toDate") LocalDate toDate) {
        return ResponseEntity.ok(courseService.getByBetweenDate(fromDate, toDate));
    }

    @GetMapping(value = "/pagination")
    public ResponseEntity<?> getPagination(@RequestParam(value = "page", defaultValue = "1") int page,
                                           @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(courseService.getPagination(page - 1, size));
    }

    @GetMapping(value = "/pagination/sorted")
    public ResponseEntity<?> getPaginationSort(@RequestParam(value = "page", defaultValue = "1") int page) {
        return ResponseEntity.ok(courseService.getPaginationSortCreatedDate(page - 1));
    }

    @GetMapping(value = "/pagination/byPrice")
    public ResponseEntity<?> getPaginationByPrice(@RequestParam(value = "page", defaultValue = "1") int page,
                                                  @RequestParam("price") Double price) {
        return ResponseEntity.ok(courseService.getPaginationByPrice(page - 1, price));
    }

    @GetMapping(value = "/pagination/price")
    public ResponseEntity<?> getPaginationByPriceBetween(@RequestParam(value = "page", defaultValue = "1") int page,
                                                         @RequestParam("from") Double from,
                                                         @RequestParam("to") Double to) {
        return ResponseEntity.ok(courseService.getPaginationByPriceBetween(page - 1, from,to));
    }
}
