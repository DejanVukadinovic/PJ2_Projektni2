package org.unibl.etf.dvukadinovic.report;

import org.unibl.etf.dvukadinovic.passengers.Passenger;
import org.unibl.etf.dvukadinovic.vehicle.Vehicle;

import java.io.Serializable;

public class Report implements Serializable {
    private Vehicle vehicle;
    private Passenger passenger;
    private Boolean fatal = false;

    Report(Vehicle v, Passenger p){
        this.vehicle = v;
        this.passenger = p;
    }
    Report(Vehicle v, Passenger p, boolean fatal){
        this.vehicle = v;
        this.passenger = p;
        this.fatal = fatal;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    @Override
    public String toString() {
        if(passenger==null){
            return vehicle+" Fatal!\n";
        }
        return vehicle + " " + passenger;
    }
}
