package org.unibl.etf.dvukadinovic.vehicle;

import org.unibl.etf.dvukadinovic.passengers.Passenger;
import org.unibl.etf.dvukadinovic.report.ReportRecord;
import org.unibl.etf.dvukadinovic.util.Pair;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class Vehicle extends Thread implements Serializable {

    private static Logger logger = Logger.getLogger(Vehicle.class.getName());
    static {
        try {
            logger.addHandler(new FileHandler("./logs/vehicle"+System.nanoTime()));

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private transient RoadMap map;
    private transient Pair<Integer, Integer> position;
    protected List<Passenger> passengers = Collections.synchronizedList(new ArrayList<>());
    static int vNum = 0;
    public int id = vNum;
    private transient Pair<Boolean, Boolean> simMode;
    static long ts = System.nanoTime();

    public int getVehicleId(){
        return id;
    }

    public Vehicle(RoadMap map, int maxNumOfPassengers, Pair<Boolean, Boolean> simMode){
        this.map = map;
        this.simMode = simMode;
        position = new Pair<>(map.getSize().getFirst()-1, 1);
        int numOfPassengers = 1 + (int) (Math.random() * maxNumOfPassengers);
        for (int i = 0; i <numOfPassengers; i++) {
            passengers.add(new Passenger());
        }
        setDaemon(false);
        vNum++;
    }
    public Vehicle(RoadMap map, Pair<Boolean, Boolean> simMode){
        this.map = map;
        this.simMode = simMode;
        position = new Pair<>(map.getSize().getFirst()-1, 1);
        setDaemon(false);
        vNum++;
    }
    public void run(){
        while (position.getFirst()!=-1){
            if(!simMode.getFirst()){
            if(map.getStatus(position).getSecond()){
                if(map.getStatus(position).getFirst()){
                    for (int i = 0; i < map.getSize().getSecond(); i++) {
                        synchronized (map){
                            Pair<Integer, Integer> newPosition = new Pair<>(position.getFirst()-1, i);
                            if (map.getAvailability(newPosition, this)){
                                map.setContent(newPosition, this);
                                map.setContent(position, null);
                                position=newPosition;
                                break;

                            }
                        }

                    }
                }
            }else{
                break;

            }
            }
            try {
                Thread.sleep(20);
            }catch (InterruptedException e ){
                logger.warning(e.getMessage());
            }
        }
    }
    public List<Passenger> getPassengers(){
        return this.passengers;
    }
    public void setPassengers(List<Passenger> passengers){
        this.passengers = passengers;

    }
}
