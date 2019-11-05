package com.a710.cs6310.model;

public class Gopher {
    private int id;
    private int gopherTurn;

    public Gopher() {
    }

    public Gopher(int id, int gopherTurn) {
        this.id = id;
        this.gopherTurn = gopherTurn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGopherTurn() {
        return gopherTurn;
    }

    public void setGopherTurn(int gopherTurn) {
        this.gopherTurn = gopherTurn;
    }
}
