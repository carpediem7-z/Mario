package edu.fdzc;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 场景物品类
 */
public class StaticValue {
    //第一关第二关背景
    public static BufferedImage bg=null;
    public static BufferedImage bg2=null;
    //左右跳跃
    public static BufferedImage jump_L=null;
    public static BufferedImage jump_R=null;
    //左右站立
    public static BufferedImage stand_L=null;
    public static BufferedImage stand_R=null;
    //城堡
    public static BufferedImage tower =null;
    //旗杆
    public static BufferedImage gan=null;
    //各种障碍物
    public static List<BufferedImage> obstacle=new ArrayList<>();
    //左右跑
    public static List<BufferedImage> run_L=new ArrayList<>();
    public static List<BufferedImage> run_R=new ArrayList<>();
    //蘑菇
    public static List<BufferedImage> mogu=new ArrayList<>();
    //食人花
    public static List<BufferedImage> flower=new ArrayList<>();
    //图片路径
    public static String path=System.getProperty("user.dir")+"/src/main/resources/images/";

    //初始化方法
    public static void init(){
        try{
            //背景
            bg= ImageIO.read(new File(path+"bg.png"));
            bg2=ImageIO.read(new File(path+"bg2.png"));
            //左右跳跃
            jump_L=ImageIO.read(new File(path+"s_mario_jump1_L.png"));
            jump_R=ImageIO.read(new File(path+"s_mario_jump1_R.png"));
            //左右站立
            stand_L=ImageIO.read(new File(path+"s_mario_stand_L.png"));
            stand_R=ImageIO.read(new File(path+"s_mario_stand_R.png"));
            //城堡
            tower=ImageIO.read(new File(path+"tower.png"));
            //旗杆
            gan=ImageIO.read(new File(path+"gan.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
        //左跑
        for(int i=1;i<=2;i++){
            try {
                run_L.add(ImageIO.read(new File(path+"s_mario_run"+i+"_L.png")));
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        //右跑
        for(int i=1;i<=2;i++){
            try {
                run_R.add(ImageIO.read(new File(path+"s_mario_run"+i+"_R.png")));
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        //加载障碍物
        try {
            obstacle.add(ImageIO.read(new File(path+"brick.png")));//普通砖块
            obstacle.add(ImageIO.read(new File(path+"soil_up.png")));//上地面
            obstacle.add(ImageIO.read(new File(path+"soil_base.png")));//下地面
        }catch (IOException e){
            e.printStackTrace();
        }
        //加载水管
        for(int i=1;i<=4;i++){
            try {
                obstacle.add(ImageIO.read(new File(path+"pipe"+i+".png")));
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        //加载不可破坏的砖块和旗子
        try {
            obstacle.add(ImageIO.read(new File(path+"brick2.png")));//不可破坏的砖块
            obstacle.add(ImageIO.read(new File(path+"flag.png")));//旗子
        } catch (IOException e) {
            e.printStackTrace();
        }
        //加载蘑菇
        for(int i=1;i<=3;i++){
            try {
                mogu.add(ImageIO.read(new File(path+"fungus"+i+".png")));
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        //加载食人花
        for(int i=1;i<=2;i++){
            try {
                flower.add(ImageIO.read(new File(path+"flower1."+i+".png")));
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
