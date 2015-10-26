package com.zenika;

public class Cell {

    private boolean isMine;
    private boolean isDiscovered = false;

    public Cell(boolean isMine) {
        this.isMine = isMine;
    }

    public boolean isDiscovered() {
        return isDiscovered;
    }

    public boolean isMine() {
        return isMine;
    }

    public CellState uncover() {
        if (this.isDiscovered)
            return CellState.ALREADY_UNCOVERED;
        else {
            this.isDiscovered = true;
            if (this.isMine)
                return CellState.MINE_EXPLODED;
            else
                return CellState.UNCOVERED;
        }
    }

}
