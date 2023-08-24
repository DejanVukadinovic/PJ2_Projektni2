package org.unibl.etf.dvukadinovic.map;

import org.unibl.etf.dvukadinovic.report.ReportRecord;
import org.unibl.etf.dvukadinovic.vehicle.Car;
import org.unibl.etf.dvukadinovic.vehicle.Truck;
import org.unibl.etf.dvukadinovic.vehicle.Vehicle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class BorderField extends Field{

    private static Logger logger = Logger.getLogger(BorderField.class.getName());
    static {
        try {
            logger.addHandler(new FileHandler("./logs/borderField"+System.nanoTime()));

        }catch (Exception e){
            logger.warning(e.getMessage());
        }
    }

    private BorderThread bThread;
    private static int bfNum = 0;
    protected int id = bfNum;

    public BorderField(){
        bThread = new BorderThread(content, status);
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
        //System.out.println(statusList.get(id));

        return (!content.getSecond() && !(vehicle instanceof Truck) && statusList.get(id)!=0);
    }

    public void setContent(Vehicle content){
        super.setContent(content);
        //System.out.println("Border field:"+id);
        if(content!=null){
            status.setFirst(false);
            status.setSecond(true);

        }

    }
}
