package Minesweeper;
import java.awt.*;
public class Box extends Button{
    private int value;
    public Box(int x, int y, int side){
        this.setBounds(x, y, side, side);
        this.setLabel(" ");
    }
    public void setValue(int value){
        this.value = value;
    }
    public int getValue(){
        return this.value;
    }
    public void LeftClickListener(boolean solvemode) {
        if(solvemode){
            this.setForeground(this.BoxColor());
            if(this.value != 10) this.setLabel(String.valueOf(this.value));
            else this.setLabel("B");
        }
        else if (this.getLabel().equals(" ")) {
            this.setForeground(this.BoxColor());
            if(this.value != 10) this.setLabel(String.valueOf(this.value));
            else this.setLabel("B");
        }
    }
    public void RightClickListener(){
        if(this.getLabel().equals(" ")) {
            this.setForeground(new Color(148, 0, 211));
            this.setLabel("N");
        }
        else if(this.getLabel().equals("N")) this.setLabel(" ");
    }
    private Color BoxColor(){
        switch(this.value){
            case 1:
                return Color.RED;
            case 2:
                return Color.BLUE;
            case 3:
                return Color.GREEN;
            case 4:
                return Color.PINK;
            case 5:
                return Color.CYAN;
            case 6:
                return new Color(128, 128, 0);
            case 7:
                return Color.MAGENTA;
            case 8:
                return new Color(75, 0, 30);
            case 9:
                return Color.GRAY;
            case 10:
                return new Color(107, 189, 235);
        }
        return Color.BLACK; //Case 0
    }
}
