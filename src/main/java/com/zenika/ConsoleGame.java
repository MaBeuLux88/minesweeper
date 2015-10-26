package com.zenika;

import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class ConsoleGame {

    private Scanner keyboard;
    private PrintStream ps;

    public ConsoleGame(Scanner keyboard, PrintStream ps) {
        this.keyboard = keyboard;
        this.ps = ps;
    }

    private int promptAndReadInteger(String prompt, int min, int max) {
        this.ps.println(prompt + " [" + min + "," + max + "]");
        int value = 0;
        boolean tryAgain;

        do {
            try {
                value = keyboard.nextInt();
                if (value < min || value > max)
                    throw new InputMismatchException();
                tryAgain = false;
            } catch (InputMismatchException exception) {
                keyboard.nextLine();
                this.ps.println("Positive Integers only, please try again.");
                this.ps.println(prompt);
                tryAgain = true;
            }
        }
        while (tryAgain);

        return value;
    }

    public void startGame() {
        this.ps.println("###############");
        this.ps.println("# Minesweeper #");
        this.ps.println("###############");
        int nbColumns = promptAndReadInteger("Please enter the number of nbColumns", 2, 100);
        int nbLines = promptAndReadInteger("Please enter the number of nbLines", 2, 50);
        int nbMines = promptAndReadInteger("Please enter the number of nbMines", 1, nbColumns * nbLines - 1);

        CellsGenerator cg = new CellsGenerator();
        Map<Position, Cell> cells = cg.generate(nbColumns, nbLines, nbMines);

        Grid grid = new Grid(cells, nbColumns, nbLines, nbMines);
        MineSweeper ms = new MineSweeper(grid);

        GridState gridState;
        do {
            this.ps.println(grid.toString());
            int column = promptAndReadInteger("Playing column", 1, nbColumns);
            int line = promptAndReadInteger("Playing line", 1, nbLines);
            gridState = ms.play(new Position(column, line));
        }
        while (gridState == GridState.NOT_CLEAR);

        this.ps.println(grid.toString());
        if (gridState == GridState.BURST) {
            this.ps.println("###########################");
            this.ps.println("# You failed miserably... #");
            this.ps.println("###########################");
        } else if (gridState == GridState.CLEAR) {
            this.ps.println("####################");
            this.ps.println("# Epic WIN! GG WP! #");
            this.ps.println("####################");
        }
    }
}
