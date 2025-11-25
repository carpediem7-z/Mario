package edu.fdzc.mario.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 敌人类 - 对应单机版的Enemy
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameEnemy {
    private String enemyId;
    private int x;
    private int y;
    private int type; // 敌人类型 1:蘑菇 2:食人花
    private boolean faceTo = true; // 运动方向
    private int maxUp = 0; // 食人花运动上限
    private int maxDown = 0; // 食人花运动下限
    private int moveRange = 100; // 移动范围

    // 敌人类型常量
    public static final int TYPE_MUSHROOM = 1;
    public static final int TYPE_FLOWER = 2;

    public GameEnemy(String enemyId, int x, int y, int type) {
        this.enemyId = enemyId;
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public GameEnemy(String enemyId, int x, int y, int type, int maxUp, int maxDown) {
        this.enemyId = enemyId;
        this.x = x;
        this.y = y;
        this.type = type;
        this.maxUp = maxUp;
        this.maxDown = maxDown;
    }

    /**
     * 获取敌人名称
     */
    public String getTypeName() {
        switch (type) {
            case TYPE_MUSHROOM: return "蘑菇敌人";
            case TYPE_FLOWER: return "食人花敌人";
            default: return "未知敌人";
        }
    }

    /**
     * 敌人死亡
     */
    public void death() {
        // 标记为死亡状态
        this.type = 0;
    }

    /**
     * 判断是否死亡
     */
    public boolean isDead() {
        return this.type == 0;
    }
}