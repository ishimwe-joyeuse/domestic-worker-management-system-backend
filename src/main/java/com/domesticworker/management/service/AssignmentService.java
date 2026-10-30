
package com.domesticworker.management.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.domesticworker.management.domain.Assignment;
import com.domesticworker.management.domain.AssignmentStatus;
import com.domesticworker.management.repository.AssignmentRepository;
import com.domesticworker.management.repository.WorkReportRepository;

@Service
public class AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
private WorkReportRepository workReportRepository;

    // CREATE - D1: Safe saving
    public Assignment saveAssignment(Assignment assignment) {

        // Check if assignment date is in the past
        if (assignment.getStartDate().isBefore(LocalDate.now())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Assignment date cannot be in the past"
            );
        }

        // Check if worker already has an active assignment on that date
        boolean alreadyAssigned =
                assignmentRepository.existsByWorker_IdAndStartDateAndStatusNot(
                        assignment.getWorker().getId(),
                        assignment.getStartDate(),
                        AssignmentStatus.TERMINATED
                );

        if (alreadyAssigned) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Worker already has an assignment starting on that date"
            );
        }

        // Every new assignment starts as REQUESTED
        assignment.setStatus(AssignmentStatus.REQUESTED);

        return assignmentRepository.save(assignment);
    }

    // READ ALL
    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    // READ ONE
    public Optional<Assignment> getAssignmentById(UUID id) {
        return assignmentRepository.findById(id);
    }

    // UPDATE
    public Assignment updateAssignment(UUID id, Assignment assignment) {
        Optional<Assignment> existing =
                assignmentRepository.findById(id);

        if (existing.isEmpty()) {
            return null;
        }

        Assignment current = existing.get();

        current.setStartDate(assignment.getStartDate());
        current.setDuties(assignment.getDuties());
        current.setDurationDays(assignment.getDurationDays());
        current.setEmployer(assignment.getEmployer());
        current.setWorker(assignment.getWorker());

        // Status cannot be changed through general PUT
        return assignmentRepository.save(current);
    }

    // DELETE
   // E3: Delete assignment only when it has no work reports
public String deleteAssignment(UUID id) {

    if (!assignmentRepository.existsById(id)) {
        return "The assignment with that id does not exist";
    }

    // Check whether the assignment has work reports
    if (workReportRepository.existsByAssignment_Id(id)) {
        throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "That assignment has work reports, delete them first"
        );
    }

    assignmentRepository.deleteById(id);

    return "Assignment deleted successfully";
}

    // FIND BY STATUS
    public List<Assignment> findByStatus(AssignmentStatus status) {
        return assignmentRepository
                .findByStatusOrderByStartDateAsc(status);
    }

    // B3
    public List<Assignment> findBetween(
            LocalDate start, LocalDate end) {

        return assignmentRepository
                .findByStartDateBetweenOrderByStartDateAsc(start, end);
    }

    //D2

    // D2: Change assignment status
public String updateAssignmentStatus(UUID id, AssignmentStatus newStatus) {

    Optional<Assignment> optionalAssignment =
            assignmentRepository.findById(id);

    if (optionalAssignment.isEmpty()) {
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "The assignment with that id does not exist"
        );
    }

    Assignment assignment = optionalAssignment.get();
    AssignmentStatus currentStatus = assignment.getStatus();

    boolean allowed = false;

    if (currentStatus == AssignmentStatus.REQUESTED) {
        allowed = newStatus == AssignmentStatus.ASSIGNED
                || newStatus == AssignmentStatus.TERMINATED;
    } else if (currentStatus == AssignmentStatus.ASSIGNED) {
        allowed = newStatus == AssignmentStatus.FINISHED
                || newStatus == AssignmentStatus.TERMINATED;
    }

    if (!allowed) {
        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Cannot change status from " + currentStatus
                        + " to " + newStatus
        );
    }

    assignment.setStatus(newStatus);
    assignmentRepository.save(assignment);

    return "Assignment status updated successfully";
}
//F1

public List<Object[]> getAssignmentStatsByStatus() {
    return assignmentRepository.countAssignmentsByStatus();
}

//F2

public String cancelWorkerAssignments(UUID workerId, LocalDate date) {

    int cancelled = assignmentRepository.cancelWorkerAssignments(
            workerId,
            date,
            AssignmentStatus.TERMINATED,
            AssignmentStatus.FINISHED
    );

    return cancelled + " assignments cancelled";
}

//Bonus 2
public Page<Assignment> getAssignmentsPage(Pageable pageable) {
    return assignmentRepository.findAll(pageable);
}


}
