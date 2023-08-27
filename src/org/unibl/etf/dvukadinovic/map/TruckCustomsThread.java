package org.unibl.etf.dvukadinovic.map;

import org.unibl.etf.dvukadinovic.passengers.Passenger;
import org.unibl.etf.dvukadinovic.report.ReportRecord;
import org.unibl.etf.dvukadinovic.util.Pair;
import org.unibl.etf.dvukadinovic.vehicle.Truck;
import org.unibl.etf.dvukadinovic.vehicle.Vehicle;

import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class TruckCustomsThread extends CustomsThread{
    private Pair<Vehicle, Boolean> content;
    private Pair<Boolean, Boolean> status;

    public TruckCustomsThread(Pair<Vehicle, Boolean> content, Pair<Boolean, Boolean> status){
        super(content, status);
        this.content = content;
        this.status = status;
        setDaemon(true);
    }

    public void run(){
        while (true){
            if (content.getSecond() && content.getFirst()!=null){
                synchronized (content.getFirst()){
                    Truck truck = (Truck) content.getFirst();
                    if(truck.getDeclaredWeight()<truck.getActualWeight()){
                        status.setSecond(false);
                        status.setFirst(true);
                        writeReport(truck, null);
                    }else{
                        status.setFirst(true);
                        content.setSecond(false);
                        if(truck.doesNeedPapers()){
                            truck.generateDocumentation();
                        }
                    }
                }
            }
            Thread.yield();
        }

    }
}
