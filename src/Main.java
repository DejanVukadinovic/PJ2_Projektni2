import org.unibl.etf.dvukadinovic.map.Map;
import org.unibl.etf.dvukadinovic.passengers.Bag;
import org.unibl.etf.dvukadinovic.passengers.BusPassenger;
import org.unibl.etf.dvukadinovic.passengers.Passenger;
import org.unibl.etf.dvukadinovic.report.ReportRecord;
import org.unibl.etf.dvukadinovic.simulation.SimulationUIThread;
import org.unibl.etf.dvukadinovic.util.Pair;
import org.unibl.etf.dvukadinovic.vehicle.*;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        /*
        System.out.println("Hello world!");
        Pair<String, String> p = new Pair<String, String>("Hello", "World");
        System.out.println(vehicles);
        try {
            Thread.sleep(10_000);
        }catch (InterruptedException e){}
        System.out.println(ReportRecord.getRecord());
        */




        Map map = new Map();
        long startTime = System.nanoTime();
        Pair<Double, Boolean> simStatus = new Pair<>(0.0, false);
        Pair<Boolean, Boolean> simMode = new Pair<>(false, false);
        Boolean paused = false;
        JFrame frame = new JFrame();
        frame.setSize(500,900);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SimulationUIThread smp = new SimulationUIThread(map, simStatus, simMode);
        List<Vehicle> vehicles = VehicleQueueBuilder.buildQueue(map, 5,3,2, simMode);

        Thread t1 = new Thread(smp);
        t1.start();
        frame.add(smp);
        frame.setVisible(true);
        for(Vehicle vehicle : vehicles){
            vehicle.start();
        }
        while (true){
            if(vehicles.stream().noneMatch(Thread::isAlive)){
                System.out.println("It's done");
                simStatus.setSecond(true);
                ReportRecord.serializeRecord();
                break;
            }else{
                simStatus.setFirst((double)(System.nanoTime() - startTime)/1_000_000_000);
                if(simMode.getFirst()){
                }
            }
        }


    }
}