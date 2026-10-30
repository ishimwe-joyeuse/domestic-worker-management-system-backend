package com.domesticworker.management.service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.domesticworker.management.domain.AssignmentStatus;
import com.domesticworker.management.repository.AssignmentRepository;
import com.domesticworker.management.repository.EmployerRepository;
import com.domesticworker.management.repository.WorkerRepository;
import com.domesticworker.management.repository.WorkReportRepository;

@Service
public class DashboardService {

    private final EmployerRepository employerRepository;
    private final WorkerRepository workerRepository;
    private final AssignmentRepository assignmentRepository;
    private final WorkReportRepository workReportRepository;
    private final RoomService roomService;

    public DashboardService(
            EmployerRepository employerRepository,
            WorkerRepository workerRepository,
            AssignmentRepository assignmentRepository,
            WorkReportRepository workReportRepository,
            RoomService roomService) {

        this.employerRepository = employerRepository;
        this.workerRepository = workerRepository;
        this.assignmentRepository = assignmentRepository;
        this.workReportRepository = workReportRepository;
        this.roomService = roomService;
    }

    public Map<String, Long> getDashboardSummary() {

        Map<String, Long> summary = new LinkedHashMap<>();

        summary.put("employers", employerRepository.count());
        summary.put("workers", workerRepository.count());

        // Reuse your existing C2 free-room feature
        summary.put("freeRooms",
                (long) roomService.getFreeRooms().size());

        summary.put("assignmentsToday",
                assignmentRepository.countByStartDate(LocalDate.now()));

        summary.put("requested",
                assignmentRepository.countByStatus(
                        AssignmentStatus.REQUESTED));

        summary.put("workReports", workReportRepository.count());

        return summary;
    }
}