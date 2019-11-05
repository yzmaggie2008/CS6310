package com.a710.cs6310.model;

import com.a710.cs6310.common.Direction;

// add the id to locate the mower by system
// use string[] as surroundings to store the scan results which is easier for direction picking in OptimizeStrategy
public class Mower {
    private int id;
    private Direction dir;
    private boolean isCrash;
    private int StrategyId;
    private String[] surroundings;


    public Mower() {
        super();
    }

    public Mower(int id, Direction dir, boolean isCrash, int strategyId, String[] surroundings) {
        super();
        this.id = id;
        this.dir = dir;
        this.isCrash = isCrash;
        StrategyId = strategyId;
        this.surroundings = surroundings;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Direction getDir() {
        return dir;
    }

    public void setDir(Direction dir) {
        this.dir = dir;
    }

    public boolean isCrash() {
        return isCrash;
    }

    public void setCrash(boolean isCrash) {
        this.isCrash = isCrash;
    }


    public int getStrategyId() {
        return StrategyId;
    }

    public void setStrategyId(int strategyId) {
        StrategyId = strategyId;
    }

    public String[] getSurroundings() {
        return surroundings;
    }

    public void setSurroundings(String[] surroundings) {
        this.surroundings = surroundings;
    }
}
