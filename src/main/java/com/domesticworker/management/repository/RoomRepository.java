
package com.domesticworker.management.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.domesticworker.management.domain.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, UUID> {

    Optional<Room> findByRoomNumber(int roomNumber);

    

    @Query("""
        SELECT r FROM Room r
        WHERE NOT EXISTS (
            SELECT w FROM Worker w
            WHERE w.room = r
        )
        ORDER BY r.roomNumber ASC
        """)
    List<Room> findFreeRooms();
}
