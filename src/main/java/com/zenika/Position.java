package com.zenika;

public final class Position {

    private final int column;
    private final int line;

    public Position(int column, int line) {
        this.column = column;
        this.line = line;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Position position = (Position) o;
        return column == position.column && line == position.line;
    }

    public int getColumn() {
        return column;
    }

    public int getLine() {
        return line;
    }

    @Override
    public int hashCode() {
        int result = column;
        result = 31 * result + line;
        return result;
    }

}
