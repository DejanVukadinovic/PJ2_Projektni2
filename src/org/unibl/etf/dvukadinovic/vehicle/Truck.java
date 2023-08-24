package org.unibl.etf.dvukadinovic.vehicle;

import org.unibl.etf.dvukadinovic.util.Pair;

public class Truck extends Vehicle{

    private double declaredWeight;
    private double actualWeight;

    private boolean needsDocs;

    private TruckDocumentation docs = null;

    public Truck(RoadMap map, Pair<Boolean, Boolean> simMode){
        super(map, 3, simMode);
        declaredWeight = 10_000+Math.random()*8_000;
        actualWeight = declaredWeight;
        if(Math.random()<0.2){
            actualWeight*=1.3;
        }
        needsDocs = Math.random()<0.5;
    }
    public double getDeclaredWeight(){
        return declaredWeight;
    }
    public double getActualWeight(){
        return actualWeight;
    }
    public boolean doesNeedPapers(){
        return needsDocs;
    }
    public void generateDocumentation(){
        docs = new TruckDocumentation();
    }
    public String toString(){
        return "Truck id:"+this.id;
    }
}
