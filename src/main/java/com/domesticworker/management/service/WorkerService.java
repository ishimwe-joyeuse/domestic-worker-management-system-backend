
package com.domesticworker.management.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.domesticworker.management.domain.Room;
import com.domesticworker.management.domain.Skill;
import com.domesticworker.management.domain.Worker;
import com.domesticworker.management.repository.RoomRepository;
import com.domesticworker.management.repository.SkillRepository;
import com.domesticworker.management.repository.WorkerRepository;

@Service
public class WorkerService {

    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private SkillRepository skillRepository;

    // A2: CREATE
    public Worker saveWorker(Worker worker) {
        return workerRepository.save(worker);
    }

    // A2: READ ALL
    public List<Worker> getAllWorkers() {
        return workerRepository.findAll();
    }

    // A2: READ ONE
    public Optional<Worker> getWorkerById(UUID id) {
        return workerRepository.findById(id);
    }

    // A2: UPDATE
    public Worker updateWorker(UUID id, Worker worker) {
        Optional<Worker> existing = workerRepository.findById(id);

        if (existing.isEmpty()) {
            return null;
        }

        Worker current = existing.get();
        current.setFirstName(worker.getFirstName());
        current.setLastName(worker.getLastName());
        current.setPhone(worker.getPhone());

        // Do not change the status of an assignment here.
        // This is a Worker update, not an Assignment update.

        if (worker.getRoom() != null && worker.getRoom().getId() != null) {
            Optional<Room> room =
                    roomRepository.findById(worker.getRoom().getId());

            if (room.isEmpty()) {
                return null;
            }

            current.setRoom(room.get());
        }

        if (worker.getSkills() != null) {
            List<Skill> existingSkills = new ArrayList<>();

            for (Skill skill : worker.getSkills()) {
                if (skill.getId() == null) {
                    return null;
                }

                Optional<Skill> found =
                        skillRepository.findById(skill.getId());

                if (found.isEmpty()) {
                    return null;
                }

                existingSkills.add(found.get());
            }

            current.setSkills(existingSkills);
        }

        return workerRepository.save(current);
    }

    // A2: DELETE
    public String deleteWorker(UUID id) {
        if (workerRepository.existsById(id)) {
            workerRepository.deleteById(id);
            return "Worker deleted successfully";
        }

        return "The worker with that id does not exist";
    }

    // C1: WORKERS BY SKILL
    public List<Worker> findWorkersBySkillName(String name) {
        return workerRepository.findWorkersBySkillName(name);
    }

    // D3: ADD ONE SKILL TO A WORKER
    public String addSkill(UUID workerId, UUID skillId) {
        Optional<Worker> workerOptional =
                workerRepository.findById(workerId);

        if (workerOptional.isEmpty()) {
            return "The worker with that id does not exist";
        }

        Optional<Skill> skillOptional =
                skillRepository.findById(skillId);

        if (skillOptional.isEmpty()) {
            return "That skill does not exist";
        }

        Worker worker = workerOptional.get();
        Skill skill = skillOptional.get();

        if (worker.getSkills().contains(skill)) {
            return "The worker already has that skill";
        }

        worker.getSkills().add(skill);
        workerRepository.save(worker);

        return "Skill added to the worker";
    }

    // D4: CHANGE WORKER'S ROOM
    public String changeRoom(UUID workerId, int roomNumber) {
        Optional<Worker> workerOptional =
                workerRepository.findById(workerId);

        if (workerOptional.isEmpty()) {
            return "The worker with that id does not exist";
        }

        Optional<Room> roomOptional =
                roomRepository.findByRoomNumber(roomNumber);

        if (roomOptional.isEmpty()) {
            return "The room with that roomNumber does not exist";
        }

        Worker worker = workerOptional.get();
        Room room = roomOptional.get();

        // Allow a worker to keep their current room.
        if (worker.getRoom() != null
                && worker.getRoom().getRoomNumber() == roomNumber) {
            return "Room changed successfully";
        }

        if (workerRepository.existsByRoom_RoomNumber(roomNumber)) {
            return "That room is taken by another worker";
        }

        worker.setRoom(room);
        workerRepository.save(worker);

        return "Room changed successfully";
    }
}
