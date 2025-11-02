package edu.fdzc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建窗口
 */
public class MyFrame extends JFrame implements KeyListener,Runnable {
    //存储所有背景
    private List<BackGround> allBg=new ArrayList<>();
    //存储当前背景
    private BackGround nowBg=new BackGround();
    //双缓存
    private Image offScreenImage=null;
    //定义马里奥对象
    private Mario mario=new Mario();
    //定义线程对象，实现马里奥运动
    private Thread thread=new Thread(this);

    public MyFrame() {
        //窗口大小：800*600
        this.setSize(800,600);
        //窗口居中
        this.setLocationRelativeTo(null);
        //窗口可见性
        this.setVisible(true);
        //关闭键:结束程序
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //窗口大小不可变
        this.setResizable(false);
        //添加键盘监听
        this.addKeyListener(this);
        //窗口名称
        this.setTitle("超级玛丽");
        //初始化图片
        StaticValue.init();
        //初始化马里奥
        mario=new Mario(10,355);
        //创建所有场景
        for(int i=1;i<=3;i++){
            allBg.add(new BackGround(i, i == 3));
        }
        //设置第一个场景为当前背景
        nowBg=allBg.get(0);
        mario.setBackGround(nowBg);
        //绘制图像
        repaint();
        thread.start();
    }

    public static void main(String[] args) {
        MyFrame frame = new MyFrame();
    }

    @Override
    public void paint(Graphics g) {
        if(offScreenImage==null){
            offScreenImage=createImage(800,600);
        }
        Graphics graphics=offScreenImage.getGraphics();
        graphics.fillRect(0,0,800,600);
        //绘制背景
        graphics.drawImage(nowBg.getBgImage(),0,0,this);
        //绘制障碍物
        for (Obstacle ob:nowBg.getObstacleList()){
            graphics.drawImage(ob.getShow(),ob.getX(),ob.getY(),this);
        }
        //绘制城堡
        graphics.drawImage(nowBg.getTower(),620,270,this);
        //绘制旗杆
        graphics.drawImage(nowBg.getGan(),500,220,this);
        //绘制马里奥
        graphics.drawImage(mario.getShow(),mario.getX(),mario.getY(),this);
        //将图像绘制在窗口中
        g.drawImage(offScreenImage,0,0,this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
    //当键盘按下按键时调用
    @Override
    public void keyPressed(KeyEvent e) {
        //向左移动
        if(e.getKeyCode()==37){
            mario.leftMove();
        }
        //向右移动
        if(e.getKeyCode()==39){
            mario.rightMove();
        }
        //跳跃
        if(e.getKeyCode()==38){
            mario.jump();
        }
    }
    //当键盘松开按键时调用
    @Override
    public void keyReleased(KeyEvent e) {
        //向左停止
        if(e.getKeyCode()==37){
            mario.leftStop();
        }
        //向右停止
        if(e.getKeyCode()==39){
            mario.rightStop();
        }
    }

    @Override
    public void run() {
        while (true){
            repaint();
            try {
                Thread.sleep(50);
                //走到右侧边缘换下一个场景
                if (mario.getX()>=775){
                    nowBg=allBg.get(nowBg.getSort());
                    mario.setBackGround(nowBg);
                    mario.setX(10);
                    mario.setY(355);
                }
                //判断游戏是否结束
                if(mario.isOK()){
                    JOptionPane.showMessageDialog(this,"恭喜用户xxx成功通关！");
                    System.exit(0);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
