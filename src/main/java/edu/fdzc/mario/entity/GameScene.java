package edu.fdzc.mario.entity;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

/**
 * 游戏场景类 - 管理关卡和游戏元素
 * 对应单机版的BackGround功能
 */
@Data
public class GameScene {
    private String sceneId;
    private int sort; // 关卡序号
    private boolean isLastScene; // 是否是最后一关
    private List<GameObstacle> obstacles = new ArrayList<>();
    private List<GameEnemy> enemies = new ArrayList<>();
    private boolean isReach = false; // 是否到达旗杆
    private boolean isBase = false; // 旗子是否落地

    // 场景尺寸
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final int GROUND_LEVEL = 420;

    public GameScene(String sceneId, int sort, boolean isLastScene) {
        this.sceneId = sceneId;
        this.sort = sort;
        this.isLastScene = isLastScene;
    }

    /**
     * 添加障碍物
     */
    public void addObstacle(GameObstacle obstacle) {
        this.obstacles.add(obstacle);
    }

    /**
     * 添加敌人
     */
    public void addEnemy(GameEnemy enemy) {
        this.enemies.add(enemy);
    }

    /**
     * 获取场景信息
     */
    public SceneInfo getSceneInfo() {
        SceneInfo info = new SceneInfo();
        info.setSceneId(sceneId);
        info.setSort(sort);
        info.setLastScene(isLastScene);
        info.setObstacleCount(obstacles.size());
        info.setEnemyCount(enemies.size());
        info.setReach(isReach);
        info.setBase(isBase);
        return info;
    }

    /**
     * 场景信息类
     */
    @Data
    public static class SceneInfo {
        private String sceneId;
        private int sort;
        private boolean lastScene;
        private int obstacleCount;
        private int enemyCount;
        private boolean reach;
        private boolean base;
    }
}