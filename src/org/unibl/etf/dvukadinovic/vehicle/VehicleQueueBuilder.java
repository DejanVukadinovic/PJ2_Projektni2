package org.unibl.etf.dvukadinovic.vehicle;

import org.unibl.etf.dvukadinovic.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class VehicleQueueBuilder {
    public static List<Vehicle> buildQueue(RoadMap map, int cars, int buses, int trucks, Pair<Boolean, Boolean> simMode){
        List<Vehicle> res = new ArrayList<>();
        for (int i = 0; i < cars; i++) {
            res.add(new Car(map, simMode));
        }
        for (int i = 0; i < buses; i++) {
            res.add(new Bus(map, simMode));
        }
        for (int i = 0; i < trucks; i++) {
            res.add(new Truck(map, simMode));
        }
        Collections.shuffle(res);
        return res;
    }
}
