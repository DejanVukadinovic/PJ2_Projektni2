package org.unibl.etf.dvukadinovic.map;

import org.unibl.etf.dvukadinovic.report.ReportRecord;
import org.unibl.etf.dvukadinovic.vehicle.Vehicle;
import org.unibl.etf.dvukadinovic.vehicle.Truck;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class TruckBorderField extends BorderField{
    private static Logger logger = Logger.getLogger(TruckBorderField.class.getName());
    static {
        try {
            logger.addHandler(new FileHandler("./logs/truckBorderField"+System.nanoTime()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public Boolean isAvaliable(Vehicle vehicle){
        ArrayList<Integer> statusList = new ArrayList<>();
        try(BufferedReader bf = new BufferedReader(new FileReader("./src/setup.txt"))){
            String line = bf.readLine();
            while (line != null) {
                ArrayList<String> tmp = new ArrayList<>(List.of(line.split(",")));
                for(String e:tmp){
                    statusList.add(Integer.parseInt(e));
                }
                line = bf.readLine();
            }
        }catch (IOException e){
            logger.warning(e.getMessage());
        }
        return (!content.getSecond() && (vehicle instanceof Truck) && statusList.get(id)!=0);
    }
}