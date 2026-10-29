
package com.domesticworker.management.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.domesticworker.management.domain.Room;
import com.domesticworker.management.repository.RoomRepository;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    // CREATE
    public Room saveRoom(Room room) {
        return roomRepository.save(room);
    }

    // READ ALL
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    // READ FREE ROOMS
    public List<Room> getFreeRooms() {
        return roomRepository.findFreeRooms();
    }

    // READ ONE
    public Optional<Room> getRoomById(UUID id) {
        return roomRepository.findById(id);
    }

    // UPDATE
    public Room updateRoom(UUID id, Room room) {
        Optional<Room> existingRoom = roomRepository.findById(id);

        if (existingRoom.isEmpty()) {
            return null;
        }

        Room currentRoom = existingRoom.get();

        currentRoom.setName(room.getName());
        currentRoom.setRoomNumber(room.getRoomNumber());

        return roomRepository.save(currentRoom);
    }

    // DELETE
    public String deleteRoom(UUID id) {
        if (roomRepository.existsById(id)) {
            roomRepository.deleteById(id);
            return "Room deleted successfully";
        }

        return "The room with that id does not exist";
    }
}
