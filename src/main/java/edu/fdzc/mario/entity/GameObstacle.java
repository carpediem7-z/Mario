package edu.fdzc.mario.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 游戏障碍物类 - 对应单机版的Obstacle
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameObstacle {
    private int x;
    private int y;
    private int type; // 障碍物类型
    private int width = 30;
    private int height = 30;

    // 障碍物类型常量
    public static final int TYPE_BRICK = 0;        // 普通砖块
    public static final int TYPE_SOIL_UP = 1;      // 上地面
    public static final int TYPE_SOIL_BASE = 2;    // 下地面
    public static final int TYPE_PIPE_TOP_LEFT = 3; // 水管左上
    public static final int TYPE_PIPE_TOP_RIGHT = 4; // 水管右上
    public static final int TYPE_PIPE_BODY_LEFT = 5; // 水管主体左
    public static final int TYPE_PIPE_BODY_RIGHT = 6; // 水管主体右
    public static final int TYPE_BRICK2 = 7;       // 不可破坏的砖块
    public static final int TYPE_FLAG = 8;         // 旗子

    public GameObstacle(int x, int y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    /**
     * 获取障碍物名称
     */
    public String getTypeName() {
        switch (type) {
            case TYPE_BRICK: return "普通砖块";
            case TYPE_SOIL_UP: return "上地面";
            case TYPE_SOIL_BASE: return "下地面";
            case TYPE_PIPE_TOP_LEFT: return "水管左上";
            case TYPE_PIPE_TOP_RIGHT: return "水管右上";
            case TYPE_PIPE_BODY_LEFT: return "水管主体左";
            case TYPE_PIPE_BODY_RIGHT: return "水管主体右";
            case TYPE_BRICK2: return "不可破坏砖块";
            case TYPE_FLAG: return "旗子";
            default: return "未知障碍物";
        }
    }
}