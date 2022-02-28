package Minesweeper;
import java.awt.event.*;
import java.awt.*;
import java.util.Random;
import java.io.*;
import javax.sound.sampled.*;
import javax.swing.*;
public class GameplayFrame extends Frame {
    private Box[][] box;
    private int size, Width, Height, Bomb;
    private boolean lose, win;
    private File file;
    private AudioInputStream audio;
    private Clip chooseone, winner, loser;
    private ImageIcon icon;
    public GameplayFrame(int width, int height, int bomb, int solveLeft){
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.icon = new ImageIcon("spikebomb.jpg");
        this.setIconImage(this.icon.getImage());
        try{
            this.file = new File("chooseone.wav");
            this.audio = AudioSystem.getAudioInputStream(this.file);
            this.chooseone = AudioSystem.getClip();
            this.chooseone.open(this.audio);
            this.file = new File("winner.wav");
            this.audio = AudioSystem.getAudioInputStream(this.file);
            this.winner = AudioSystem.getClip();
            this.winner.open(this.audio);
            this.file = new File("loser.wav");
            this.audio = AudioSystem.getAudioInputStream(this.file);
            this.loser = AudioSystem.getClip();
            this.loser.open(this.audio);
        }
        catch(IOException | UnsupportedAudioFileException | LineUnavailableException e){
            e.printStackTrace();
        }
        Solve solve = new Solve(this, solveLeft, this.chooseone);
        if((width + height) / 2 <= 6) this.size = 75;
        else if((width + height) / 2 <= 10 && height <= 10) this.size = 50;
        else if((width + height) / 2 <= 20 && height <= 15) this.size = 35;
        else this.size = 25;
        this.Width = width;
        this.Height = height;
        this.Bomb = bomb;
        solve.setsolveenable(false);
        this.setSize(1925, 695);
        this.setVisible(true);
        this.setLayout(null);
        this.setResizable(false);
        box = new Box[height][width];
        this.lose = false;
        this.win = false;
        repaint();
        for(int i = 0; i < this.Height; i++){
            for(int j = 0; j < this.Width; j++){
                this.box[i][j] = new Box(10 + this.size * j, 50 + this.size * i, this.size);
                this.add(this.box[i][j]);
                this.box[i][j].setValue(i * j % 10);
                final int finalI = i, finalJ = j;
                this.box[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (!lose && !win) {
                            switch (e.getButton()) {
                                case MouseEvent.BUTTON1:
                                    if(solve.getsolveenable()){
                                        solve.setsolveenable(false);
                                        solve.setsolveleft(solve.getsolveleft() - 1);
                                        solve.showsolvetutorial(false);
                                        box[finalI][finalJ].LeftClickListener(true);
                                        solve.setlabelcontent("You have " + String.valueOf(solve.getsolveleft()) + " left");
                                    }
                                    else {
                                        if (box[finalI][finalJ].getValue() == 10) lose = true;
                                        else {
                                            ChooseNoneBombSquare(finalI, finalJ, box[finalI][finalJ].getValue());
                                            win = CheckWin();
                                        }
                                        box[finalI][finalJ].LeftClickListener(false);
                                    }
                                    playsound(chooseone);
                                    break;
                                case MouseEvent.BUTTON3:
                                    playsound(chooseone);
                                    box[finalI][finalJ].RightClickListener();
                                    break;
                            }
                            if(win) playsound(winner);
                            else if(lose) playsound(loser);
                            repaint();
                        }
                    }
                });
            }
        }
        this.BombSpawn(this.Bomb);
        this.SetBoard();
    }
    private void playsound(Clip sound){
        sound.stop();
        sound.setMicrosecondPosition(0);
        sound.start();
    }
    private boolean CheckWin(){
        int count = 0;
        for(int i = 0; i < this.Height; i++) for(int j = 0; j < this.Width; j++) if(!this.box[i][j].getLabel().equals(" ") && !this.box[i][j].getLabel().equals("N")) count++;
        return count >= this.Width * this.Height - this.Bomb - 1;
    }
    private void BombSpawn(int number){
        int height, width;
        Random rand = new Random();
        for(int i = 1; i <= number; i++){
            do {
                height = rand.nextInt(this.Height);
                width = rand.nextInt(this.Width);
            } while(this.box[height][width].getValue() == 10);
            this.box[height][width].setValue(10);
        }
    }
    private boolean CheckBombSurround(int height, int width){
        if(height < 0 || height >= this.Height || width < 0 || width >= this.Width || this.box[height][width].getValue() != 10) return false;
        return true;
    }
    private void SetBoard(){
        int dem;
        for(int i = 0; i < this.Height; i++){
            for(int j = 0; j < this.Width; j++){
                if(this.box[i][j].getValue() == 10) continue;
                dem = 0;
                if(this.CheckBombSurround(i, j + 1)) dem++;
                if(this.CheckBombSurround(i, j - 1)) dem++;
                if(this.CheckBombSurround(i + 1, j)) dem++;
                if(this.CheckBombSurround(i - 1, j)) dem++;
                if(this.CheckBombSurround(i + 1, j - 1)) dem++;
                if(this.CheckBombSurround(i + 1, j + 1)) dem++;
                if(this.CheckBombSurround(i - 1, j + 1)) dem++;
                if(this.CheckBombSurround(i - 1, j - 1)) dem++;
                this.box[i][j].setValue(dem);
            }
        }
    }
    private void FillGrayBackground(Graphics g){
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, 1500, 1500);
    }
    private void EndGame(Graphics g, String s){
        g.setColor(Color.RED);
        g.setFont(new Font("MV Boli", Font.ITALIC, 50));
        g.drawString(s, this.size * this.Width + 120, 80);
    }
    private void ChooseNoneBombSquare(int height, int width, int pre){
        if(height < 0 || height >= this.Height || width < 0 | width >= this.Width || pre != 0 || this.box[height][width].getLabel().equals("0")) return;
        else{
            this.box[height][width].LeftClickListener(false);
            this.ChooseNoneBombSquare(height, width + 1, this.box[height][width].getValue());
            this.ChooseNoneBombSquare(height, width - 1, this.box[height][width].getValue());
            this.ChooseNoneBombSquare(height + 1, width, this.box[height][width].getValue());
            this.ChooseNoneBombSquare(height - 1, width, this.box[height][width].getValue());
        }
    }
    @Override
    public void paint(Graphics g){
        this.FillGrayBackground(g);
        if(this.lose) {
            this.EndGame(g, "You Lose !");
            for(int i = 0; i < this.Height; i++) for(int j = 0; j < this.Width; j++) if(this.box[i][j].getValue() == 10) this.box[i][j].LeftClickListener(false);
        }
        else if(this.win) {
            this.EndGame(g, "You Win !");
            for(int i = 0; i < this.Height; i++) for (int j = 0; j < this.Width; j++) this.box[i][j].LeftClickListener(true);
        }
    }
}