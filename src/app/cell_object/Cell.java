package app.cell_object;

import app.contracts.ICell;

public class Cell implements ICell {

    private int x;
    private int y;
    private boolean isVisited;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.isVisited = false;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public boolean isVisited() {
        return isVisited;
    }

    @Override
    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    @Override
    public String toString() {
        return "[" + this.x + "," + this.y + "]";
    }
}
