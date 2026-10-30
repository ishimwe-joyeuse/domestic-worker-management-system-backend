package com.domesticworker.management.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.domesticworker.management.domain.Assignment;
import com.domesticworker.management.domain.AssignmentStatus;
import com.domesticworker.management.domain.WorkReport;
import com.domesticworker.management.repository.AssignmentRepository;
import com.domesticworker.management.repository.WorkReportRepository;

@Service
public class WorkReportService {

    @Autowired
    private WorkReportRepository workReportRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    // E1: Save a work report
    public String saveWorkReport(UUID assignmentId, WorkReport workReport) {

        // Find assignment
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "The assignment with that id does not exist"
                ));

        // Only FINISHED assignments can have work reports
        if (assignment.getStatus() != AssignmentStatus.FINISHED) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Work reports are only for FINISHED assignments"
            );
        }

        // Set today's date and link the assignment
        workReport.setReportDate(LocalDate.now());
        workReport.setAssignment(assignment);

        workReportRepository.save(workReport);

        return "Work report saved successfully";
    }

    // E2: Get work report history of an employer
    public List<WorkReport> getEmployerReports(String nationalId) {
        return workReportRepository.findReportsByEmployer(nationalId);
    }

    // E3: Delete a work report
    public String deleteWorkReport(UUID id) {

        if (!workReportRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "The work report with that id does not exist"
            );
        }

        workReportRepository.deleteById(id);

        return "Work report deleted successfully";
    }
}