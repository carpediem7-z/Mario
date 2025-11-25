package edu.fdzc.mario.service;

import edu.fdzc.mario.entity.GameRoom;
import edu.fdzc.mario.entity.Player;
import edu.fdzc.mario.repository.GameRoomRepository;
import edu.fdzc.mario.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameRoomService {
    private final GameRoomRepository gameRoomRepository;
    private final PlayerRepository playerRepository;

    @Transactional
    public boolean joinRoom(String roomId, Player player) {
        Optional<GameRoom> roomOpt = gameRoomRepository.findById(roomId);
        if (roomOpt.isPresent()) {
            GameRoom room = roomOpt.get();

            // 检查房间人数
            int currentPlayers = playerRepository.countByRoomId(roomId);
            if (currentPlayers >= room.getMaxPlayers()) {
                return false;
            }

            // 更新玩家房间信息
            player.setRoomId(roomId);
            playerRepository.save(player);

            // 更新房间人数
            room.setCurrentPlayers(currentPlayers + 1);
            gameRoomRepository.save(room);

            return true;
        }
        return false;
    }

    @Transactional
    public void leaveRoom(String playerId) {
        Optional<Player> playerOpt = playerRepository.findById(playerId);
        if (playerOpt.isPresent()) {
            Player player = playerOpt.get();
            String roomId = player.getRoomId();

            if (roomId != null) {
                // 更新玩家房间信息
                player.setRoomId(null);
                playerRepository.save(player);

                // 更新房间人数
                Optional<GameRoom> roomOpt = gameRoomRepository.findById(roomId);
                if (roomOpt.isPresent()) {
                    GameRoom room = roomOpt.get();
                    int currentPlayers = playerRepository.countByRoomId(roomId);
                    room.setCurrentPlayers(currentPlayers);
                    gameRoomRepository.save(room);
                }
            }
        }
    }

    public GameRoom getRoom(String roomId) {
        return gameRoomRepository.findById(roomId).orElse(null);
    }

    public List<GameRoom> getAllRooms() {
        return gameRoomRepository.findAll();
    }

    public Map<String, Object> getRoomState(String roomId) {
        List<Player> players = playerRepository.findByRoomId(roomId);
        Map<String, Object> playersData = new HashMap<>();

        for (Player player : players) {
            Map<String, Object> playerData = new HashMap<>();
            playerData.put("id", player.getId());
            playerData.put("name", player.getName());
            playerData.put("role", player.getRole());
            playerData.put("x", player.getX());
            playerData.put("y", player.getY());
            playerData.put("status", player.getStatus());
            playerData.put("score", player.getScore());
            playerData.put("sessionId", player.getSessionId());
            playersData.put(player.getId(), playerData);
        }

        return playersData;
    }

    public Map<String, Object> getAllRoomsInfo() {
        List<GameRoom> allRooms = gameRoomRepository.findAll();
        Map<String, Object> roomsInfo = new HashMap<>();

        for (GameRoom room : allRooms) {
            roomsInfo.put(room.getRoomId(), room.getRoomInfo());
        }

        return roomsInfo;
    }

    public GameRoom getRoomByPlayerSession(String sessionId) {
        Player player = playerRepository.findBySessionId(sessionId).orElse(null);
        if (player != null && player.getRoomId() != null) {
            return gameRoomRepository.findById(player.getRoomId()).orElse(null);
        }
        return null;
    }

    public Map<String, Object> getRoomPlayers(String roomId) {
        GameRoom room = getRoom(roomId);
        if (room != null) {
            Map<String, Object> result = new HashMap<>();
            result.put("roomId", roomId);
            result.put("roomName", room.getRoomName());

            List<Player> players = playerRepository.findByRoomId(roomId);
            result.put("players", players.stream()
                    .map(player -> {
                        Map<String, Object> playerInfo = new HashMap<>();
                        playerInfo.put("id", player.getId());
                        playerInfo.put("name", player.getName());
                        playerInfo.put("role", player.getRole());
                        playerInfo.put("score", player.getScore());
                        playerInfo.put("x", player.getX());
                        playerInfo.put("y", player.getY());
                        playerInfo.put("status", player.getStatus());
                        return playerInfo;
                    })
                    .collect(Collectors.toList()));
            return result;
        }
        return null;
    }

    /**
     * 获取完整的房间状态（包含房间信息和玩家数据）
     */
    public Map<String, Object> getFullRoomState(String roomId) {
        Map<String, Object> fullState = new HashMap<>();

        GameRoom room = getRoom(roomId);
        if (room != null) {
            fullState.put("roomInfo", room.getRoomInfo());
            fullState.put("players", getRoomState(roomId));
        }

        return fullState;
    }

    /**
     * 检查房间是否可以加入
     */
    public boolean canJoinRoom(String roomId) {
        Optional<GameRoom> roomOpt = gameRoomRepository.findById(roomId);
        if (roomOpt.isPresent()) {
            GameRoom room = roomOpt.get();
            int currentPlayers = playerRepository.countByRoomId(roomId);
            return currentPlayers < room.getMaxPlayers();
        }
        return false;
    }
}