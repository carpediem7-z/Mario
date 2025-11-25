package edu.fdzc.mario.repository;

import edu.fdzc.mario.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, String> {
    Optional<Player> findBySessionId(String sessionId);
    List<Player> findByRoomId(String roomId);

    @Modifying
    @Query("UPDATE Player p SET p.roomId = null WHERE p.roomId = :roomId")
    void clearRoomPlayers(String roomId);

    @Query("SELECT COUNT(p) FROM Player p WHERE p.roomId = :roomId")
    Integer countByRoomId(String roomId);
}