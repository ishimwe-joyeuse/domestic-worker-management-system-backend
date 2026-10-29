
package com.domesticworker.management.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.domesticworker.management.domain.Assignment;
import com.domesticworker.management.domain.AssignmentStatus;
import com.domesticworker.management.repository.AssignmentRepository;

@Service
public class AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    // CREATE
    public Assignment saveAssignment(Assignment assignment) {
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

        // Status must not be changed through general PUT.
        return assignmentRepository.save(current);
    }

    // DELETE
    public String deleteAssignment(UUID id) {
        if (!assignmentRepository.existsById(id)) {
            return "The assignment with that id does not exist";
        }

        assignmentRepository.deleteById(id);
        return "Assignment deleted successfully";
    }

    // FIND BY STATUS
    public List<Assignment> findByStatus(AssignmentStatus status) {
        return assignmentRepository
                .findByStatusOrderByStartDateAsc(status);
    }
}
