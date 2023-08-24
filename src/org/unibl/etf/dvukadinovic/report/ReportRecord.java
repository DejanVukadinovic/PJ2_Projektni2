package org.unibl.etf.dvukadinovic.report;

import org.unibl.etf.dvukadinovic.passengers.Passenger;
import org.unibl.etf.dvukadinovic.vehicle.Vehicle;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class ReportRecord {

    private static ArrayList<Report> record = new ArrayList<>();
    private static Logger logger = Logger.getLogger(ReportRecord.class.getName());
    static {
        try {
            logger.addHandler(new FileHandler("./logs/records"+System.nanoTime()));

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static String recordString = "./records/record"+System.nanoTime()+".ser";
    public static String customsRecordString = "./records/customs"+System.nanoTime()+".txt";

    public static void createRecord(Vehicle v, Passenger p){
        record.add(new Report(v, p));
    }
    public static ArrayList<Report> getRecord(){
        return record;
    }

    public static void serializeRecord(){

        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(recordString))){
            oos.writeObject(record);
            oos.flush();
        }catch (IOException e){
            logger.warning(e.getMessage());
            e.printStackTrace();
        }
    }
}
