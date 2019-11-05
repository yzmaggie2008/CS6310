package com.a710.cs6310.common;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static java.lang.Math.abs;

public interface PosDirectUtil {
    HashMap<Direction, List<Integer>> NEIGHBOR_OFFSET = new HashMap<Direction, List<Integer>>() {{
        put(Direction.NORTH, Arrays.asList(0, 1));
        put(Direction.NORTHEAST, Arrays.asList(1, 1));
        put(Direction.EAST, Arrays.asList(1, 0));
        put(Direction.SOUTHEAST, Arrays.asList(1, -1));
        put(Direction.SOUTH, Arrays.asList(0, -1));
        put(Direction.SOUTHWEST, Arrays.asList(-1, -1));
        put(Direction.WEST, Arrays.asList(-1, 0));
        put(Direction.NORTHWEST, Arrays.asList(-1, 1));
    }};

    /*
     ** Get new position based on moving with the direction
     */
    static Point getMoveToPosition(Point curPos, Direction direction) {
//        SimpleLog.log(String.format("getMoveToPosition: %s, %s", curPos.toString(), direction));
        return new Point(curPos.getPosX() + NEIGHBOR_OFFSET.get(direction).get(0),
                curPos.getPosY() + NEIGHBOR_OFFSET.get(direction).get(1));
    }

    /*
     ** Get the direction from curPos to nextPos
     */
    static Direction calculateDirection(Point curPos, Point nextPos) {
        for (Direction direct : Direction.values()) {
            Point neighborPos = getMoveToPosition(curPos, direct);
            if (neighborPos.equals(nextPos)) {
                return direct;
            }
        }

        SimpleLog.log(String.format("Direction.UNKNOWN: %s, %s", curPos.toString(), nextPos.toString()));
        return null;
    }

    /*
     ** Check whether the move is valid or not based on the direction and position
     */
    static boolean validateMove(Point curPos, Point newPos, Direction curDirect) {
        return (abs(newPos.getPosX() - curPos.getPosX()) < 2)
                && (abs(newPos.getPosY() - curPos.getPosY()) < 2)
                && curDirect.equals(calculateDirection(curPos, newPos));
    }
}
