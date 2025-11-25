package edu.fdzc.mario.controller;

import edu.fdzc.mario.service.GameSceneService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/scene")
@RequiredArgsConstructor
public class GameSceneController {

    private final GameSceneService gameSceneService;

    /**
     * 获取所有场景信息
     */
    @GetMapping("/all")
    public Map<String, Object> getAllScenes() {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", gameSceneService.getAllSceneInfos());
        return response;
    }

    /**
     * 获取特定场景数据
     */
    @GetMapping("/{sceneId}")
    public Map<String, Object> getScene(@PathVariable String sceneId) {
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> sceneData = gameSceneService.getSceneData(sceneId);

        if (sceneData != null) {
            response.put("success", true);
            response.put("data", sceneData);
        } else {
            response.put("success", false);
            response.put("message", "场景不存在");
        }
        return response;
    }

    /**
     * 获取默认场景
     */
    @GetMapping("/default")
    public Map<String, Object> getDefaultScene() {
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> sceneData = gameSceneService.getSceneData("scene-1");

        response.put("success", true);
        response.put("data", sceneData);
        return response;
    }

    /**
     * 重置场景
     */
    @PostMapping("/{sceneId}/reset")
    public Map<String, Object> resetScene(@PathVariable String sceneId) {
        gameSceneService.resetScene(sceneId);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "场景重置成功");
        return response;
    }
}