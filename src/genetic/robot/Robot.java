package genetic.robot;


import java.util.ArrayList;
import java.util.List;


public class Robot {
    private enum Direction {
        NORTH,
        EAST,
        SOUTH,
        WEST
    }

    ;
    private int xPosition;
    private int yPosition;
    private Direction heading;
    int maxMoves;
    int moves;
    private int sensorVal;
    private final int sensorActions[];
    private Maze maze;
    private List<int[]> route;

    public Robot(int[] sensorActions, Maze maze, int maxMoves) {
        this.sensorActions = calculateSensorActions(sensorActions);
        this.maze = maze;
        int startPos[] = maze.getStartPosition();
        xPosition = startPos[0];
        yPosition = startPos[1];
        sensorVal = -1;
        heading = Direction.EAST;
        this.maxMoves = maxMoves;
        moves = 0;
        route = new ArrayList<int[]>();
        route.add(startPos);
    }

    public void run() {
        while (true) {
            moves++;
            if (getNextAction() == 0) return;
            if (maze.getPositionValue(xPosition, yPosition) == 4) return;
            if (moves > maxMoves) return;
            makeNextAction();
        }
    }

    private int[] calculateSensorActions(int[] sensorActionsStr) {
        int numActions = sensorActionsStr.length / 2;
        int sensorActions[] = new int[numActions];

        for (int sensorValue = 0; sensorValue < numActions; sensorValue++) {
            int sensorAction = 0;
            if (sensorActionsStr[sensorValue * 2] == 1) sensorAction += 2;
            if (sensorActionsStr[sensorValue * 2 + 1] == 1) sensorAction += 1;
            sensorActions[sensorValue] = sensorAction;
        }
        return sensorActions;
    }

    public void makeNextAction() {
        if (getNextAction() == 1) {
            int currentX = xPosition;
            int currentY = yPosition;

            if (Direction.NORTH == heading && yPosition != 0) yPosition--;
            else if (Direction.EAST == heading && xPosition != maze.getMaxX()) xPosition++;
            else if (Direction.SOUTH == heading && yPosition != maze.getMaxY()) yPosition++;
            else if (Direction.WEST == heading && xPosition != 0) xPosition--;

            if (maze.isWall(xPosition, yPosition)) {
                xPosition = currentX;
                yPosition = currentY;
            } else {
                if (currentX != xPosition || currentY != yPosition) route.add(getPosition());
            }
        } else if (getNextAction() == 2) {
            if (Direction.NORTH == heading) heading = Direction.EAST;
            else if (Direction.EAST == heading) heading = Direction.SOUTH;
            else if (Direction.SOUTH == heading) heading = Direction.WEST;
            else if (Direction.WEST == heading) heading = Direction.NORTH;
        } else if (getNextAction() == 3) {
            if (Direction.NORTH == heading) heading = Direction.WEST;
            else if (Direction.EAST == heading) heading = Direction.NORTH;
            else if (Direction.SOUTH == heading) heading = Direction.EAST;
            else if (Direction.WEST == heading) heading = Direction.SOUTH;
        }
        sensorVal = -1;
    }

    public int getNextAction() {
        return sensorActions[getSensorValue()];
    }

    public int getSensorValue() {
        if (sensorVal > -1) return sensorVal;
        boolean frontSensor = false;
        boolean frontLeftSensor = false;
        boolean fronRightSensor = false;
        boolean leftSensor = false;
        boolean rightSensor = false;
        boolean backSensor = false;

        if (heading == Direction.NORTH) {
            frontSensor = maze.isWall(xPosition, yPosition - 1);
            frontLeftSensor = maze.isWall(xPosition - 1, yPosition - 1);
            fronRightSensor = maze.isWall(xPosition + 1, yPosition - 1);
            leftSensor = maze.isWall(xPosition - 1, yPosition);
            rightSensor = maze.isWall(xPosition + 1, yPosition);
            backSensor = maze.isWall(xPosition, yPosition + 1);
        } else if (heading == Direction.EAST) {
            frontSensor = maze.isWall(xPosition + 1, yPosition);
            frontLeftSensor = maze.isWall(xPosition + 1, yPosition - 1);
            fronRightSensor = maze.isWall(xPosition + 1, yPosition + 1);
            leftSensor = maze.isWall(xPosition, yPosition - 1);
            rightSensor = maze.isWall(xPosition, yPosition + 1);
            backSensor = maze.isWall(xPosition - 1, yPosition);
        } else if (heading == Direction.SOUTH) {
            frontSensor = maze.isWall(xPosition, yPosition + 1);
            frontLeftSensor = maze.isWall(xPosition + 1, yPosition + 1);
            fronRightSensor = maze.isWall(xPosition - 1, yPosition + 1);
            leftSensor = maze.isWall(xPosition + 1, yPosition);
            rightSensor = maze.isWall(xPosition - 1, yPosition);
            backSensor = maze.isWall(xPosition, yPosition - 1);
        } else if (heading == Direction.WEST) {
            frontSensor = maze.isWall(xPosition - 1, yPosition);
            frontLeftSensor = maze.isWall(xPosition - 1, yPosition + 1);
            fronRightSensor = maze.isWall(xPosition - 1, yPosition - 1);
            leftSensor = maze.isWall(xPosition, yPosition + 1);
            rightSensor = maze.isWall(xPosition, yPosition - 1);
            backSensor = maze.isWall(xPosition + 1, yPosition);
        }
        int sensorVal = 0;
        if (frontSensor) sensorVal++;
        if (frontLeftSensor) sensorVal += 2;
        if (fronRightSensor) sensorVal += 4;
        if (leftSensor) sensorVal += 8;
        if (rightSensor) sensorVal += 16;
        if (backSensor) sensorVal += 32;

        this.sensorVal = sensorVal;

        return sensorVal;
    }

    public int[] getPosition() {
        return new int[]{xPosition, yPosition};
    }

    public List<int[]> getRoute(){
        return route;
    }

    public String printRoute() {
        StringBuilder result = new StringBuilder();

        for (int[] routeStep : route) {
            result.append("{ ").append(routeStep[0]).append(",").append(routeStep[1]).append("} ");
        }
        return result.toString();
    }
}
