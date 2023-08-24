package org.unibl.etf.dvukadinovic.map;

import org.unibl.etf.dvukadinovic.util.Pair;
import org.unibl.etf.dvukadinovic.vehicle.Vehicle;

public class Field {
    protected Pair<Vehicle, Boolean> content = new Pair<>(null, false);
    protected Pair<Boolean, Boolean> status = new Pair<>(true, true);

    public Boolean isAvaliable(Vehicle v){
        return !content.getSecond();
    }
    public Pair<Boolean, Boolean> getStatus(){
        return status;
    }
    public void setContent(Vehicle content){
        synchronized (this.content){
            this.content.setFirst(content);
            this.content.setSecond(content!=null);
        }
        //System.out.println("setting");
    }
    public Vehicle getContent(){
        return content.getFirst();
    }
}
