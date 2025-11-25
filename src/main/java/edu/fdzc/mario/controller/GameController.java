package edu.fdzc.mario.controller;

import edu.fdzc.mario.service.GameRoomService;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/game")
@RequiredArgsConstructor
public class GameController {
    private final GameRoomService gameRoomService;

    @GetMapping("/rooms")
    public Map<String, Object> getAllRooms() {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", gameRoomService.getAllRooms());
        return response;
    }

    @GetMapping("/room/{roomId}/state")
    public Map<String, Object> getRoomState(@PathVariable String roomId) {
        Map<String, Object> state = gameRoomService.getRoomState(roomId);
        Map<String, Object> response = new HashMap<>();
        response.put("success", state != null);
        response.put("data", state);
        return response;
    }

    @PostMapping("/room/{roomId}/join")
    public Map<String, Object> joinRoom(@PathVariable String roomId, @RequestBody Map<String, String> request) {
        String playerName = request.get("playerName");
        String sessionId = request.get("sessionId"); // 需要从前端传递sessionId

        Map<String, Object> response = new HashMap<>();

        // 这里需要实际的加入房间逻辑
        // 暂时返回成功
        response.put("success", true);
        response.put("message", "加入房间成功");
        response.put("roomId", roomId);
        response.put("playerName", playerName);

        return response;
    }

    // 添加离开房间接口
    @PostMapping("/room/{roomId}/leave")
    public Map<String, Object> leaveRoom(@PathVariable String roomId, @RequestBody Map<String, String> request) {
        String playerId = request.get("playerId");

        gameRoomService.leaveRoom(playerId);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "离开房间成功");
        return response;
    }

    @GetMapping("/player/room")
    public Map<String, Object> getPlayerRoom(@RequestParam String sessionId) {
        Map<String, Object> response = new HashMap<>();

        var room = gameRoomService.getRoomByPlayerSession(sessionId);
        if (room != null) {
            response.put("success", true);
            response.put("data", room.getRoomInfo());
        } else {
            response.put("success", false);
            response.put("message", "玩家不在任何房间中");
        }

        return response;
    }
}
