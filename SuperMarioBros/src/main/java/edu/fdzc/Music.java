package edu.fdzc;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * 背景音乐类
 * 需要.wav格式
 */
public class Music {
    public Music () throws FileNotFoundException, JavaLayerException {
        Player player;
        String str=System.getProperty("user.dir")+"/src/main/resources/Music/music.wav";
        BufferedInputStream name = new BufferedInputStream(new FileInputStream(str));
        player=new Player(name);
        player.play();
    }
}
