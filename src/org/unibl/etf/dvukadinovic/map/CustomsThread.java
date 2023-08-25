package org.unibl.etf.dvukadinovic.map;

import org.unibl.etf.dvukadinovic.passengers.Passenger;
import org.unibl.etf.dvukadinovic.report.ReportRecord;
import org.unibl.etf.dvukadinovic.util.Pair;
import org.unibl.etf.dvukadinovic.vehicle.Car;
import org.unibl.etf.dvukadinovic.vehicle.Bus;
import org.unibl.etf.dvukadinovic.vehicle.Vehicle;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class CustomsThread extends Thread{
    private static Logger logger = Logger.getLogger(CustomsThread.class.getName());
    static {
        try {
            logger.addHandler(new FileHandler("./logs/customsThread"+System.nanoTime()));

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private Pair<Vehicle, Boolean> content;
    private Pair<Boolean, Boolean> status;



    public CustomsThread(Pair<Vehicle, Boolean> content, Pair<Boolean, Boolean> status){
        this.content = content;
        this.status = status;
        //System.out.println(content.getFirst()+ " "+ content.getSecond());
        setDaemon(true);
    }

    protected void writeReport(Vehicle v, Passenger p){
        try(RandomAccessFile raf = new RandomAccessFile(ReportRecord.customsRecordString, "rw")){
            FileChannel channel = raf.getChannel();
            FileLock lock = channel.lock();
            raf.seek(raf.length());
            if(p == null){
                raf.writeBytes(v.toString() +" Fatal!\n");

            }else {
                raf.writeBytes(v.toString() +" "+ p + "\n");
            }
        }catch (Exception e){
            logger.warning(e.getMessage());
        }
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
                    if(content.getFirst() instanceof Car){
                        System.out.println("It's a car");
                        try{
                            Thread.sleep(2000);

                        }catch (InterruptedException e){
                            logger.warning(e.getMessage());
                        }
                    }else if(content.getFirst() instanceof Bus){
                        try{
                            System.out.println("It's a bus");

                            Thread.sleep(content.getFirst().getPassengers().size()*100);
                        }catch (InterruptedException e){
                            logger.warning(e.getMessage());
                        }
                    }
                    List<Passenger> passengers = content.getFirst().getPassengers();
                    if(!passengers.get(0).process().getFirst()){
                        status.setSecond(false);
                        status.setFirst(true);
                        content.setSecond(false);
                        writeReport(content.getFirst(), null);
                    }else {
                        passengers = passengers.stream().filter(el-> {
                            if(!el.process().getFirst()){
                                writeReport(content.getFirst(), el);
                            }
                            return el.process().getSecond();
                        }).toList();

                        //System.out.println(" "+ content.getFirst());

                        content.getFirst().setPassengers(passengers);
                        status.setFirst(true);
                        content.setSecond(false);
                    }


                }

            }
        }

    }
}
