package org.unibl.etf.dvukadinovic.map;

import org.unibl.etf.dvukadinovic.report.Report;
import org.unibl.etf.dvukadinovic.report.ReportRecord;
import org.unibl.etf.dvukadinovic.vehicle.Car;
import org.unibl.etf.dvukadinovic.vehicle.Truck;
import org.unibl.etf.dvukadinovic.vehicle.Vehicle;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class CustomsField extends Field{

    private static Logger logger = Logger.getLogger(CustomsField.class.getName());
    static {
        try {
            logger.addHandler(new FileHandler("./logs/customsField"+System.nanoTime()));

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private CustomsThread bThread;
    private static int bfNum = 0;
    public int id = bfNum;

    public CustomsField(){
        bThread = new CustomsThread(content, status);
        bThread.start();
        bfNum++;
    }
    public Boolean isAvaliable(Vehicle vehicle){
        //System.out.println("Border field avaliability: "+id);
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
        return (!content.getSecond() && !(vehicle instanceof Truck) && statusList.get(id+2)!=0);


    }

    public void setContent(Vehicle content){
        super.setContent(content);
        System.out.println("Processing vehicle: "+content);
        if(content!=null){
            status.setFirst(false);
            status.setSecond(true);

        }


    }
}
