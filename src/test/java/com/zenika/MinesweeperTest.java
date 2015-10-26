package com.zenika;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class MinesweeperTest {

    private Grid generate3by3GridWith2MinesInCorner() {
        Map<Position, Cell> cellMap = new HashMap<>();
        cellMap.put(new Position(1, 1), new Cell(true));
        cellMap.put(new Position(1, 2), new Cell(true));
        cellMap.put(new Position(1, 3), new Cell(false));
        cellMap.put(new Position(2, 1), new Cell(false));
        cellMap.put(new Position(2, 2), new Cell(false));
        cellMap.put(new Position(2, 3), new Cell(false));
        cellMap.put(new Position(3, 1), new Cell(false));
        cellMap.put(new Position(3, 2), new Cell(false));
        cellMap.put(new Position(3, 3), new Cell(false));
        return new Grid(cellMap, 3, 3, 2);
    }

    private Grid generate3by3GridWith3MinesInAColumn() {
        Map<Position, Cell> cellMap = new HashMap<>();
        cellMap.put(new Position(1, 1), new Cell(true));
        cellMap.put(new Position(1, 2), new Cell(true));
        cellMap.put(new Position(1, 3), new Cell(true));
        cellMap.put(new Position(2, 1), new Cell(false));
        cellMap.put(new Position(2, 2), new Cell(false));
        cellMap.put(new Position(2, 3), new Cell(false));
        cellMap.put(new Position(3, 1), new Cell(false));
        cellMap.put(new Position(3, 2), new Cell(false));
        cellMap.put(new Position(3, 3), new Cell(false));
        return new Grid(cellMap, 3, 3, 3);
    }

    private Grid generateCellsMineClearMineClear() {
        Map<Position, Cell> cellMap = new HashMap<>();
        cellMap.put(new Position(1, 1), new Cell(true));
        cellMap.put(new Position(1, 2), new Cell(true));
        cellMap.put(new Position(2, 1), new Cell(false));
        cellMap.put(new Position(2, 2), new Cell(false));
        return new Grid(cellMap, 2, 2, 2);
    }

    private Grid generateCellsOneMineOneNormal() {
        Map<Position, Cell> cellMap = new HashMap<>();
        cellMap.put(new Position(1, 1), new Cell(true));
        cellMap.put(new Position(1, 2), new Cell(false));
        return new Grid(cellMap, 1, 2, 1);
    }

    private Grid generateCellsOneNormal() {
        Map<Position, Cell> cellMap = new HashMap<>();
        cellMap.put(new Position(1, 1), new Cell(false));
        return new Grid(cellMap, 1, 1, 0);
    }

    @Test
    public void shouldBeLostIfOneMineIsClicked() {
        Grid grid = generateCellsOneMineOneNormal();
        MineSweeper ms = new MineSweeper(grid);
        GridState status = ms.play(new Position(1, 1));
        assertThat(status).isEqualTo(GridState.BURST);
    }

    @Test
    public void shouldBeNotClearUntilIWin() {
        Grid grid = generateCellsMineClearMineClear();
        MineSweeper ms = new MineSweeper(grid);
        GridState statusNotClear = ms.play(new Position(2, 1));
        assertThat(statusNotClear).isEqualTo(GridState.NOT_CLEAR);
        GridState statusClear = ms.play(new Position(2, 2));
        assertThat(statusClear).isEqualTo(GridState.CLEAR);
    }

    @Test
    public void shouldBeOnGoingIfOneMineLeft() {
        Grid grid = generateCellsMineClearMineClear();
        MineSweeper ms = new MineSweeper(grid);
        GridState status = ms.play(new Position(2, 2));
        assertThat(status).isEqualTo(GridState.NOT_CLEAR);
    }

    @Test(expectedExceptions = NoSuchPositionException.class)
    public void shouldNotBeAbleToClickOutsideTheCells() {
        Grid grid = generateCellsOneNormal();
        MineSweeper ms = new MineSweeper(grid);
        ms.play(new Position(1, 2));
    }

    @Test
    public void shouldUncoverCellsTouchingACellWithNoMinesAroundWhenIPlayOnOneOfThem() {
        Grid grid = generate3by3GridWith2MinesInCorner();
        MineSweeper ms = new MineSweeper(grid);
        GridState status = ms.play(new Position(3, 3));
        Assertions.assertThat(status).isEqualTo(GridState.NOT_CLEAR);
        status = ms.play(new Position(1, 3));
        Assertions.assertThat(status).isEqualTo(GridState.CLEAR);
    }

    @Test
    public void shouldWinIfNoMinesAndAllDiscovered() {
        Grid grid = generateCellsOneNormal();
        MineSweeper ms = new MineSweeper(grid);
        GridState status = ms.play(new Position(1, 1));
        assertThat(status).isEqualTo(GridState.CLEAR);
    }

    @Test
    public void shouldWinInstantlyWhenGridIs3by3WithOneLineOfMinesAndIClickOnOneEmptyLine() {
        Grid grid = generate3by3GridWith3MinesInAColumn();
        MineSweeper ms = new MineSweeper(grid);
        GridState status = ms.play(new Position(3, 3));
        Assertions.assertThat(status).isEqualTo(GridState.CLEAR);
    }

    @Test
    public void shouldCalculateCorrectlyNumberOfMinesAroundEachPosition() {
        Grid grid = generate3by3GridWith2MinesInCorner();
        Map<Position, Integer> map = grid.getMinesAroundPosition();
        assertThat(map).isNotNull();
        assertThat(map).isNotEmpty();
        assertThat(map).contains(entry(new Position(1, 1), 1));
        assertThat(map).contains(entry(new Position(1, 2), 1));
        assertThat(map).contains(entry(new Position(1, 3), 1));
        assertThat(map).contains(entry(new Position(2, 1), 2));
        assertThat(map).contains(entry(new Position(2, 2), 2));
        assertThat(map).contains(entry(new Position(2, 3), 1));
        assertThat(map).contains(entry(new Position(3, 1), 0));
        assertThat(map).contains(entry(new Position(3, 2), 0));
        assertThat(map).contains(entry(new Position(3, 3), 0));
    }

    @Test
    public void shouldPrintAGridCorrectly() {
        Grid grid = generate3by3GridWith2MinesInCorner();
        grid.uncoverCell(new Position(3, 3));
        String s = grid.toString();
        assertThat(s).isEqualTo("   123\n  _____\n 1| 20|\n 2| 20|\n 3| 10|\n  -----");
    }

}
