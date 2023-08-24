package org.unibl.etf.dvukadinovic.passengers;

import java.io.Serializable;
import java.util.Random;

public class Bag implements Serializable {
    private boolean is_allowed = true;
    public Bag(){
        if(Math.random()<=0.1) is_allowed = false;
    }
    public boolean process(){
        return is_allowed;
    }
}
