package org.unibl.etf.dvukadinovic.map;

import org.unibl.etf.dvukadinovic.util.Pair;
import org.unibl.etf.dvukadinovic.vehicle.RoadMap;
import org.unibl.etf.dvukadinovic.vehicle.Vehicle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class Map implements RoadMap {
    private Pair<Integer, Integer> size = new Pair<>(50, 3);
    private List<List<Field>> map = Collections.synchronizedList(new ArrayList<>());
    public Map(){
        initMap();
    }
    private void initMap(){
        map.add(Collections.synchronizedList(Arrays.asList(null , new CustomsField(), new TruckCustomsField())));
        map.add(Collections.synchronizedList(Arrays.asList(new BorderField(), new BorderField(), new TruckBorderField())));
        initRegularFields();
    }
    private void initRegularFields(){
        while(map.size()<size.getFirst()){
            List<Field> flist = Collections.synchronizedList(new ArrayList<>());
            flist.add(null);
            for (int i = 1; i < size.getSecond()-1; i++) {
                flist.add(new Field());
            }
            flist.add(null);
            map.add(flist);
        }
    }

    public boolean getAvailability(Pair<Integer, Integer> coords, Vehicle v){
        if (coords.getFirst()==-1) return true;
        if(map.get(coords.getFirst()).get(coords.getSecond())==null) return false;
        return map.get(coords.getFirst()).get(coords.getSecond()).isAvaliable(v);
    }
    public Pair<Boolean, Boolean> getStatus(Pair<Integer, Integer> coords){
        return map.get(coords.getFirst()).get(coords.getSecond()).getStatus();
    }
    public Pair<Integer, Integer> getSize(){
        return this.size;
    }
    public void setContent(Pair<Integer, Integer> coords, Vehicle content){
        if (coords.getFirst()==-1) return;
        map.get(coords.getFirst()).get(coords.getSecond()).setContent(content);
    }

    public List<List<Field>> getMap(){
        return this.map;
    }
    public Field getField(int i, int j){return this.map.get(i).get(j);}
}
