package Model;

/**
 * 熊义杰
 * 消息提示音的播放
 */

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class playSound {
    Player player;
    public playSound(){

    }
    public void play() throws FileNotFoundException, JavaLayerException {
        BufferedInputStream buffer = new BufferedInputStream(new FileInputStream(new File("E:\\javaLastWork\\chat\\src\\Model\\qq.mp3")));
        player = new Player(buffer);
        player.play();
    }
}
