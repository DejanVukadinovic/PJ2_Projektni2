package org.unibl.etf.dvukadinovic.vehicle;

import org.unibl.etf.dvukadinovic.passengers.Passenger;
import org.unibl.etf.dvukadinovic.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Car extends Vehicle {


    public Car(RoadMap map, Pair<Boolean, Boolean> simMode) {
        super(map, 5,  simMode);

    }
    public String toString(){
        return "Car id:"+this.id;
    }

}
