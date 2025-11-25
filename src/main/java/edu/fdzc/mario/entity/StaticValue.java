package edu.fdzc.mario.entity;

import lombok.Data;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 场景物品类 - 资源管理器
 * 负责加载和管理所有游戏图片资源
 */
@Component
@Data
public class StaticValue {
    // 背景图片
    public static BufferedImage bg = null;
    public static BufferedImage bg2 = null;

    // 马里奥动作图片
    public static BufferedImage jump_L = null;
    public static BufferedImage jump_R = null;
    public static BufferedImage stand_L = null;
    public static BufferedImage stand_R = null;

    // 场景元素
    public static BufferedImage tower = null;
    public static BufferedImage gan = null;

    // 各种列表
    public static List<BufferedImage> obstacle = new ArrayList<>();
    public static List<BufferedImage> run_L = new ArrayList<>();
    public static List<BufferedImage> run_R = new ArrayList<>();
    public static List<BufferedImage> mogu = new ArrayList<>();
    public static List<BufferedImage> flower = new ArrayList<>();

    /**
     * 初始化方法 - 加载所有游戏资源
     */
    @PostConstruct
    public void init() {
        try {
            // 加载背景
            bg = loadImage("static/images/bg.png");
            bg2 = loadImage("static/images/bg2.png");

            // 加载马里奥动作
            jump_L = loadImage("static/images/s_mario_jump1_L.png");
            jump_R = loadImage("static/images/s_mario_jump1_R.png");
            stand_L = loadImage("static/images/s_mario_stand_L.png");
            stand_R = loadImage("static/images/s_mario_stand_R.png");

            // 加载场景元素
            tower = loadImage("static/images/tower.png");
            gan = loadImage("static/images/gan.png");

            // 加载跑步动作序列
            for (int i = 1; i <= 2; i++) {
                run_L.add(loadImage("static/images/s_mario_run" + i + "_L.png"));
                run_R.add(loadImage("static/images/s_mario_run" + i + "_R.png"));
            }

            // 加载障碍物
            obstacle.add(loadImage("static/images/brick.png"));      // 普通砖块
            obstacle.add(loadImage("static/images/soil_up.png"));    // 上地面
            obstacle.add(loadImage("static/images/soil_base.png"));  // 下地面

            // 加载水管
            for (int i = 1; i <= 4; i++) {
                obstacle.add(loadImage("static/images/pipe" + i + ".png"));
            }

            // 加载特殊障碍物
            obstacle.add(loadImage("static/images/brick2.png")); // 不可破坏的砖块
            obstacle.add(loadImage("static/images/flag.png"));   // 旗子

            // 加载敌人
            for (int i = 1; i <= 3; i++) {
                mogu.add(loadImage("static/images/fungus" + i + ".png"));
            }

            for (int i = 1; i <= 2; i++) {
                flower.add(loadImage("static/images/flower1." + i + ".png"));
            }

            System.out.println("游戏资源加载完成！");

        } catch (IOException e) {
            System.err.println("资源加载失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 从classpath加载图片
     */
    private BufferedImage loadImage(String path) throws IOException {
        ClassPathResource resource = new ClassPathResource(path);
        return ImageIO.read(resource.getInputStream());
    }
}