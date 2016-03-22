package controller;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Observable;
import java.util.Observer;

import model.*;
import view.*;

public class OceanController extends JFrame implements Runnable{

    private Ocean ocean;
    private OceanView oceanView;
    private OceanStats oceanStats;
    private boolean running = false;
    private final int ROWS = 20;
    private final int COLS = 20;
    private int delay;
    private final int MAXCREATURES = ROWS * COLS;
    private JPanel algaePanel, bigFishPanel, littleFishPanel, mainPanel, controlPanel;
    private Thread thread;
    
    public OceanController(){
        super("Ocean Simulation");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e) {
            System.out.println("Error setting native LAF: " + e);
        }
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = getContentPane();
        ocean = new Ocean(ROWS, COLS);
        oceanView = new OceanView(ocean, this, ROWS, COLS);
        oceanStats = new OceanStats(ocean, this);
        container.add(oceanView, BorderLayout.CENTER);
        container.add(oceanStats, BorderLayout.SOUTH);
        ocean.addObserver(oceanView);
        ocean.addObserver(oceanStats);
        container.add(createControlPanel(), BorderLayout.EAST);
        pack();
        setResizable(false);
        setVisible(true);
    }

    private void init(){
        thread = new Thread(this);
        delay = 10000;
        thread.start();
    }  

    public JPanel createControlPanel(){
        controlPanel = new JPanel(new GridLayout(4, 1));
        algaePanel = createPanel("Algae", new GridLayout(2, 1));
        createLabel("<html><b>Number</b></html>", algaePanel);
        createSlider(0, MAXCREATURES, 0, "algaeNumSlider", false, algaePanel);
        createLabel("<html><b>Growth Rate</b></html>", algaePanel);
        createSlider(0, 100, 0, "alageGrowthSlider", false, algaePanel);
        littleFishPanel = createPanel("Little Fish",new GridLayout(3, 1));
        createLabel("<html><b>Number</b></html>", littleFishPanel);
        createSlider(0, MAXCREATURES, 0, "littleFishNumSlider", false, littleFishPanel);
        createLabel("<html><b>Gestation Period</b></html>", littleFishPanel);
        createSlider(0, 100, 0, "littleFishGestSlider", false, littleFishPanel);
        createLabel("<html><b>Starvation Rate</b></html>", littleFishPanel);
        createSlider(0, 100, 0, "littleFishStarveSlider", false, littleFishPanel);
        bigFishPanel = createPanel("Big Fish", new GridLayout(3, 1));
        createLabel("<html><b>Number</b></html>", bigFishPanel);
        createSlider(0, MAXCREATURES, 0, "bigFishNumSlider", false, bigFishPanel);
        createLabel("<html><b>Gestation Period</b></html>", bigFishPanel);
        createSlider(0, 100, 0, "bigFishGestSlider", false, bigFishPanel);
        createLabel("<html><b>Starvation Rate</b></html>", bigFishPanel);
        createSlider(0, 100, 0, "bigFishStarveSlider", false, bigFishPanel);
        mainPanel = createPanel("Main Controls", new GridLayout(2, 1));
        createLabel("<html><b>Delay (ms)</b></html>", mainPanel);
        createSlider(100, 1000, 1000, "delaySlider", true, mainPanel);
        createButton("Start", new ControlListener(ocean, oceanView, oceanStats), mainPanel);
        createButton("Reset", new ControlListener(ocean, oceanView, oceanStats), mainPanel);
        controlPanel.add(algaePanel);
        controlPanel.add(littleFishPanel);
        controlPanel.add(bigFishPanel);
        controlPanel.add(mainPanel);
        return controlPanel;
    }

    public JPanel createPanel(String title, LayoutManager layout){
        JPanel newJPanel = new JPanel();
        newJPanel.setPreferredSize(new Dimension(300, 0));
        newJPanel.setBorder(new TitledBorder(title));
        newJPanel.setLayout(layout);
        newJPanel.setName(title);
        return newJPanel;
    }

    public void createLabel(String title, JPanel panel){
        JLabel label = new JLabel(title);
        label.setFont(new Font("Verdana", Font.BOLD, 11));
        panel.add(label);
    }

    public void createSlider(int min, int max, int value, String name, Boolean invert, JPanel panel){
        JSlider newJSlider = new JSlider(min, max);
        newJSlider.setName(name);
        newJSlider.setInverted(invert);
        newJSlider.setValue(value);
        newJSlider.addChangeListener(new ControlListener(ocean, oceanView, oceanStats));
        panel.add(newJSlider);
    }

    public void createButton(String name, ControlListener controlListener, JPanel panel){
        JButton button = new JButton(name);
        button.addActionListener(controlListener);
        panel.add(button);
    }

    public void resetComponents(){
        Component[] panels = controlPanel.getComponents();
        for(int i=0; i<panels.length; i++){
            Component[] components = ((JComponent)panels[i]).getComponents();
            for(Component component : components){
                if (component instanceof JSlider){
                    if("delaySlider".equals(component.getName()))
                        ((JSlider)component).setValue(1000);
                    else {
                        ((JSlider)component).setValue(1);   
                        ((JSlider)component).setValue(0);
                    }
                }
            }
        }
    }
    

    public void enableComponents(Boolean enabled){
        Component[] panels = controlPanel.getComponents();
        for(int i=0; i<panels.length; i++){
            if(!("Main Controls".equals(panels[i].getName()))){
                Component[] components = ((JComponent)panels[i]).getComponents();
                for(Component component : components){
                    ((JComponent)component).setEnabled(enabled);
                }
            }
        }
    }

    @Override
    public void run(){
        while(true){
            if(running){	
	       ocean.step();
	       try{
	           Thread.sleep(delay);	
	       }
	       catch(InterruptedException e){
	           return;
	       }
            }
            else {
               try{
	           Thread.sleep(1);	
	       }
	       catch(InterruptedException e){
	           return;
	       }
           }
        }
    }

    public static void main(String args[]){
        OceanController oceanController = new OceanController();
        oceanController.init();
    }

    private class ControlListener extends AbstractAction implements ActionListener, ChangeListener{ 
        private Ocean ocean;
        private OceanView oceanView;
        private OceanStats oceanStats;

        public ControlListener(Ocean ocean, OceanView oceanView, OceanStats oceanStats){
            this.ocean = ocean;
            this.oceanView = oceanView;
            this.oceanStats = oceanStats;
        }

        public void actionPerformed(ActionEvent evt){
            String s = evt.getActionCommand();
            JButton button = (JButton) evt.getSource();
		if("Start".equals(s)){
			button.setActionCommand("Stop");
			button.setText("Stop");
                        enableComponents(false);
                        running = true;
                }
		else if("Stop".equals(s)){
			button.setActionCommand("Start");
			button.setText("Start");
                        enableComponents(true);
                        running = false;
                }
                else if("Reset".equals(s)){
                    enableComponents(true);
                    running = false;
                    ocean.setNumberOfAlgae(0);
                    ocean.setAlgaeGrowthProbability(0);
                    ocean.setNumberOfLittleFish(0);
                    ocean.setLittleFishGestationPeriod(0);
                    ocean.setLittleFishStarvationPeriod(0);
                    ocean.setNumberOfBigFish(0);
                    ocean.setBigFishGestationPeriod(0);
                    ocean.setBigFishStarvationPeriod(0);
                    delay = 1000;
                    oceanStats.resetStepNumber();
                    resetComponents();
                    oceanView.repaint();
                    oceanStats.repaint();
                }
        }

        public void stateChanged(ChangeEvent evt){
            JSlider slider = (JSlider) evt.getSource();
            if(!slider.getValueIsAdjusting()){
                String name = slider.getName();
                int value = slider.getValue();
                if("algaeNumSlider".equals(name)){
                    if(!ocean.setNumberOfAlgae(value))
			slider.setValue(ocean.getNumberOfAlgae());
                }
                else if("alageGrowthSlider".equals(name)){
                    ocean.setAlgaeGrowthProbability((double)value/100);
                }
                else if("littleFishNumSlider".equals(name)){
                    if(!ocean.setNumberOfLittleFish(value))
			slider.setValue(MAXCREATURES - ocean.getNumberOfBigFish());
                }
                else if("littleFishGestSlider".equals(name)){
                    ocean.setLittleFishGestationPeriod(value);
                }
                else if("littleFishStarveSlider".equals(name)){
                    ocean.setLittleFishStarvationPeriod(value);
                }
                else if("bigFishNumSlider".equals(name)){
                    if(!ocean.setNumberOfBigFish(value))
			slider.setValue(MAXCREATURES - ocean.getNumberOfLittleFish());
                }
                else if("bigFishGestSlider".equals(name)){
                    ocean.setBigFishGestationPeriod(value);
                }
                else if("bigFishStarveSlider".equals(name)){
                    ocean.setBigFishStarvationPeriod(value);
                }
                else if("delaySlider".equals(name)){
                    delay = value;
                }
            }
            oceanView.repaint();
            oceanStats.repaint();
        }
    }
}
