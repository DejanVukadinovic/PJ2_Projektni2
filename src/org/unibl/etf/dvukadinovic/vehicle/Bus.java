package org.unibl.etf.dvukadinovic.vehicle;

import org.unibl.etf.dvukadinovic.passengers.BusPassenger;
import org.unibl.etf.dvukadinovic.util.Pair;

public class Bus extends Vehicle{
    public Bus(RoadMap map, Pair<Boolean, Boolean> simMode){
        super(map, simMode);
        for (int i = 0; i < 1 + (int) (Math.random() * 52); i++) {
            passengers.add(new BusPassenger());
        }
    }
    public String toString(){
        return "Bus id:"+this.id;
    }
}
