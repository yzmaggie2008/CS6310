package com.a710.cs6310.model;

import com.a710.cs6310.common.*;

import java.util.ArrayList;
import java.util.List;

public class Lawn {
    private Integer _width;
    private Integer _height;
    private Integer _grassTotal;
    private Integer _grassRemains;
    private Item[][] _grids;
    private List<Point> _mowerPos = new ArrayList<>();

    public Lawn(Item[][] grids) {
        _grids = grids;
        _height = grids.length;
        _width = grids[0].length;
        _grassTotal = _grassRemains = 0;
        init();
    }

    /*
     ** Calculation of grass and crater in initial state
     */
    public void init() {
        for (int y = 0; y < _height; y++) {
            for (int x = 0; x < _width; x++) {
                if (ItemType.GRASS.equals(_grids[y][x].getType())) {
                    _grassTotal++;
                    _grassRemains++;
                }
            }
        }
    }

    /*
     ** Place mower on this position
     */
    public void setMowerPos(Point pos) {
        EmptyLand land = new EmptyLand(pos);
        land.setMowerFlag(true);

        _grassTotal--;
        _grassRemains--;
        _grids[pos.getPosY()][pos.getPosX()] = land;
        _mowerPos.add(pos);
    }

    /*
     ** Place mower on this position
     */
    public void setGopherPos(Point pos) {
        _grids[pos.getPosY()][pos.getPosX()].setGopherFlag(true);
    }

    /*
     ** Update grid information when mower move out this grid
     */
    public void updateForMowerMove(int index, Point newPosInLawn) {
        Point oldPos = _mowerPos.get(index);
        getGrid(oldPos).setMowerFlag(false);
        _mowerPos.set(index, newPosInLawn);

        Item item;
        if (ItemType.EMPTY.equals(getGrid(newPosInLawn).getType())) {
            item = getGrid(newPosInLawn);
        } else {//grass -> empty land
            item = new EmptyLand(newPosInLawn);
            _grassRemains--;
        }

        item.setMowerFlag(true);
        _grids[newPosInLawn.getPosY()][newPosInLawn.getPosX()] = item;
    }

    /*
     ** Get total number of grass
     */
    public final Integer getGrassTotal() {
        return _grassTotal;
    }


    /*
     ** Get number of uncut grass
     */
    public final Integer getGrassRemains() {
        return _grassRemains;
    }

    /*
     ** Get the width of this lawn
     */
    public final Integer getWidth() {
        return _width;
    }

    /*
     ** Get the height of this lawn
     */
    public final Integer getHeight() {
        return _height;
    }

    /*
     ** Whether have any grass uncut
     */
    public final boolean hasGrass() {
        return _grassRemains > 0;
    }


    /*
     ** Get surrounding grids based on given position
     ** Make sure the scan result is a copy of surrounding current grids.
     */
    public final List<ScanResultType> scanForMower(Integer mowerIdx) {
        List<ScanResultType> result = new ArrayList<>();
        Point pos = _mowerPos.get(mowerIdx);

        for (Direction direction : Direction.values()) {
            int x = pos.getPosX() + PosDirectUtil.NEIGHBOR_OFFSET.get(direction).get(0);
            int y = pos.getPosY() + PosDirectUtil.NEIGHBOR_OFFSET.get(direction).get(1);

            if (x >= _width || x < 0 || y < 0 || y >= _height) {
                result.add(ScanResultType.FENCE);
            } else {
                result.add(_grids[y][x].toScanResult());
            }
        }
        return result;
    }

    /*
     ** Get all the grids of this lawn
     */
    public final Item getGrid(Point pos) {
        return _grids[pos.getPosY()][pos.getPosX()];
    }


    /*
     ** Check whether given position is valid for this lawn
     */
    public boolean isPosValid(Point pos) {
        int x = pos.getPosX();
        int y = pos.getPosY();
        return x >= 0 && x < _width && y >= 0 && y < _height;
    }

    /*
     ** Get mower position in this lawn based on mower index
     */
    public final Point getMowerPos(int index) {
        return _mowerPos.get(index);
    }
}
