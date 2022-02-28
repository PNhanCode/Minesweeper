package Minesweeper;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
public class Solve {
    private int solveleft;
    private Label solvetutoriallabel, solveleftlabel;
    private Button solvebutton;
    private boolean solveenable;
    public Solve(Frame frame, int solvelefts, Clip choose){
        this.solvebutton = new Button("Solve!");
        this.solveleft = solvelefts;
        this.solvetutoriallabel = new Label("Left click on any square to escape solve mode!");
        this.solveleftlabel = new Label("You have " + String.valueOf(this.solveleft) + " left");
        this.solvebutton.setBounds(1000, 125, 100, 50);
        this.solveleftlabel.setBounds(1000, 90, 200, 30);
        this.solvetutoriallabel.setBounds(1000, 180, 300, 30);
        frame.add(this.solvebutton);
        frame.add(this.solveleftlabel);
        frame.add(this.solvetutoriallabel);
        this.solvetutoriallabel.setVisible(false);
        this.solvebutton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON1 && solveleft > 0) {
                    if(!solveenable) {
                        solvetutoriallabel.setVisible(true);
                        solveenable = true;
                        playsound(choose);
                    }
                    else{
                        solveenable = false;
                        playsound(choose);
                        solvetutoriallabel.setVisible(false);
                    }
                }
            }
        });
    }
    private void playsound(Clip sound){
        sound.stop();
        sound.setMicrosecondPosition(0);
        sound.start();
    }
    public void showsolvetutorial(boolean show){
        this.solvetutoriallabel.setVisible(show);
    }
    public void setsolveleft(int solveleft){
        this.solveleft = solveleft;
    }
    public int getsolveleft(){
        return this.solveleft;
    }
    public void setlabelcontent(String s){
        this.solveleftlabel.setText(s);
    }
    public void setsolveenable(boolean solveenable){
        this.solveenable = solveenable;
    }
    public boolean getsolveenable(){
        return this.solveenable;
    }
}
