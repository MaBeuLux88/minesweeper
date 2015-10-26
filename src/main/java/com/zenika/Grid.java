package com.zenika;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Grid {

    private Map<Position, Cell> cells;
    private int nbCellsToUncoverToWin;
    private int nbColumns;
    private int nbLines;
    private Map<Position, Integer> minesAroundPosition;

    public Grid(Map<Position, Cell> cells, int nbColumns, int nbLines, int nbMines) {
        if (cells == null)
            throw new IllegalArgumentException("Grid should not be null.");
        this.cells = cells;
        this.nbCellsToUncoverToWin = nbColumns * nbLines - nbMines;
        this.nbLines = nbLines;
        this.nbColumns = nbColumns;
        initialiseMinesAround();
    }

    private Cell getCell(Position position) {
        return cells.get(position);
    }

    public Map<Position, Integer> getMinesAroundPosition() {
        return minesAroundPosition;
    }

    private Set<Position> getNeighbours(Position position) {
        Set<Position> neighbours = new HashSet<>();
        Set<Position> existingPositions = cells.keySet();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                neighbours.add(new Position(position.getColumn() + i, position.getLine() + j));
            }
        }
        neighbours.remove(position);
        neighbours.retainAll(existingPositions);
        return neighbours;
    }

    private void initialiseMinesAround() {
        minesAroundPosition = new HashMap<>();
        cells.forEach((position, cell) -> {
            int numberMinesAround = 0;
            Set<Position> neighbours = getNeighbours(position);
            for (Position neighbour : neighbours) {
                if (cells.get(neighbour).isMine())
                    numberMinesAround++;
            }
            minesAroundPosition.put(position, numberMinesAround);
        });
    }

    /**
     * Horrible toString to be able to display the Grid in the console...
     * It could be very simple but I wanted a better UX.
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("   ");
        for (int i = 1; i <= nbColumns; i++) {
            stringBuilder.append(i % 10);
        }
        stringBuilder.append("\n  ");
        for (int i = 0; i <= nbColumns + 1; i++) {
            stringBuilder.append("_");
        }
        stringBuilder.append("\n");

        for (int j = 1; j <= nbLines; j++) {
            Serializable numberLine = j < 10 ? " " + j : j;
            stringBuilder.append(numberLine).append("|");
            for (int i = 1; i <= nbColumns; i++) {
                Position pos = new Position(i, j);
                Cell cell = cells.get(pos);
                if (cell.isDiscovered())
                    if (cell.isMine())
                        stringBuilder.append("X");
                    else
                        stringBuilder.append(minesAroundPosition.get(pos).toString());
                else
                    stringBuilder.append(" ");
            }
            stringBuilder.append("|").append("\n");
        }
        stringBuilder.append("  ");
        for (int i = 0; i <= nbColumns + 1; i++) {
            stringBuilder.append("-");
        }
        return stringBuilder.toString();
    }

    public GridState uncoverCell(Position position) {
        try {
            GridState state = null;
            Cell cell = getCell(position);
            CellState cellState = cell.uncover();
            switch (cellState) {
                case MINE_EXPLODED:
                    state = GridState.BURST;
                    break;
                case UNCOVERED:
                    this.nbCellsToUncoverToWin--;
                    if (minesAroundPosition.get(position) == 0) {
                        Set<Position> neighbours = getNeighbours(position);
                        neighbours.forEach(this::uncoverCell);
                    }
                    state = this.nbCellsToUncoverToWin == 0 ? GridState.CLEAR : GridState.NOT_CLEAR;
                    break;
                case ALREADY_UNCOVERED:
                    state = GridState.NOT_CLEAR;
                    break;
            }
            return state;
        } catch (NullPointerException exception) {
            throw new NoSuchPositionException("This position doesn't exist.");
        }
    }
}
