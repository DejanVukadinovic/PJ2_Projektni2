package org.unibl.etf.dvukadinovic.simulation;


import org.unibl.etf.dvukadinovic.map.BorderField;
import org.unibl.etf.dvukadinovic.map.CustomsField;
import org.unibl.etf.dvukadinovic.map.Field;
import org.unibl.etf.dvukadinovic.map.Map;
import org.unibl.etf.dvukadinovic.report.ReportRecord;
import org.unibl.etf.dvukadinovic.util.Pair;
import org.unibl.etf.dvukadinovic.vehicle.Bus;
import org.unibl.etf.dvukadinovic.vehicle.Car;
import org.unibl.etf.dvukadinovic.vehicle.Truck;
import org.unibl.etf.dvukadinovic.vehicle.Vehicle;

import java.util.ArrayList;
import java.awt.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import javax.swing.*;

public class SimulationUIThread extends JPanel implements Runnable {
    private static Logger logger = Logger.getLogger(SimulationUIThread.class.getName());
    static {
        try {
            logger.addHandler(new FileHandler("./logs/simulationUi"+System.nanoTime()));

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private Map map;
    Pair<Double, Boolean> status;
    Pair<Boolean, Boolean> simMode;
    long pauseStart = 0;
    double totalPauseTime = 0.0;
    public SimulationUIThread(Map map){
        this.map=map;
    }
    public SimulationUIThread(Map map, Pair<Double, Boolean> simStatus, Pair<Boolean, Boolean> simMode){
        this.map=map;
        this.status=simStatus;
        this.simMode = simMode;
    }

    @Override
    public void run() {
        ArrayList<ArrayList<JButton>> buttons = new ArrayList<ArrayList<JButton>>();
        JButton btn = new JButton("lasting: ");

        this.add(btn);
        //Pair<Integer, Integer> size = map.getSize();
        for (int i = 0; i < 7; i++) {
            JPanel panel = new JPanel();
            panel.setLayout(new FlowLayout());
            panel.setPreferredSize(new Dimension(1920, 50));
            panel.setBackground(new Color(0x999999));

            ArrayList<JButton> jblist = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                JButton fb = new JButton();

                if(map.getField(i, j)!=null){

                    final int fi = i, fj = j;
                    fb.addActionListener(e->{
                        JFrame modal = new JFrame();
                        modal.setSize(200,100);
                        try {
                            modal.setLayout(new FlowLayout());
                            Vehicle v = map.getField(fi, fj).getContent();
                            modal.add(new Label(v.toString()));
                            modal.add(new Label("Passengers: "+ v.getPassengers().size()));
                            modal.add(new Label(""+ v.getPassengers()));

                        }catch (NullPointerException exception){
                            logger.warning(exception.getMessage());
                        }
                        modal.setVisible(true);
                    });
                }
                jblist.add(fb);
                fb.setPreferredSize(new Dimension(70,50));
                if(map.getField(i,j) instanceof BorderField){
                    fb.setBackground(new Color(0x3291ff));
                }else if(map.getField(i,j) instanceof CustomsField){
                    fb.setBackground(new Color(0xff9132));
                }

                if(map.getField(i,j)!=null ){
                    Vehicle v = map.getField(i,j).getContent();
                    if(v==null) {
                        fb.setText("FR");
                    }else{
                        if (v instanceof Car){
                            fb.setText("C");
                            fb.setBackground(new Color(0xFF0000));
                        }else  if (v instanceof Bus){
                            fb.setText("B");
                            fb.setBackground(new Color(0x00FF00));
                        }else  if (v instanceof Truck){
                            fb.setText("T");
                            fb.setBackground(new Color(0x0000FF));
                        }
                    }
                }

                panel.add(fb);
            }
            buttons.add(jblist);
            this.add(panel);
        }
        JPanel endPanel = new JPanel();
        JButton pauseBtn = new JButton("Pause");
        pauseBtn.addActionListener(e->{
            if(!simMode.getFirst()){
                pauseStart = System.nanoTime();
                simMode.setFirst(true);

            }else {
                totalPauseTime += (double) (System.nanoTime()-pauseStart)/1_000_000_000;
                simMode.setFirst(false);
            }
        });
        JButton reportsBtn = new JButton("Reports");
        reportsBtn.setEnabled(false);
        reportsBtn.addActionListener(e->{
            JFrame modal = new ReportsModal();
            modal.setSize(200,100);
            modal.setVisible(true);
        });
        JButton restBtn = new JButton("Rest of Vehicles");
        endPanel.add(pauseBtn);
        endPanel.add(restBtn);
        endPanel.add(reportsBtn);
        this.add(endPanel);
        JPanel restPanel = new JPanel();
        restPanel.setLayout(new FlowLayout());
        restPanel.setPreferredSize(new Dimension(500, 400));
        ArrayList<JButton> restButtons = new ArrayList<>();
        for (int i = 7; i < map.getSize().getFirst(); i++) {
            JButton fb = new JButton();
            Vehicle v = map.getField(i,1).getContent();
            if(v==null) {
                fb.setText("FR");
            }else{
                if (v instanceof Car){
                    fb.setText("C");
                    fb.setBackground(new Color(0xFF0000));
                }else  if (v instanceof Bus){
                    fb.setText("B");
                    fb.setBackground(new Color(0x00FF00));
                }else  if (v instanceof Truck){
                    fb.setText("T");
                    fb.setBackground(new Color(0x0000FF));
                }
            }
            restButtons.add(fb);
            restPanel.add(fb);
        }
        this.add(restPanel);
        while (true) {
            if (!simMode.getFirst()) {
            if(status.getSecond()){
                reportsBtn.setEnabled(true);
            }

            btn.setText("lasting: " + (status.getFirst() - totalPauseTime));
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 3; j++) {
                    Field f = map.getField(i, j);
                    JButton fb = buttons.get(i).get(j);
                    if (f != null) {
                        Vehicle v = map.getField(i, j).getContent();
                        if (v != null) {
                            if (v instanceof Car) {
                                fb.setText("C");
                                fb.setBackground(new Color(0xFF0000));
                            } else if (v instanceof Bus) {
                                fb.setText("B");
                                fb.setBackground(new Color(0x00FF00));
                            } else if (v instanceof Truck) {
                                fb.setText("T");
                                fb.setBackground(new Color(0x0000FF));
                            }
                        } else {
                            fb.setText("FR");
                            fb.setBackground(new Color(0xeeeeee));

                        }
                    }
                }
            }
            for (int i = 0; i < restButtons.size(); i++) {
                JButton fb = restButtons.get(i);
                Vehicle v = map.getField(i+7,1).getContent();
                fb.setBackground(new Color(0xFFFFFF));
                if(v==null) {
                    fb.setText("FR");
                }else{
                    if (v instanceof Car){
                        fb.setText("C");
                        fb.setBackground(new Color(0xFF0000));
                    }else  if (v instanceof Bus){
                        fb.setText("B");
                        fb.setBackground(new Color(0x00FF00));
                    }else  if (v instanceof Truck){
                        fb.setText("T");
                        fb.setBackground(new Color(0x0000FF));
                    }
                }
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                logger.warning(e.getMessage());
            }
            if (status.getSecond()) {
                //break;
            }
        }else {
                System.out.println("Paused");

            }
        }
    }
}
