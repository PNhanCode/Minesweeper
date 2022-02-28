package Minesweeper;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
public class SetGameConfigFrame extends Frame{
    private ImageIcon icon;
    private File file;
    private AudioInputStream audio;
    private Clip chooseone;
    private void SetAndAdd(int x, int y, int width, int height, Component object){
        object.setBounds(x, y, width, height);
        this.add(object);
    }
    private void playsound(Clip sound){
        sound.stop();
        sound.setMicrosecondPosition(0);
        sound.start();
    }
    public SetGameConfigFrame(){
        try{
            this.file = new File("chooseone.wav");
            this.audio = AudioSystem.getAudioInputStream(this.file);
            this.chooseone = AudioSystem.getClip();
            this.chooseone.open(this.audio);
        }
        catch(IOException | UnsupportedAudioFileException | LineUnavailableException e){
            e.printStackTrace();
        }
        this.setSize(1000, 600);
        this.setVisible(true);
        this.setLayout(null);
        this.icon = new ImageIcon("spikebomb.jpg");
        this.setIconImage(this.icon.getImage());
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        Button start = new Button("Start!"), easy = new Button("Easy"), normal = new Button("Normal"), hard = new Button("Hard");
        TextField tfwidth = new TextField(), tfheight = new TextField(), tfbomb = new TextField();
        Label lwidth = new Label("Width : "), lheight = new Label("Height : "), lbomb = new Label("Bomb : "), tutorial = new Label("Width must be lower than 31, height must be lower than 25, bomb must be lower than or equal to half of multuply of width and height, the minium value is 6"), wrong = new Label("Wrong Syntax!"), easymode = new Label("Easy : You have 3 solves, the area is 9 x 9 and there are 10 mines"), normalmode = new Label("Normal : you have 2 solves, the area is 16 x 16 and there are 40 mines"), hardmode = new Label("Hard : You have 1 solves, the area is 30 x 16 and there are 99 mines"), custommode = new Label("Custom : You have 3 solves and everything is custom");
        wrong.setBounds(480, 60, 200, 30);
        this.SetAndAdd(70, 30, 1000, 30, tutorial);
        this.SetAndAdd(440, 100, 50, 30, lwidth);
        this.SetAndAdd(491, 105, 100, 20, tfwidth);
        this.SetAndAdd(440, 140, 50, 30, lheight);
        this.SetAndAdd(491, 145, 100, 20, tfheight);
        this.SetAndAdd(440, 180, 50, 30, lbomb);
        this.SetAndAdd(491, 185, 100, 20, tfbomb);
        this.SetAndAdd(485, 225, 70, 30, start);
        this.SetAndAdd(414, 270, 70, 30, easy);
        this.SetAndAdd(485, 270, 70, 30, normal);
        this.SetAndAdd(556, 270, 70, 30, hard);
        this.SetAndAdd(350, 310, 800, 30, easymode);
        this.SetAndAdd(350, 340, 800, 30, normalmode);
        this.SetAndAdd(350, 370, 800, 30, hardmode);
        this.SetAndAdd(350, 400, 800, 30, custommode);
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playsound(chooseone);
                String swidth = tfwidth.getText(), sheight = tfheight.getText(), sbomb = tfbomb.getText();
                int width = toValue(swidth), height = toValue(sheight), bomb = toValue(sbomb);
                if(CheckBeforeStart(width, height, bomb)){
                    setVisible(false);
                    new GameplayFrame(width, height, bomb, 3);
                }
                else add(wrong);
            }
        });
        easy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playsound(chooseone);
                setVisible(false);
                new GameplayFrame(9, 9, 10, 3);
            }
        });
        normal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playsound(chooseone);
                setVisible(false);
                new GameplayFrame(16, 16, 40, 2);
            }
        });
        hard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playsound(chooseone);
                setVisible(false);
                new GameplayFrame(30, 16, 99, 1);
            }
        });
    }
    private int toValue(String s){
        int res = 0;
        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) >= '0' && s.charAt(i) <= '9') res = res * 10 + s.charAt(i) - 48;
            else return -1;
        }
        if(res != 0) return res;
        else return -1;
    }
    private boolean CheckBeforeStart(int width, int height, int bomb){
        if(width > 30 || height > 24 || width < 0 || height < 0 || bomb > width * height / 2 || width < 6 || height < 6 || bomb < 6) return false;
        return true;
    }
}
