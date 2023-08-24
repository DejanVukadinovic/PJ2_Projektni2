package org.unibl.etf.dvukadinovic.vehicle;

import org.unibl.etf.dvukadinovic.util.Pair;

public interface RoadMap {
    public boolean getAvailability(Pair<Integer, Integer> coords,  Vehicle vehicle);
    public Pair<Boolean, Boolean> getStatus(Pair<Integer, Integer> coords);
    public Pair<Integer, Integer> getSize();
    public void setContent(Pair<Integer, Integer> coords, Vehicle content);
}
