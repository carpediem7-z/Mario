package edu.fdzc.mario.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.awt.image.BufferedImage;

/**
 * 玩家实体类 - 对应单机版的Mario类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "players")
public class Player {
    @Id
    private String id;

    @Column(unique = true)
    private String sessionId;

    private String name;
    private String role = "mario";
    private int x = 10;
    private int y = 355;
    private String status = "stand--right";
    private int score = 0;
    private String roomId;

    @Transient
    private transient BufferedImage show;

    // 单机版Mario的属性
    private int xSpeed = 0;
    private int ySpeed = 0;
    private int upTime = 0;
    private boolean isDeath = false;
    private boolean isOK = false;

    public Player(String id, int x, int y, String role) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.role = role;
    }

    // 从单机版Mario类移植的方法
    public void leftMove() {
        this.x = Math.max(0, this.x - 5);
        this.status = "move--left";
        this.xSpeed = -5;
    }

    public void rightMove() {
        this.x = Math.min(775, this.x + 5);
        this.status = "move--right";
        this.xSpeed = 5;
    }

    public void leftStop() {
        this.xSpeed = 0;
        if (this.status.contains("jump")) {
            this.status = "jump--left";
        } else {
            this.status = "stop--left";
        }
    }

    public void rightStop() {
        this.xSpeed = 0;
        if (this.status.contains("jump")) {
            this.status = "jump--right";
        } else {
            this.status = "stop--right";
        }
    }

    public void jump() {
        if (!status.contains("jump")) {
            if (status.contains("left")) {
                status = "jump--left";
            } else {
                status = "jump--right";
            }
            ySpeed = -10;
            upTime = 7;
        }
    }

    public void fall() {
        if (status.contains("left")) {
            status = "jump--left";
        } else {
            status = "jump--right";
        }
        ySpeed = 10;
    }

    public void death() {
        this.isDeath = true;
    }

    /**
     * 物理更新方法 - 从单机版移植
     */
    public void update() {
        // 应用水平移动
        if (xSpeed != 0) {
            x += xSpeed;
            x = Math.max(0, Math.min(775, x));
        }

        // 应用垂直移动（跳跃/下落）
        if (ySpeed != 0) {
            y += ySpeed;

            // 简单的重力模拟
            if (ySpeed < 0) {
                ySpeed += 1;
            } else {
                ySpeed += 1;
            }

            // 落地检测
            if (y >= 355) {
                y = 355;
                ySpeed = 0;
                upTime = 0;

                // 落地后恢复站立状态
                if (status.contains("jump")) {
                    if (status.contains("left")) {
                        status = xSpeed < 0 ? "move--left" : "stop--left";
                    } else {
                        status = xSpeed > 0 ? "move--right" : "stop--right";
                    }
                }
            }
        }

        // 处理上升时间
        if (upTime > 0) {
            upTime--;
            if (upTime == 0) {
                fall();
            }
        }
    }
}