package app.movement_logic;

import app.cell_object.Cell;

import java.util.ArrayList;
import java.util.List;

public class PathCalculator {
    private static final int MATRIX_LENGTH = 3;

    private static List<Cell> coordinates = new ArrayList<>();
    private static List<Cell> coordinates2 = new ArrayList<>();

    private static Cell[][] matrix = new Cell[MATRIX_LENGTH][MATRIX_LENGTH];

    private static boolean hasFoundPath = false;

    private PathCalculator() {
    }

    private static void initializeMatrix() {
        for (int i = 0; i < MATRIX_LENGTH; i++) {
            for (int j = 0; j < MATRIX_LENGTH; j++) {
                matrix[i][j] = new Cell(i, j);
            }
        }
    }

    private static Cell makeCellPointToBeginning(int fromX, int fromY) {
        Cell from = new Cell(fromX, fromY);
        matrix[fromX][fromY] = from;
        return from;
    }

    public static List<Cell> calculatePath(int fromX, int fromY, int toX, int toY) {

        initializeMatrix();
        coordinates = new ArrayList<>();
        coordinates2 = new ArrayList<>();

        coordinates.add(new Cell(fromX, fromY));
        coordinates2.add(new Cell(fromX, fromY));

        if (fromX == toX && fromY == toY) {
            return coordinates;
        } else if ((fromX == 1 && fromY == 1) || (toX == 1 && toY == 1)) {
            return null;
        } else {
            Cell from = makeCellPointToBeginning(fromX, fromY);
            Cell to = new Cell(toX, toY);
            matrix[toX][toY] = to;

            if ((fromX * MATRIX_LENGTH + fromY) % 2 == 0) {
                clockwiseCalculation(from, to);
                hasFoundPath = false;
                from = makeCellPointToBeginning(fromX, fromY);
                counterclockwiseCalculation(from, to);
                hasFoundPath = false;
            } else {
                clockwiseCalculation(from, to);
                hasFoundPath = false;
                from = makeCellPointToBeginning(fromX, fromY);
                toTheRightCalculation(from, to);
                hasFoundPath = false;
            }
        }

        return coordinates.size() < coordinates2.size() ? coordinates : coordinates2;
    }

    private static void toTheRightCalculation(Cell from, Cell to) {
        if (from.getX() == to.getX() && from.getY() == to.getY()) {
            hasFoundPath = true;
            return;
        }

        int X, Y;

        X = from.getX() - 2;
        Y = from.getY() + 1;
        if (X >= 0 && Y <= 2 && !matrix[X][Y].isVisited() && !hasFoundPath) {
            from.setVisited(true);
            from = matrix[X][Y];
            coordinates2.add(new Cell(from.getX(), from.getY()));
            toTheRightCalculation(from, to);

        }
        X = from.getX() - 2;
        Y = from.getY() - 1;
        if (X >= 0 && Y >= 0 && !matrix[X][Y].isVisited() && !hasFoundPath) {
            from.setVisited(true);
            from = matrix[X][Y];
            coordinates2.add(new Cell(from.getX(), from.getY()));
            toTheRightCalculation(from, to);

        }

        X = from.getX() + 1;
        Y = from.getY() + 2;
        if (X <= 2 && Y <= 2 && !matrix[X][Y].isVisited() && !hasFoundPath) {
            from.setVisited(true);
            from = matrix[X][Y];
            coordinates2.add(new Cell(from.getX(), from.getY()));
            toTheRightCalculation(from, to);

        }
        X = from.getX() - 1;
        Y = from.getY() + 2;
        if (X >= 0 && Y <= 2 && !matrix[X][Y].isVisited() && !hasFoundPath) {
            from.setVisited(true);
            from = matrix[X][Y];
            coordinates2.add(new Cell(from.getX(), from.getY()));
            toTheRightCalculation(from, to);

        }

        X = from.getX() + 2;
        Y = from.getY() - 1;
        if (X <= 2 && Y >= 0 && !matrix[X][Y].isVisited() && !hasFoundPath) {
            from.setVisited(true);
            from = matrix[X][Y];
            coordinates2.add(new Cell(from.getX(), from.getY()));
            toTheRightCalculation(from, to);

        }
        X = from.getX() + 2;
        Y = from.getY() + 1;
        if (X <= 2 && Y <= 2 && !matrix[X][Y].isVisited() && !hasFoundPath) {
            from.setVisited(true);
            from = matrix[X][Y];
            coordinates2.add(new Cell(from.getX(), from.getY()));
            toTheRightCalculation(from, to);

        }

        X = from.getX() - 1;
        Y = from.getY() - 2;
        if (X >= 0 && Y >= 0 && !matrix[X][Y].isVisited() && !hasFoundPath) {
            from.setVisited(true);
            from = matrix[X][Y];
            coordinates2.add(new Cell(from.getX(), from.getY()));
            toTheRightCalculation(from, to);

        }
        X = from.getX() + 1;
        Y = from.getY() - 2;
        if (X <= 2 && Y >= 0 && !matrix[X][Y].isVisited() && !hasFoundPath) {
            from.setVisited(true);
            from = matrix[X][Y];
            coordinates2.add(new Cell(from.getX(), from.getY()));
            toTheRightCalculation(from, to);

        }
    }

