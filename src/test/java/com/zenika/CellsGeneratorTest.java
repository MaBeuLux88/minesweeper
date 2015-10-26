package com.zenika;

import org.testng.annotations.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CellsGeneratorTest {

    private CellsGenerator cellsGenerator = new CellsGenerator();

    @Test
    public void aOneByOneGridWithOneMineShouldGenerateOneCellMined() {
        Map<Position, Cell> map = cellsGenerator.generate(1, 1, 1);
        assertThat(map).isNotNull();
        assertThat(map.size()).isEqualTo(1);

        Cell cell = map.get(new Position(1, 1));
        assertThat(cell).isNotNull();
        assertThat(cell.isMine()).isTrue();
    }

    @Test
    public void aOneByTwoGridShouldGeneratorTwoCells() {
        Map<Position, Cell> map = cellsGenerator.generate(1, 2, 1);
        assertThat(map).isNotNull();
        assertThat(map.size()).isEqualTo(2);

        Cell cell = map.get(new Position(1, 1));
        assertThat(cell).isNotNull();

        cell = map.get(new Position(1, 2));
        assertThat(cell).isNotNull();
    }

    @Test
    public void aTenByTenGridWithTwentyFourMinesShouldGenerateOneHundredCellsWithTwentyFourMines() {
        Map<Position, Cell> map = cellsGenerator.generate(10, 10, 24);
        assertThat(map).isNotNull();
        assertThat(map.size()).isEqualTo(100);

        final Integer[] nbMines = {0};
        map.forEach((pos, cell) -> {
            if (cell.isMine()) {
                nbMines[0]++;
            }
        });
        assertThat(nbMines[0]).isEqualTo(24);
    }

    @Test
    public void aTenByTenGridWithOneHundredMinesShouldGenerateOneHundredMines() {
        Map<Position, Cell> map = cellsGenerator.generate(10, 10, 100);
        assertThat(map).isNotNull();
        assertThat(map.size()).isEqualTo(100);

        final Integer[] nbMines = {0};
        map.forEach((pos, cell) -> {
            if (cell.isMine()) {
                nbMines[0]++;
            }
        });
        assertThat(nbMines[0]).isEqualTo(100);
    }

}
