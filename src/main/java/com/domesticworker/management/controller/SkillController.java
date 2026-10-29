
package com.domesticworker.management.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.domesticworker.management.domain.Skill;
import com.domesticworker.management.service.SkillService;

@RestController
@RequestMapping("/api/skills")
public class SkillController {

    @Autowired
    private SkillService skillService;

    // CREATE
    @PostMapping("/save")
    public ResponseEntity<String> saveSkill(@RequestBody Skill skill) {
        skillService.saveSkill(skill);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Skill saved successfully");
    }

    // READ ALL
    @GetMapping("/all")
    public List<Skill> getAllSkills() {
        return skillService.getAllSkills();
    }

    // READ UNUSED SKILLS
    @GetMapping("/unused")
    public List<Skill> getUnusedSkills() {
        return skillService.getUnusedSkills();
    }

    // READ ONE
    @GetMapping("/{id}")
    public ResponseEntity<?> getSkillById(@PathVariable UUID id) {
        Optional<Skill> skill = skillService.getSkillById(id);

        if (skill.isPresent()) {
            return ResponseEntity.ok(skill.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("The skill with that id does not exist");
    }

    // UPDATE
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateSkill(
            @PathVariable UUID id,
            @RequestBody Skill skill) {

        Skill updated = skillService.updateSkill(id, skill);

        if (updated != null) {
            return ResponseEntity.ok(updated);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("The skill with that id does not exist");
    }

    // DELETE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSkill(@PathVariable UUID id) {
        String result = skillService.deleteSkill(id);

        if (result.equals("Skill deleted successfully")) {
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(result);
    }
}
