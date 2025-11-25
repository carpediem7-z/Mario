package edu.fdzc.mario.service;

import edu.fdzc.mario.entity.GameEnemy;
import edu.fdzc.mario.entity.GameObstacle;
import edu.fdzc.mario.entity.GameScene;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class GameSceneService {
    private final Map<String, GameScene> gameScenes = new ConcurrentHashMap<>();

    public GameSceneService() {
        // 初始化默认游戏场景
        initializeDefaultScenes();
        log.info("游戏场景服务初始化完成，共加载 {} 个场景", gameScenes.size());
    }

    private void initializeDefaultScenes() {
        try {
            // 创建第一关场景 - 对应单机版第一关
            GameScene scene1 = new GameScene("scene-1", 1, false);
            initializeScene1(scene1);
            gameScenes.put("scene-1", scene1);

            // 创建第二关场景 - 对应单机版第二关
            GameScene scene2 = new GameScene("scene-2", 2, false);
            initializeScene2(scene2);
            gameScenes.put("scene-2", scene2);

            // 创建第三关场景 - 对应单机版第三关
            GameScene scene3 = new GameScene("scene-3", 3, true);
            initializeScene3(scene3);
            gameScenes.put("scene-3", scene3);

            log.info("场景初始化完成: 第一关-{}障碍物, 第二关-{}障碍物, 第三关-{}障碍物",
                    scene1.getObstacles().size(),
                    scene2.getObstacles().size(),
                    scene3.getObstacles().size());

        } catch (Exception e) {
            log.error("初始化游戏场景失败", e);
        }
    }

    /**
     * 初始化第一关场景 - 完整移植单机版第一关配置
     */
    private void initializeScene1(GameScene scene) {
        // 地板
        for (int i = 0; i < 27; i++) {
            scene.addObstacle(new GameObstacle(i * 30, 420, GameObstacle.TYPE_SOIL_UP));
        }
        for (int j = 0; j <= 120; j += 30) {
            for (int i = 0; i < 27; i++) {
                scene.addObstacle(new GameObstacle(i * 30, 570 - j, GameObstacle.TYPE_SOIL_BASE));
            }
        }

        // 砖块A
        for (int i = 120; i <= 150; i += 30) {
            scene.addObstacle(new GameObstacle(i, 300, GameObstacle.TYPE_BRICK2));
        }

        // 砖块B-F
        for (int i = 300; i <= 570; i += 30) {
            if (i == 360 || i == 390 || i == 480 || i == 510 || i == 540) {
                scene.addObstacle(new GameObstacle(i, 300, GameObstacle.TYPE_BRICK2));
            } else {
                scene.addObstacle(new GameObstacle(i, 300, GameObstacle.TYPE_BRICK));
            }
        }

        // 砖块G
        for (int i = 420; i <= 450; i += 30) {
            scene.addObstacle(new GameObstacle(i, 240, GameObstacle.TYPE_BRICK2));
        }

        // 水管
        for (int i = 360; i <= 600; i += 25) {
            if (i == 360) {
                scene.addObstacle(new GameObstacle(620, i, GameObstacle.TYPE_PIPE_TOP_LEFT));
                scene.addObstacle(new GameObstacle(645, i, GameObstacle.TYPE_PIPE_TOP_RIGHT));
            } else {
                scene.addObstacle(new GameObstacle(620, i, GameObstacle.TYPE_PIPE_BODY_LEFT));
                scene.addObstacle(new GameObstacle(645, i, GameObstacle.TYPE_PIPE_BODY_RIGHT));
            }
        }

        // 蘑菇敌人
        scene.addEnemy(new GameEnemy(
                UUID.randomUUID().toString(),
                580, 385, GameEnemy.TYPE_MUSHROOM
        ));

        // 食人花敌人
        scene.addEnemy(new GameEnemy(
                UUID.randomUUID().toString(),
                635, 420, GameEnemy.TYPE_FLOWER, 328, 428
        ));

        log.debug("第一关初始化完成: {}个障碍物, {}个敌人",
                scene.getObstacles().size(), scene.getEnemies().size());
    }

    /**
     * 初始化第二关场景 - 完整移植单机版第二关配置
     */
    private void initializeScene2(GameScene scene) {
        // 地板
        for (int i = 0; i < 27; i++) {
            scene.addObstacle(new GameObstacle(i * 30, 420, GameObstacle.TYPE_SOIL_UP));
        }
        for (int j = 0; j <= 120; j += 30) {
            for (int i = 0; i < 27; i++) {
                scene.addObstacle(new GameObstacle(i * 30, 570 - j, GameObstacle.TYPE_SOIL_BASE));
            }
        }

        // 第一个水管
        for (int i = 360; i <= 600; i += 25) {
            if (i == 360) {
                scene.addObstacle(new GameObstacle(60, i, GameObstacle.TYPE_PIPE_TOP_LEFT));
                scene.addObstacle(new GameObstacle(85, i, GameObstacle.TYPE_PIPE_TOP_RIGHT));
            } else {
                scene.addObstacle(new GameObstacle(60, i, GameObstacle.TYPE_PIPE_BODY_LEFT));
                scene.addObstacle(new GameObstacle(85, i, GameObstacle.TYPE_PIPE_BODY_RIGHT));
            }
        }

        // 第二个水管
        for (int i = 330; i <= 600; i += 25) {
            if (i == 330) {
                scene.addObstacle(new GameObstacle(620, i, GameObstacle.TYPE_PIPE_TOP_LEFT));
                scene.addObstacle(new GameObstacle(645, i, GameObstacle.TYPE_PIPE_TOP_RIGHT));
            } else {
                scene.addObstacle(new GameObstacle(620, i, GameObstacle.TYPE_PIPE_BODY_LEFT));
                scene.addObstacle(new GameObstacle(645, i, GameObstacle.TYPE_PIPE_BODY_RIGHT));
            }
        }

        // 砖块C
        scene.addObstacle(new GameObstacle(300, 330, GameObstacle.TYPE_BRICK));

        // 砖块BEG
        for (int i = 270; i <= 330; i += 30) {
            if (i == 270 || i == 330) {
                scene.addObstacle(new GameObstacle(i, 360, GameObstacle.TYPE_BRICK));
            } else {
                scene.addObstacle(new GameObstacle(i, 360, GameObstacle.TYPE_BRICK2));
            }
        }

        // 砖块ADFHI
        for (int i = 240; i <= 360; i += 30) {
            if (i == 240 || i == 360) {
                scene.addObstacle(new GameObstacle(i, 390, GameObstacle.TYPE_BRICK));
            } else {
                scene.addObstacle(new GameObstacle(i, 390, GameObstacle.TYPE_BRICK2));
            }
        }

        // 妨碍砖块1
        scene.addObstacle(new GameObstacle(240, 300, GameObstacle.TYPE_BRICK));

        // 空砖块1-4
        for (int i = 360; i <= 540; i += 60) {
            scene.addObstacle(new GameObstacle(i, 270, GameObstacle.TYPE_BRICK2));
        }

        // 第一个食人花
        scene.addEnemy(new GameEnemy(
                UUID.randomUUID().toString(),
                75, 420, GameEnemy.TYPE_FLOWER, 328, 418
        ));

        // 第二个食人花
        scene.addEnemy(new GameEnemy(
                UUID.randomUUID().toString(),
                635, 420, GameEnemy.TYPE_FLOWER, 298, 388
        ));

        // 第一个蘑菇
        scene.addEnemy(new GameEnemy(
                UUID.randomUUID().toString(),
                200, 385, GameEnemy.TYPE_MUSHROOM
        ));

        // 第二个蘑菇
        scene.addEnemy(new GameEnemy(
                UUID.randomUUID().toString(),
                500, 385, GameEnemy.TYPE_MUSHROOM
        ));

        log.debug("第二关初始化完成: {}个障碍物, {}个敌人",
                scene.getObstacles().size(), scene.getEnemies().size());
    }

    /**
     * 初始化第三关场景 - 完整移植单机版第三关配置
     */
    private void initializeScene3(GameScene scene) {
        // 地板
        for (int i = 0; i < 27; i++) {
            scene.addObstacle(new GameObstacle(i * 30, 420, GameObstacle.TYPE_SOIL_UP));
        }
        for (int j = 0; j <= 120; j += 30) {
            for (int i = 0; i < 27; i++) {
                scene.addObstacle(new GameObstacle(i * 30, 570 - j, GameObstacle.TYPE_SOIL_BASE));
            }
        }

        // 砖块A-O
        int temp = 290;
        for (int i = 390; i >= 270; i -= 30) {
            for (int j = temp; j <= 410; j += 30) {
                scene.addObstacle(new GameObstacle(j, i, GameObstacle.TYPE_BRICK2));
            }
            temp += 30;
        }

        // 砖块P-R
        temp = 60;
        for (int i = 390; i >= 360; i -= 30) {
            for (int j = temp; j <= 90; j += 30) {
                scene.addObstacle(new GameObstacle(j, i, GameObstacle.TYPE_BRICK2));
            }
            temp += 30;
        }

        // 旗子
        scene.addObstacle(new GameObstacle(515, 220, GameObstacle.TYPE_FLAG));

        // 蘑菇敌人
        scene.addEnemy(new GameEnemy(
                UUID.randomUUID().toString(),
                150, 385, GameEnemy.TYPE_MUSHROOM
        ));

        log.debug("第三关初始化完成: {}个障碍物, {}个敌人",
                scene.getObstacles().size(), scene.getEnemies().size());
    }

    /**
     * 获取场景
     */
    public GameScene getScene(String sceneId) {
        return gameScenes.get(sceneId);
    }

    /**
     * 获取所有场景
     */
    public Map<String, GameScene> getAllScenes() {
        return new HashMap<>(gameScenes);
    }

    /**
     * 获取场景信息列表
     */
    public Map<String, GameScene.SceneInfo> getAllSceneInfos() {
        Map<String, GameScene.SceneInfo> sceneInfos = new HashMap<>();
        gameScenes.forEach((sceneId, scene) -> {
            sceneInfos.put(sceneId, scene.getSceneInfo());
        });
        return sceneInfos;
    }

    /**
     * 获取默认场景（第一关）
     */
    public GameScene getDefaultScene() {
        return gameScenes.get("scene-1");
    }

    /**
     * 获取下一个场景
     */
    public GameScene getNextScene(String currentSceneId) {
        switch (currentSceneId) {
            case "scene-1":
                return gameScenes.get("scene-2");
            case "scene-2":
                return gameScenes.get("scene-3");
            case "scene-3":
                return gameScenes.get("scene-1"); // 循环回到第一关
            default:
                return getDefaultScene();
        }
    }

    /**
     * 检查玩家是否到达场景边界（触发场景切换）
     */
    public boolean isPlayerAtBoundary(String sceneId, int playerX) {
        GameScene scene = getScene(sceneId);
        if (scene != null && playerX >= 775) {
            return true;
        }
        return false;
    }

    /**
     * 重置场景状态
     */
    public void resetScene(String sceneId) {
        GameScene scene = gameScenes.get(sceneId);
        if (scene != null) {
            scene.setReach(false);
            scene.setBase(false);
            // 重置敌人状态
            scene.getEnemies().forEach(enemy -> {
                if (enemy.isDead()) {
                    // 重新生成敌人
                    enemy.setType(enemy.getType() == 0 ? GameEnemy.TYPE_MUSHROOM : enemy.getType());
                }
            });
            log.debug("场景 {} 已重置", sceneId);
        }
    }

    /**
     * 获取场景的障碍物数据（用于前端渲染）
     */
    public Map<String, Object> getSceneData(String sceneId) {
        GameScene scene = getScene(sceneId);
        if (scene == null) {
            return null;
        }

        Map<String, Object> sceneData = new HashMap<>();
        sceneData.put("sceneId", scene.getSceneId());
        sceneData.put("sort", scene.getSort());
        sceneData.put("lastScene", scene.isLastScene());

        // 障碍物数据
        List<Map<String, Object>> obstaclesData = new ArrayList<>();
        for (GameObstacle obstacle : scene.getObstacles()) {
            Map<String, Object> obstacleData = new HashMap<>();
            obstacleData.put("x", obstacle.getX());
            obstacleData.put("y", obstacle.getY());
            obstacleData.put("type", obstacle.getType());
            obstacleData.put("width", obstacle.getWidth());
            obstacleData.put("height", obstacle.getHeight());
            obstaclesData.add(obstacleData);
        }
        sceneData.put("obstacles", obstaclesData);

        // 敌人数据
        List<Map<String, Object>> enemiesData = new ArrayList<>();
        for (GameEnemy enemy : scene.getEnemies()) {
            if (!enemy.isDead()) {
                Map<String, Object> enemyData = new HashMap<>();
                enemyData.put("enemyId", enemy.getEnemyId());
                enemyData.put("x", enemy.getX());
                enemyData.put("y", enemy.getY());
                enemyData.put("type", enemy.getType());
                enemyData.put("faceTo", enemy.isFaceTo());
                enemyData.put("maxUp", enemy.getMaxUp());
                enemyData.put("maxDown", enemy.getMaxDown());
                enemiesData.add(enemyData);
            }
        }
        sceneData.put("enemies", enemiesData);

        return sceneData;
    }
}