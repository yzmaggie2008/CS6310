package com.a710.cs6310.model.form;

public class SystemStatus {
    private int turn;
    private String GameState;
    private String Body;
    private String message;
    private String[][] currentLawn;
    private int[][] digitLawn; // use this first before develop parser
    private int numOfMower;
    private int numOfGopher;
    private int maxTurn;
    private int countAliveMower;
    private int countMowGrass;
    private int totalGrass;
    private String summaryInfo;

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public String getGameState() {
        return GameState;
    }

    public void setGameState(String gameState) {
        GameState = gameState;
    }

    public String getBody() {
        return Body;
    }

    public void setBody(String body) {
        Body = body;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String[][] getCurrentLawn() {
        return currentLawn;
    }

    public void setCurrentLawn(String[][] currentLawn) {
        this.currentLawn = currentLawn;
    }

    public int getNumOfMower() {
        return numOfMower;
    }

    public void setNumOfMower(int numOfMower) {
        this.numOfMower = numOfMower;
    }

    public int getNumOfGopher() {
        return numOfGopher;
    }

    public void setNumOfGopher(int numOfGopher) {
        this.numOfGopher = numOfGopher;
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

    public String getSummaryInfo() {
        return summaryInfo;
    }

    public void setSummaryInfo(String summaryInfo) {
        this.summaryInfo = summaryInfo;
    }

    public int[][] getDigitLawn() {
        return digitLawn;
    }

    public void setDigitLawn(int[][] digitLawn) {
        this.digitLawn = digitLawn;
    }


}
