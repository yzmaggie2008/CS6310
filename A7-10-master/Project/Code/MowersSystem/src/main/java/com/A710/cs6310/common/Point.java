package com.a710.cs6310.common;

public class Point {
    private final int _x;
    private final int _y;

    public Point(int x, int y) {
        this._x = x;
        this._y = y;
    }

    public int getPosX() {
        return _x;
    }

    public int getPosY() {
        return _y;
    }

    public boolean equals(Object other) {
        return _x == ((Point) other)._x && _y == ((Point) other)._y;
    }

    public String toString() {
        return _x + " + " + _y;
    }
}
