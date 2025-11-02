package edu.fdzc;

import java.awt.image.BufferedImage;

/**
 * 障碍物类
 */
public class Obstacle implements Runnable{
    private int x;
    private int y;
    private int type;//类型
    private BufferedImage show=null;//显示图像
    private BackGround bg=null;//当前场景对象
    private Thread thread=new Thread(this);//线程对象

    public Obstacle(int x,int y,int type,BackGround bg){
        this.x=x;
        this.y=y;
        this.type=type;
        this.bg=bg;
        show=StaticValue.obstacle.get(type);
        //如果是旗子就启动线程
        if(type==8){
            thread.start();
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getType() {
        return type;
    }

    public BufferedImage getShow() {
        return show;
    }

    @Override
    public void run() {
        while(true){
            if(this.bg.isReach()){
                if(this.y<374){
                    this.y+=5;
                }else {
                    this.bg.setBase(true);
                }
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
