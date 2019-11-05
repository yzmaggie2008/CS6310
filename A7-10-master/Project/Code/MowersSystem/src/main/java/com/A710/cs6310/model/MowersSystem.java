package com.a710.cs6310.model;

import com.a710.cs6310.model.form.State;
import com.a710.cs6310.service.MovingStrategy;

import java.util.ArrayList;
import java.util.List;

//Singleton Design pattern. make sure there is only one object of mowersSystem created in application
// the moverService class is not created as it's a simple system, the methods are listed here to refer the relationship to mower class
// the service layer only include the implementation of moving strategy
// The behavior of mowersSystem will call the methods from surface movingStrategy
// pollMowerForAction,validateMowerAction,displayActionAndResponses,renderLawn will realize the use case  (functions):3,4,5,6,7,9

public class MowersSystem {
    private static final MowersSystem mowersSystem = new MowersSystem();


    private List<Mower> mowers;
    private int[][] lawnCurrent; // remove the Lawn class, use the two dimension array to present lawninfo directly
    private int[] mowerX;
    private int[] mowerY;
    private int maxTurn;
    private int countAliveMower;
    private int countMowGrass;
    private int totalGrass; // add this attribute for convenient to check whether all grass cut
    private int currentTurn;
    private List<State> states;

    private final int EMPTY_CODE = 0;
    private final int GRASS_CODE = 1;
    private final int CRATER_CODE = 2;

    //singleton
    private MowersSystem() {
        mowers = new ArrayList<Mower>();
        currentTurn = 0;
        states = new ArrayList<State>();
        states.add(new State(SystemState.uninitial.toString(), "", ""));
    }

    public List<State> getStates() {
        return states;
    }

    public void setStates(List<State> states) {
        this.states = states;
    }

    public static MowersSystem getInstance() {
        return mowersSystem;
    }


    public List<Mower> getMowers() {
        return mowers;
    }

    public void setMowers(List<Mower> mowers) {
        this.mowers = mowers;
    }

    public int[][] getLawnCurrent() {
        return lawnCurrent;
    }

    public void setLawnCurrent(int[][] lawnCurrent) {
        this.lawnCurrent = lawnCurrent;
    }

    public int getMaxTurn() {
        return maxTurn;
    }

    public void setMaxTurn(int maxTurn) {
        this.maxTurn = maxTurn;
    }

    public int getCountAliveMower() {
        return countAliveMower;
    }

    public void setCountAliveMower(int countAliveMower) {
        this.countAliveMower = countAliveMower;
    }

    public int getCountMowGrass() {
        return countMowGrass;
    }

    public void setCountMowGrass(int countMowGrass) {
        this.countMowGrass = countMowGrass;
    }

    public int getTotalGrass() {
        return totalGrass;
    }

    public void setTotalGrass(int totalGrass) {
        this.totalGrass = totalGrass;
    }


    public int[] getMowerX() {
        return mowerX;
    }

    public void setMowerX(int[] mowerX) {
        this.mowerX = mowerX;
    }

    public int[] getMowerY() {
        return mowerY;
    }

    public void setMowerY(int[] mowerY) {
        this.mowerY = mowerY;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(int currentTurn) {
        this.currentTurn = currentTurn;
    }

    public void finalReport(int completeTurns) {
        int lawnSize = lawnCurrent.length * lawnCurrent[0].length;
        int potentialCut = totalGrass;
        int actualCut = countMowGrass;
//        System.out.println("*****************************Final Report*********************************");
//        System.out.println("lawn size " + ", total grass " + ", actually cut" + ", complete turn");
        System.out.println(lawnSize + "," + potentialCut + "," + actualCut + "," + completeTurns);
//        System.out.println("alive mower: " + countAliveMower);
    }

    public void pollMowerForAction(MovingStrategy strategy, int id) {
        strategy.pollMowerForAction(id);
    }

    public void validateMowerAction(MovingStrategy strategy, int id) {
        strategy.validateMowerAction(id);
    }

    public void displayActionAndResponses(MovingStrategy strategy, int id) {
        strategy.displayActionAndResponses(id);
    }

    public void renderLawn(MovingStrategy strategy) {
        strategy.renderLawn();
    }

}
