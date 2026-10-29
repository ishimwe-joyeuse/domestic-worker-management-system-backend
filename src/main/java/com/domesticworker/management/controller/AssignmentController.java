
package com.domesticworker.management.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.domesticworker.management.domain.Assignment;
import com.domesticworker.management.domain.AssignmentStatus;
import com.domesticworker.management.service.AssignmentService;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    // A2: CREATE
    @PostMapping("/save")
    public ResponseEntity<?> saveAssignment(
            @RequestBody Assignment assignment) {

        Assignment saved = assignmentService.saveAssignment(assignment);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Assignment saved successfully");
    }

    // A2: READ ALL
    @GetMapping("/all")
    public List<Assignment> getAllAssignments() {
        return assignmentService.getAllAssignments();
    }

    // A2: READ ONE
    @GetMapping("/{id}")
    public ResponseEntity<?> getAssignmentById(
            @PathVariable UUID id) {

        Optional<Assignment> assignment =
                assignmentService.getAssignmentById(id);

        if (assignment.isPresent()) {
            return ResponseEntity.ok(assignment.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The assignment with that id does not exist");
        }
    }

    // A2: UPDATE
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateAssignment(
            @PathVariable UUID id,
            @RequestBody Assignment assignment) {

        Assignment updated =
                assignmentService.updateAssignment(id, assignment);

        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The assignment with that id does not exist");
        }
    }

    // A2: DELETE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAssignment(
            @PathVariable UUID id) {

        String result = assignmentService.deleteAssignment(id);

        if (result.equals("Assignment deleted successfully")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(result);
        }
    }

    // B2: FIND ASSIGNMENTS BY STATUS
    @GetMapping("/by-status")
    public List<Assignment> findByStatus(
            @RequestParam AssignmentStatus status) {

        return assignmentService.findByStatus(status);
    }

    //B3
    @GetMapping("/between")
public List<Assignment> findBetween(
        @RequestParam String start,
        @RequestParam String end) {

    LocalDate startDate = LocalDate.parse(start);
    LocalDate endDate = LocalDate.parse(end);

    return assignmentService.findBetween(startDate, endDate);
}
}
