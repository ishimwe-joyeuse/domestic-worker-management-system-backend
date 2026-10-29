
package com.domesticworker.management.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.domesticworker.management.domain.Room;
import com.domesticworker.management.service.RoomService;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    // CREATE
    @PostMapping("/save")
    public ResponseEntity<String> saveRoom(@RequestBody Room room) {
        roomService.saveRoom(room);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Room saved successfully");
    }

    // READ ALL
    @GetMapping("/all")
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }

    // READ FREE ROOMS
    @GetMapping("/free")
    public List<Room> getFreeRooms() {
        return roomService.getFreeRooms();
    }

    // READ ONE
    @GetMapping("/{id}")
    public ResponseEntity<?> getRoomById(@PathVariable UUID id) {
        Optional<Room> room = roomService.getRoomById(id);

        if (room.isPresent()) {
            return ResponseEntity.ok(room.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("The room with that id does not exist");
    }

    // UPDATE
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateRoom(
            @PathVariable UUID id,
            @RequestBody Room room) {

        Room updated = roomService.updateRoom(id, room);

        if (updated != null) {
            return ResponseEntity.ok(updated);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("The room with that id does not exist");
    }

    // DELETE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRoom(@PathVariable UUID id) {
        String result = roomService.deleteRoom(id);

        if (result.equals("Room deleted successfully")) {
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }
}
