
package com.domesticworker.management.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.domesticworker.management.domain.Employer;
import com.domesticworker.management.service.EmployerService;

@RestController
@RequestMapping("/api/employers")
public class EmployerController {

    @Autowired
    private EmployerService employerService;

    // CREATE
    @PostMapping("/save")
    public ResponseEntity<?> saveEmployer(@RequestBody Employer employer) {
        Employer saved = employerService.saveEmployer(employer);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Employer saved successfully");
    }

    // READ ALL
    @GetMapping("/all")
    public List<Employer> getAllEmployers() {
        return employerService.getAllEmployers();
    }

    // READ ONE
    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployerById(@PathVariable UUID id) {
        Optional<Employer> employer = employerService.getEmployerById(id);

        if (employer.isPresent()) {
            return ResponseEntity.ok(employer.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The employer with that id does not exist");
        }
    }

    // UPDATE
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateEmployer(
            @PathVariable UUID id,
            @RequestBody Employer employer) {

        Employer updated = employerService.updateEmployer(id, employer);

        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The employer with that id does not exist");
        }
    }

    // DELETE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEmployer(@PathVariable UUID id) {
        String result = employerService.deleteEmployer(id);

        if (result.equals("Employer deleted successfully")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(result);
        }
    }
}
