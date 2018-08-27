package app.contracts;

public interface ICell {
    int getX();

    int getY();

    boolean isVisited();

    void setVisited(boolean visited);
}
