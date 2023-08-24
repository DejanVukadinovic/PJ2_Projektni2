package org.unibl.etf.dvukadinovic.passengers;

import org.unibl.etf.dvukadinovic.util.Pair;

public class BusPassenger extends Passenger{
    private final Bag bag = new Bag();

    public Pair<Boolean, Boolean> process(){
        Pair<Boolean, Boolean> result = super.process();
        result.setSecond(bag.process());
        return result;
    }
}
