package org.unibl.etf.dvukadinovic.map;

import org.unibl.etf.dvukadinovic.passengers.BusPassenger;
import org.unibl.etf.dvukadinovic.passengers.Passenger;
import org.unibl.etf.dvukadinovic.report.ReportRecord;
import org.unibl.etf.dvukadinovic.util.Pair;
import org.unibl.etf.dvukadinovic.vehicle.Car;
import org.unibl.etf.dvukadinovic.vehicle.Bus;
import org.unibl.etf.dvukadinovic.vehicle.Vehicle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class BorderThread extends Thread{

    private static Logger logger = Logger.getLogger(BorderThread.class.getName());
    static {
        try {
            logger.addHandler(new FileHandler("./logs/borderThread"+System.nanoTime()));

        }catch (Exception e){
            logger.warning(e.getMessage());
        }
    }

    private Pair<Vehicle, Boolean> content;
    private Pair<Boolean, Boolean> status;

    public BorderThread(Pair<Vehicle, Boolean> content, Pair<Boolean, Boolean> status){
        this.content = content;
        this.status = status;
        //System.out.println(content.getFirst()+ " "+ content.getSecond());
        setDaemon(true);
    }

    public void run(){

        while (true){
            //System.out.print("");
            //try{
            //   Thread.sleep(0);
            //}catch (InterruptedException e){}
            Thread.yield();

            if (content.getSecond() && content.getFirst()!=null){
                synchronized (content.getFirst()){
                    List<Passenger> passengers = content.getFirst().getPassengers();
                    if(!passengers.get(0).process().getFirst()){
                        status.setSecond(false);
                        status.setFirst(true);
                        content.setSecond(false);
                        ReportRecord.createRecord(content.getFirst(), null);
                    }else {
                        passengers = passengers.stream().filter(el-> {
                            try {
                                if(el instanceof BusPassenger){
                                    Thread.sleep(100);
                                }else {
                                    Thread.sleep(500);
                                }
                            }catch (InterruptedException e){
                                logger.warning(e.getMessage());
                            }
                            if(!el.process().getFirst()){
                                ReportRecord.createRecord(content.getFirst(), el);
                            }
                            return el.process().getFirst();
                        }).toList();

                        //System.out.println(content.getFirst().id + " "+ content.getFirst());
                        try {
                            content.getFirst().setPassengers(passengers);
                        }catch (NullPointerException e){
                            logger.warning(e.getMessage());
                        }
                        status.setFirst(true);
                        //content.setSecond(false);
                    }


                }

            }
        }

    }
}
