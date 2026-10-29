
package com.domesticworker.management.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.domesticworker.management.domain.Employer;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, UUID> {

    boolean existsByNationalId(String nationalId);

    Optional<Employer> findByNationalId(String nationalId);

    // B1: Derived query — case-insensitive, sorted by first name
    List<Employer> findByLastNameIgnoreCaseOrderByFirstNameAsc(
            String lastName);

    // C4: JPQL — employers who have assignments with a particular worker
    @Query("SELECT DISTINCT a.employer FROM Assignment a " +
           "WHERE a.worker.id = :workerId")
    List<Employer> findEmployersByWorkerId(@Param("workerId") UUID workerId);
}
