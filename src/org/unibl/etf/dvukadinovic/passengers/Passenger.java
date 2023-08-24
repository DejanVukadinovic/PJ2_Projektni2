package org.unibl.etf.dvukadinovic.passengers;

import org.unibl.etf.dvukadinovic.util.Pair;

import java.io.Serializable;

public class Passenger implements Serializable {
    private final Documents docs = new Documents();
    public Pair<Boolean, Boolean> process(){
        return new Pair<>(docs.process(), true);
    }

}
