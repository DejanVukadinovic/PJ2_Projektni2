package org.unibl.etf.dvukadinovic.passengers;

import java.io.Serializable;

public class Documents implements Serializable {
    private boolean is_allowed = true;
    public Documents(){
        if(Math.random()<=0.03) {
            is_allowed = false;
        }
    }
    public boolean process(){
        return is_allowed;
    }
}
