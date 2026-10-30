package com.domesticworker.management.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.domesticworker.management.domain.WorkReport;
import com.domesticworker.management.service.WorkReportService;

@RestController
@RequestMapping("/api/work-reports")
public class WorkReportController {

    @Autowired
    private WorkReportService workReportService;

    // E1: Save work report
    @PostMapping("/save")
    public ResponseEntity<?> saveWorkReport(
            @RequestParam UUID assignmentId,
            @RequestBody WorkReport workReport) {

        try {
            String message =
                    workReportService.saveWorkReport(assignmentId, workReport);

            return ResponseEntity.status(HttpStatus.CREATED).body(message);

        } catch (ResponseStatusException ex) {
            return ResponseEntity.status(ex.getStatusCode())
                    .body(ex.getReason());
        }
    }

    // E2: Get employer work report history
    @GetMapping("/employer/{nationalId}")
    public ResponseEntity<List<WorkReport>> getEmployerReports(
            @PathVariable String nationalId) {

        return ResponseEntity.ok(
                workReportService.getEmployerReports(nationalId)
        );
    }

    // E3: Delete work report
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteWorkReport(@PathVariable UUID id) {

        try {
            String message = workReportService.deleteWorkReport(id);
            return ResponseEntity.ok(message);

        } catch (ResponseStatusException ex) {
            return ResponseEntity.status(ex.getStatusCode())
                    .body(ex.getReason());
        }
    }
}