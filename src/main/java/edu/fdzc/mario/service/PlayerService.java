package edu.fdzc.mario.service;

import edu.fdzc.mario.entity.Player;
import edu.fdzc.mario.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;

    @Transactional
    public Player createPlayer(String sessionId, String playerName) {
        String playerId = UUID.randomUUID().toString();
        Player player = new Player();
        player.setId(playerId);
        player.setSessionId(sessionId);
        player.setName(playerName);
        player.setRole("mario");
        player.setX(10);
        player.setY(355);
        player.setStatus("stand--right");

        return playerRepository.save(player);
    }

    @Transactional
    public Player updatePlayerPosition(String playerId, int x, int y, String status) {
        Optional<Player> playerOpt = playerRepository.findById(playerId);
        if (playerOpt.isPresent()) {
            Player player = playerOpt.get();
            player.setX(x);
            player.setY(y);
            player.setStatus(status);
            return playerRepository.save(player);
        }
        return null;
    }

    @Transactional
    public Player changePlayerRole(String playerId, String role) {
        Optional<Player> playerOpt = playerRepository.findById(playerId);
        if (playerOpt.isPresent()) {
            Player player = playerOpt.get();
            player.setRole(role);
            return playerRepository.save(player);
        }
        return null;
    }

    @Transactional
    public void removePlayer(String playerId) {
        playerRepository.deleteById(playerId);
    }

    public Player getPlayer(String playerId) {
        return playerRepository.findById(playerId).orElse(null);
    }

    public Player getPlayerBySession(String sessionId) {
        return playerRepository.findBySessionId(sessionId).orElse(null);
    }

    public List<Player> getPlayersByRoom(String roomId) {
        return playerRepository.findByRoomId(roomId);
    }
}