package com.zenika;

public class MineSweeper {

    private Grid grid;

    public MineSweeper(Grid grid) {
        this.grid = grid;
    }

    public GridState play(Position position) {
        return grid.uncoverCell(position);
    }

}