    private static void counterclockwiseCalculation(Cell from, Cell to) {
        if (from.getX() == to.getX() && from.getY() == to.getY()) {
            hasFoundPath = true;
            return;
        }

        int X, Y;

        X = from.getX() - 2;
        Y = from.getY() - 1;
        if (X >= 0 && Y >= 0 && !matrix[X][Y].isVisited() && !hasFoundPath) {
            from.setVisited(true);
            from = matrix[X][Y];
            coordinates2.add(new Cell(from.getX(), from.getY()));
            counterclockwiseCalculation(from, to);

        }
        X = from.getX() - 2;
        Y = from.getY() + 1;
        if (X >= 0 && Y <= 2 && !matrix[X][Y].isVisited() && !hasFoundPath) {
            from.setVisited(true);
            from = matrix[X][Y];
            coordinates2.add(new Cell(from.getX(), from.getY()));
            counterclockwiseCalculation(from, to);

        }

        X = from.getX() + 1;
        Y = from.getY() - 2;
        if (X <= 2 && Y >= 0 && !matrix[X][Y].isVisited() && !hasFoundPath) {
            from.setVisited(true);
            from = matrix[X][Y];
            coordinates2.add(new Cell(from.getX(), from.getY()));
            counterclockwiseCalculation(from, to);

        }
        X = from.getX() - 1;
        Y = from.getY() - 2;
        if (X >= 0 && Y >= 0 && !matrix[X][Y].isVisited() && !hasFoundPath) {
            from.setVisited(true);
            from = matrix[X][Y];
            coordinates2.add(new Cell(from.getX(), from.getY()));
            counterclockwiseCalculation(from, to);

        }

        X = from.getX() + 2;
        Y = from.getY() + 1;
        if (X <= 2 && Y <= 2 && !matrix[X][Y].isVisited() && !hasFoundPath) {
            from.setVisited(true);
            from = matrix[X][Y];
            coordinates2.add(new Cell(from.getX(), from.getY()));
            counterclockwiseCalculation(from, to);

        }
        X = from.getX() + 2;
        Y = from.getY() - 1;
        if (X <= 2 && Y >= 0 && !matrix[X][Y].isVisited() && !hasFoundPath) {
            from.setVisited(true);
            from = matrix[X][Y];
            coordinates2.add(new Cell(from.getX(), from.getY()));
            counterclockwiseCalculation(from, to);

        }

        X = from.getX() - 1;
        Y = from.getY() + 2;
        if (X >= 0 && Y <= 2 && !matrix[X][Y].isVisited() && !hasFoundPath) {
            from.setVisited(true);
            from = matrix[X][Y];
            coordinates2.add(new Cell(from.getX(), from.getY()));
            counterclockwiseCalculation(from, to);

        }
        X = from.getX() + 1;
        Y = from.getY() + 2;
        if (X <= 2 && Y <= 2 && !matrix[X][Y].isVisited() && !hasFoundPath) {
            from.setVisited(true);
            from = matrix[X][Y];
            coordinates2.add(new Cell(from.getX(), from.getY()));
            counterclockwiseCalculation(from, to);

        }

    }

    private static void clockwiseCalculation(Cell from, Cell to) {
        if (from.getX() == to.getX() && from.getY() == to.getY()) {
            hasFoundPath = true;
            return;
        }

        int X, Y;

        X = from.getX() - 2;
        Y = from.getY() - 1;
        if (X >= 0 && Y >= 0 && !matrix[X][Y].isVisited() && !hasFoundPath) {
            from.setVisited(true);
            from = matrix[X][Y];
            coordinates.add(new Cell(from.getX(), from.getY()));
            clockwiseCalculation(from, to);

        }
        X = from.getX() - 2;
        Y = from.getY() + 1;
        if (X >= 0 && Y <= 2 && !matrix[X][Y].isVisited() && !hasFoundPath) {
            from.setVisited(true);
            from = matrix[X][Y];
            coordinates.add(new Cell(from.getX(), from.getY()));
            clockwiseCalculation(from, to);

        }

        X = from.getX() - 1;
        Y = from.getY() + 2;
        if (X >= 0 && Y <= 2 && !matrix[X][Y].isVisited() && !hasFoundPath) {
            from.setVisited(true);
            from = matrix[X][Y];
            coordinates.add(new Cell(from.getX(), from.getY()));
            clockwiseCalculation(from, to);

        }
        X = from.getX() + 1;
        Y = from.getY() + 2;
        if (X <= 2 && Y <= 2 && !matrix[X][Y].isVisited() && !hasFoundPath) {
            from.setVisited(true);
            from = matrix[X][Y];
            coordinates.add(new Cell(from.getX(), from.getY()));
            clockwiseCalculation(from, to);

        }

        X = from.getX() + 2;
        Y = from.getY() + 1;
        if (X <= 2 && Y <= 2 && !matrix[X][Y].isVisited() && !hasFoundPath) {
            from.setVisited(true);
            from = matrix[X][Y];
            coordinates.add(new Cell(from.getX(), from.getY()));
            clockwiseCalculation(from, to);

        }
        X = from.getX() + 2;
        Y = from.getY() - 1;
        if (X <= 2 && Y >= 0 && !matrix[X][Y].isVisited() && !hasFoundPath) {
            from.setVisited(true);
            from = matrix[X][Y];
            coordinates.add(new Cell(from.getX(), from.getY()));
            clockwiseCalculation(from, to);

        }

        X = from.getX() + 1;
        Y = from.getY() - 2;
        if (X <= 2 && Y >= 0 && !matrix[X][Y].isVisited() && !hasFoundPath) {
            from.setVisited(true);
            from = matrix[X][Y];
            coordinates.add(new Cell(from.getX(), from.getY()));
            clockwiseCalculation(from, to);

        }
        X = from.getX() - 1;
        Y = from.getY() - 2;
        if (X >= 0 && Y >= 0 && !matrix[X][Y].isVisited() && !hasFoundPath) {
            from.setVisited(true);
            from = matrix[X][Y];
            coordinates.add(new Cell(from.getX(), from.getY()));
            clockwiseCalculation(from, to);

        }
    }

}
