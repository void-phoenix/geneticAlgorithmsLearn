package genetic.robot;


import java.util.ArrayList;
import java.util.List;

public class Maze {

    private final int maze[][];
    private int startPosition[] = {-1, -1};

    public Maze(int[][] maze) {
        this.maze = maze;
    }

    public int[] getStartPosition() {
        if (startPosition[0] != -1 && startPosition[1] != -1) return startPosition;

        for (int rowIndex = 0; rowIndex < maze.length; rowIndex++) {
            for (int colIndex = 0; colIndex < maze[rowIndex].length; colIndex++) {
                if (maze[rowIndex][colIndex] == 2) {
                    startPosition = new int[]{rowIndex, colIndex};
                    return startPosition;
                }
            }
        }

        startPosition = new int[]{0, 0};
        return startPosition;
    }

    public int getPositionValue(int x, int y) {
        if (x < 0 || y < 0 || x >= maze.length || y >= maze[x].length) return 1;
        return maze[x][y];
    }

    public boolean isWall(int x, int y) {
        return getPositionValue(x, y) == 1;
    }

    public int getMaxX() {
        return maze[0].length - 1;
    }

    public int getMaxY() {
        return maze.length - 1;
    }

    public int scoreRoute(List<int[]> routes) {
        int score = 0;
        boolean visited[][] = new boolean[getMaxY() + 1][getMaxX() + 1];

        for (int[] step : routes) {
            if (maze[step[1]][step[0]] == 3 && !visited[step[1]][step[0]]){
                score++;
                visited[step[1]][step[0]] = true;
            }
        }
        return score;
    }

}
