package edu.fdzc.mario.controller;

import edu.fdzc.mario.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/player")
@RequiredArgsConstructor
public class PlayerController {
    private final PlayerService playerService;

    @GetMapping("/{playerId}")
    public Map<String, Object> getPlayerInfo(@PathVariable String playerId) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", playerService.getPlayer(playerId));
        return response;
    }

    @PutMapping("/{playerId}/role")
    public Map<String, Object> changeRole(
            @PathVariable String playerId,
            @RequestBody Map<String, String> request) {
        String role = request.get("role");
        boolean success = playerService.changePlayerRole(playerId, role) != null;

        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", success ? "角色切换成功" : "角色切换失败");
        return response;
    }
}
