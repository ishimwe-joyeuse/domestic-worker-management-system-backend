
package com.domesticworker.management.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.domesticworker.management.domain.Skill;
import com.domesticworker.management.repository.SkillRepository;

@Service
public class SkillService {

    @Autowired
    private SkillRepository skillRepository;

    // CREATE
    public Skill saveSkill(Skill skill) {
        return skillRepository.save(skill);
    }

    // READ ALL
    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    // READ UNUSED SKILLS
    public List<Skill> getUnusedSkills() {
        return skillRepository.findUnusedSkills();
    }

    // READ ONE
    public Optional<Skill> getSkillById(UUID id) {
        return skillRepository.findById(id);
    }

    // UPDATE
    public Skill updateSkill(UUID id, Skill skill) {
        Optional<Skill> existingSkill = skillRepository.findById(id);

        if (existingSkill.isEmpty()) {
            return null;
        }

        Skill currentSkill = existingSkill.get();
        currentSkill.setName(skill.getName());

        return skillRepository.save(currentSkill);
    }

    // DELETE
    public String deleteSkill(UUID id) {
        if (skillRepository.existsById(id)) {
            skillRepository.deleteById(id);
            return "Skill deleted successfully";
        }

        return "The skill with that id does not exist";
    }
}
