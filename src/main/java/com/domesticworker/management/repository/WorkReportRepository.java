package com.domesticworker.management.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.domesticworker.management.domain.WorkReport;

public interface WorkReportRepository
        extends JpaRepository<WorkReport, UUID> {

    // E2: Find all reports belonging to an employer
    @Query("""
        SELECT wr
        FROM WorkReport wr
        WHERE wr.assignment.employer.nationalId = :nationalId
        ORDER BY wr.reportDate DESC
    """)
    List<WorkReport> findReportsByEmployer(
            @Param("nationalId") String nationalId
    );

    // E3: Check whether an assignment has reports
    boolean existsByAssignment_Id(UUID assignmentId);
}