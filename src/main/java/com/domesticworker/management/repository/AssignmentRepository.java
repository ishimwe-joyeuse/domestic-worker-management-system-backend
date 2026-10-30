
package com.domesticworker.management.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.domesticworker.management.domain.Assignment;
import com.domesticworker.management.domain.AssignmentStatus;

@Repository
public interface AssignmentRepository
        extends JpaRepository<Assignment, UUID> {

    // B2: Assignments by status, earliest date first
    List<Assignment> findByStatusOrderByStartDateAsc(
            AssignmentStatus status);

    // B3: Assignments between two dates, both dates inclusive
    List<Assignment> findByStartDateBetweenOrderByStartDateAsc(
            LocalDate start, LocalDate end);

    // D1: Check for a non-terminated assignment on the same date
    boolean existsByWorker_IdAndStartDateAndStatusNot(
            UUID workerId,
            LocalDate startDate,
            AssignmentStatus status);

    // F1: Count assignments grouped by status
    @Query("SELECT a.status, COUNT(a) FROM Assignment a GROUP BY a.status")
    List<Object[]> countAssignmentsByStatus();

    // F2: Terminate assignments for a worker on a date,
    
// except assignments already FINISHED
@Modifying
@Transactional
@Query("""
    UPDATE Assignment a
    SET a.status = :newStatus
    WHERE a.worker.id = :workerId
    AND a.startDate = :date
    AND a.status <> :finishedStatus
""")
int cancelWorkerAssignments(
        @Param("workerId") UUID workerId,
        @Param("date") LocalDate date,
        @Param("newStatus") AssignmentStatus newStatus,
        @Param("finishedStatus") AssignmentStatus finishedStatus
);

//Bonus 1
// Bonus: Dashboard counts
long countByStartDate(LocalDate startDate);

long countByStatus(AssignmentStatus status);

//Bonus 2

Page<Assignment> findAll(Pageable pageable);
}
