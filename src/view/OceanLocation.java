package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;

import model.*;
import controller.*;

public class OceanLocation extends JPanel{
    Ocean ocean;
    int row;
    int col;
    Canvas c1, c2;

    public OceanLocation(Ocean ocean, int row, int col){
        super();
        this.ocean = ocean;
        this.row = row;
        this.col = col;
        c1 = new Canvas();
        c2 = new Canvas();
        setLayout(new GridLayout(2,1));
        Color locationColor = new Color(14,189,233);
        c1.setBackground(locationColor);
        c2.setBackground(locationColor);
        add(c1);
	add(c2);
	setVisible(true);
    }
        
}
    

