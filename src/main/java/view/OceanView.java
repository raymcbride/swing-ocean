package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;
import java.awt.geom.*;

import model.*;
import controller.*;

public class OceanView extends JPanel implements Observer {
    private OceanController oceanController;
    private Ocean ocean;
    private int rows;
    private int cols;
    private final int HEIGHT = 600;
    private final int WIDTH = 600;
    private ImageIcon bigFishImage = new ImageIcon(getClass().getResource("/img/bigfish.png"));
    private ImageIcon littleFishImage = new ImageIcon(getClass().getResource("/img/littlefish.png"));
    private ImageIcon algaeImage = new ImageIcon(getClass().getResource("/img/algae.png"));
    private Color bgColour = new Color(17,166,203);
    private Color highlight = new Color(14,207,255);
    private Color gridColor = new Color(14,189,233);
    private Color lowlight = new Color(10,151,186);

    public OceanView(Ocean ocean, OceanController oceanController, int rows, int cols){
        super();
        this.ocean = ocean;
        this.oceanController = oceanController;
        this.rows = rows;
        this.cols = cols;
	setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setVisible(true);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        paintBackground(g2d);
        paintGrid(g2d);
        for(int i=0; i<rows; i++){
            for(int j=0; j<cols; j++){
                if(ocean.containsBigFish(i, j)){
                    g2d.drawImage(bigFishImage.getImage(), j * (WIDTH/cols)-5, i * (HEIGHT/rows)-5, this);
                }
                if(ocean.containsLittleFish(i, j)){
                    g2d.drawImage(littleFishImage.getImage(), j * (WIDTH/cols)-5, i * (HEIGHT/rows)-5, this);
                }
                if(ocean.containsAlgae(i, j)){
                    g2d.drawImage(algaeImage.getImage(), j * (WIDTH/cols)-5, i * (HEIGHT/rows)-5, this);
                }
            }
        }
    }

    private void paintBackground(Graphics2D g2d){
        g2d.setPaint(bgColour);
        Rectangle2D.Double background = new Rectangle2D.Double(0, 0, WIDTH, HEIGHT);
        g2d.fill(background);
    }

    private void paintGrid(Graphics2D g2d){
        for(int i=0; i<rows; i++){
	   for(int j=0; j<cols; j++){
            paintLocation(g2d, i, j);
           }
        }
    }

    private void paintLocation(Graphics2D g2d, int row, int col){
        g2d.setPaint(highlight);
        Rectangle2D.Double location = new Rectangle2D.Double(col * (WIDTH/cols) - 1, row * (HEIGHT/rows)- 1,  WIDTH/cols -2,  HEIGHT/rows -2);
        g2d.fill(location);
        g2d.setPaint(lowlight);
        location = new Rectangle2D.Double(col * (WIDTH/cols) + 1, row * (HEIGHT/rows) + 1, WIDTH/cols - 3,  HEIGHT/rows - 3);
        g2d.fill(location);
        g2d.setPaint(gridColor);
        location = new Rectangle2D.Double(col * (WIDTH/cols), row * (HEIGHT/rows), WIDTH/cols -3,  HEIGHT/rows -3);
        g2d.fill(location);
    }

    public void update(Observable obs, Object arg){
        repaint();
    }
}
