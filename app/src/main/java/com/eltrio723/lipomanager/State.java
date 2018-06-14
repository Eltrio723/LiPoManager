package com.eltrio723.lipomanager;

public enum State {
    CHARGED ("Charged"),
    CHARGING ("Charging"),
    USED ("Used"),
    IN_USE ("In use"),
    NEW ("New"),
    STORED ("Stored"),
    DEPLETED ("Depleted");

    private final String name;

    private State(String s){
        name = s;
    }

    public boolean equalsName(String otherName){
        return name.equals(otherName);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
