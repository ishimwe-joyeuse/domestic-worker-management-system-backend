
package com.domesticworker.management.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.domesticworker.management.domain.Worker;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, UUID> {

    // D4: Check whether another worker occupies this room
    boolean existsByRoom_RoomNumber(int roomNumber);

    // C1: JPQL — find workers by skill name, ignoring case
    @Query("SELECT DISTINCT w FROM Worker w JOIN w.skills s " +
           "WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Worker> findWorkersBySkillName(@Param("name") String name);
}
