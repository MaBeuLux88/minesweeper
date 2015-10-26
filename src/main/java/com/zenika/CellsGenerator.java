package com.zenika;

import java.util.*;

public class CellsGenerator {

    public Map<Position, Cell> generate(int nbColumns, int nbLines, int nbMines) {

        if (nbMines > nbColumns * nbLines) {
            throw new TooMuchMinesException("You cannot have more Mines than Cells.");
        }

        Set<Integer> randomMines = generateRandomListOfNumbers(nbColumns * nbLines, nbMines);

        int mineCounter = 0;
        Map<Position, Cell> cells = new HashMap<>();
        for (int i = 1; i <= nbColumns; i++) {
            for (int j = 1; j <= nbLines; j++) {
                boolean isMine = randomMines.contains(mineCounter);
                cells.put(new Position(i, j), new Cell(isMine));
                mineCounter++;
            }
        }

        return cells;
    }

    private Set<Integer> generateRandomListOfNumbers(int max, int nbMines) {
        Set<Integer> randoms = new HashSet<>();
        Random rd = new Random();
        while (randoms.size() != nbMines)
            randoms.add(rd.nextInt(max));
        return randoms;
    }
}
