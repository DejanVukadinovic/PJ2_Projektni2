package org.unibl.etf.dvukadinovic.util;

public class Pair<F,S> {
    private F first;
    private S second;

    public Pair(F f, S s){
        this.first = f;
        this.second = s;
    }
    public Pair(){}

    public F getFirst(){
        return first;
    }
    public void setFirst(F f){
        this.first = f;
    }
    public S getSecond(){
        return second;
    }
    public void setSecond(S s){
        this.second = s;
    }

}
