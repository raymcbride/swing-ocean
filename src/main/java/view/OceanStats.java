package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;

import model.*;
import controller.*;

public class OceanStats extends JPanel implements Observer {

    private Ocean ocean;
    private OceanController oceanController;
    private int stepNumber;
    

    public OceanStats(Ocean ocean, OceanController oceanController){
        super();
        this.ocean = ocean;
        this.oceanController = oceanController;
        setPreferredSize(new Dimension(0, 50));
        setBorder(BorderFactory.createEtchedBorder());
        stepNumber = 0;
    }        

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        g2d.setFont(new Font("Verdana", Font.BOLD, 11));
        RenderingHints renderHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        renderHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHints(renderHints);
        g2d.drawString("Number of Algae: " + ocean.getNumberOfAlgae() + " " + "Number of Little Fish: " + ocean.getNumberOfLittleFish() + " " + "Number of Big Fish: " + ocean.getNumberOfBigFish() + " " + "Step Number: " + stepNumber + " ", 150, 30);
    }

    public void resetStepNumber(){
        stepNumber = 0;
    }

    public void update(Observable obs, Object arg){
        stepNumber++;
        repaint();
    }

}