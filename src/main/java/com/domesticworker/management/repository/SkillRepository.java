
package com.domesticworker.management.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.domesticworker.management.domain.Skill;

@Repository
public interface SkillRepository extends JpaRepository<Skill, UUID> {

    Optional<Skill> findByName(String name);

    boolean existsByName(String name);

    @Query("SELECT s FROM Skill s WHERE s.workers IS EMPTY")
    List<Skill> findUnusedSkills();
}
