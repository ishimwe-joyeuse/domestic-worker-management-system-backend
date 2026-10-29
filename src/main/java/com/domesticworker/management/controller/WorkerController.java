
package com.domesticworker.management.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.domesticworker.management.domain.Worker;
import com.domesticworker.management.service.WorkerService;

@RestController
@RequestMapping("/api/workers")
public class WorkerController {

    @Autowired
    private WorkerService workerService;

    // A2: CREATE
    @PostMapping("/save")
    public ResponseEntity<String> saveWorker(
            @RequestBody Worker worker) {

        workerService.saveWorker(worker);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Worker saved successfully");
    }

    // A2: READ ALL
    @GetMapping("/all")
    public List<Worker> getAllWorkers() {
        return workerService.getAllWorkers();
    }

    // A2: READ ONE
    @GetMapping("/{id}")
    public ResponseEntity<?> getWorkerById(@PathVariable UUID id) {
        Optional<Worker> worker = workerService.getWorkerById(id);

        if (worker.isPresent()) {
            return ResponseEntity.ok(worker.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The worker with that id does not exist");
        }
    }

    // A2: UPDATE
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateWorker(
            @PathVariable UUID id,
            @RequestBody Worker worker) {

        Worker updated = workerService.updateWorker(id, worker);

        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The worker with that id does not exist");
        }
    }

    // A2: DELETE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteWorker(@PathVariable UUID id) {
        String result = workerService.deleteWorker(id);

        if (result.equals("Worker deleted successfully")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(result);
        }
    }

    // C1: WORKERS BY SKILL
    @GetMapping("/by-skill")
    public List<Worker> findWorkersBySkill(@RequestParam String name) {
        return workerService.findWorkersBySkillName(name);
    }

    // D3: ADD SKILL
    @PutMapping("/{workerId}/skills/{skillId}")
    public ResponseEntity<String> addSkill(
            @PathVariable UUID workerId,
            @PathVariable UUID skillId) {

        String result = workerService.addSkill(workerId, skillId);

        if (result.equals("The worker with that id does not exist")
                || result.equals("That skill does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        } else if (result.equals("The worker already has that skill")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }

        return ResponseEntity.ok(result);
    }

    // D4: CHANGE ROOM
    @PatchMapping("/{workerId}/room")
    public ResponseEntity<String> changeRoom(
            @PathVariable UUID workerId,
            @RequestParam int roomNumber) {

        String result = workerService.changeRoom(workerId, roomNumber);

        if (result.equals("The worker with that id does not exist")
                || result.equals("The room with that roomNumber does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        } else if (result.equals("That room is taken by another worker")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }

        return ResponseEntity.ok(result);
    }
}
