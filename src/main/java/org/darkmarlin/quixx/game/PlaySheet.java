package org.darkmarlin.quixx.game;

import java.util.Arrays;

public class PlaySheet {
    final private boolean[] red;
    final private boolean[] yellow;
    final private boolean[] blue;
    final private boolean[] green;

    private int furthestRed = 1;
    private int furthestYellow = 1;
    private int furthestBlue = 13;
    private int furthestGreen = 13;

    private final GameMain game;

    private int[][] allowedMoves;

    private int noTurns = 0;

    public PlaySheet(GameMain gameMain) {
        this.red = new boolean[12];
        this.yellow = new boolean[12];
        this.blue = new boolean[12];
        this.green = new boolean[12];

        Arrays.fill(red, false);
        Arrays.fill(yellow, false);
        Arrays.fill(blue, false);
        Arrays.fill(green, false);

        game = gameMain;

        allowedMoves = determineAllowedCrosses();
    }


    public void makeTurn(Turn turn){
        if(turn.isNoTurn()){
            if(turn.isSelfTurn()) {
                noTurns++;
                if (noTurns == 5) game.fiveNoTurns();
            }
            return;
        }
        if(turn.isDoubleTurn()){
            setCross(turn.getPlayRow(), turn.getPlayNumber());
            setCross(turn.getSecondPlayRow(), turn.getSecondPlayNumber());
        }else{
            setCross(turn.getPlayRow(),turn.getPlayNumber());
        }
        update();
    }

    private void setCross(Rows row, int number){
        boolean found = false;
        for(int i: allowedMoves[row.getNumber()]){
            if(number == i){found = true; break;}
        }
        if(!found){throw new IllegalArgumentException("This move is not allowed: " + row + ":" + number);}
        switch(row){
            case RED -> {
                red[number - 2] = true;
                furthestRed = number;
            }
            case YELLOW -> {
                yellow[number] = true;
                furthestYellow = number;
            }
            case BLUE -> {
                blue[number] = true;
                furthestBlue = number;
            }
            case GREEN -> {
                green[number] = true;
                furthestGreen = number;
            }
        }
        if(number == 12) {
            if(game.lock(row)){
                switch(row) {
                    case RED -> red[11] = true;
                    case YELLOW -> yellow[11] = true;
                    case BLUE -> blue[11] = true;
                    case GREEN -> green[11] = true;
                }
            }
        }
    }

    public int[][] getAllowedMoves() {
        return allowedMoves;
    }

    private void update(){
        allowedMoves = determineAllowedCrosses();
    }

    private int[][] determineAllowedCrosses(){
        int[][] ret = new int[4][];
        boolean lastAllowed;
        int counter;
        //RED
        if(!game.isRedLock()) {
            lastAllowed = getCrossesInRow(red) >= 5;
            int[] redAllowedCrosses;
            if (lastAllowed) {
                redAllowedCrosses = new int[12 - furthestRed];
                redAllowedCrosses[redAllowedCrosses.length - 1] = 12;
            } else {
                redAllowedCrosses = new int[12 - furthestRed - 1];
            }
            counter = 0;
            for (int i = furthestRed + 1; i <= 11; i++) {
                redAllowedCrosses[counter++] = i;
            }
            ret[0] = redAllowedCrosses;
        }else{
            ret[0] = new int[0];
        }

        //YELLOW
        if(!game.isYellowLock()) {
            int[] yellowAllowedCrosses;
            lastAllowed = getCrossesInRow(yellow) >= 5;
            if (lastAllowed) {
                yellowAllowedCrosses = new int[12 - furthestYellow];
                yellowAllowedCrosses[yellowAllowedCrosses.length - 1] = 12;
            } else {
                yellowAllowedCrosses = new int[12 - furthestYellow - 1];
            }
            counter = 0;
            for (int i = furthestYellow + 1; i <= 11; i++) {
                yellowAllowedCrosses[counter++] = i;
            }
            ret[1] = yellowAllowedCrosses;
        }else {
            ret[1] = new int[0];
        }

        //BLUE
        if(!game.isBlueLock()) {
            int[] blueAllowedCrosses;
            lastAllowed = getCrossesInRow(blue) >= 5;
            if (lastAllowed) {
                blueAllowedCrosses = new int[furthestBlue - 2];
                blueAllowedCrosses[blueAllowedCrosses.length - 1] = 2;
            } else {
                blueAllowedCrosses = new int[furthestBlue - 3];
            }
            counter = 0;
            for (int i = furthestBlue - 1; i > 2; i--) {
                blueAllowedCrosses[counter++] = i;
            }
            ret[2] = blueAllowedCrosses;
        }else {
            ret[2] = new int[0];
        }


        //GREEN
        if(!game.isGreenLock()) {
            int[] greenAllowedCrosses;
            lastAllowed = getCrossesInRow(green) >= 5;
            if (lastAllowed) {
                greenAllowedCrosses = new int[furthestGreen - 2];
                greenAllowedCrosses[greenAllowedCrosses.length - 1] = 2;
            } else {
                greenAllowedCrosses = new int[furthestGreen - 3];
            }
            counter = 0;
            for (int i = furthestGreen - 1; i > 2; i--) {
                greenAllowedCrosses[counter++] = i;
            }
            ret[3] = greenAllowedCrosses;
        }else{
            ret[3] = new int[0];
        }

        //RETURN
        return ret;
    }

    public int getPoints(){
        int points = 0;
        points += getPointsForCrosses(getCrossesInRow(red));
        points += getPointsForCrosses(getCrossesInRow(yellow));
        points += getPointsForCrosses(getCrossesInRow(blue));
        points += getPointsForCrosses(getCrossesInRow(green));
        points -= 5*noTurns;
        return points;
    }

    private int getCrossesInRow(boolean[] row){
        int count = 0;
        for (boolean b : row) {
            if (b) count++;
        }
        return count;
    }
    private int getPointsForCrosses(int crosses){
        return switch (crosses){
            case 0 -> 0;
            case 1 -> 1;
            case 2 -> 3;
            case 3 -> 6;
            case 4 -> 10;
            case 5 -> 15;
            case 6 -> 21;
            case 7 -> 28;
            case 8 -> 36;
            case 9 -> 45;
            case 10 -> 55;
            case 11 -> 66;
            case 12 -> 78;
            default -> throw new IllegalStateException("Unexpected value: " + crosses);
        };
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(int[] row : allowedMoves){
            for(int i : row){
                builder.append(i).append(" ");
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    public boolean[] getRed() {
        return red;
    }

    public boolean[] getYellow() {
        return yellow;
    }

    public boolean[] getBlue() {
        return blue;
    }

    public boolean[] getGreen() {
        return green;
    }

    public int getFurthestRed() {
        return furthestRed;
    }

    public int getFurthestYellow() {
        return furthestYellow;
    }

    public int getFurthestBlue() {
        return furthestBlue;
    }

    public int getFurthestGreen() {
        return furthestGreen;
    }

    public enum Rows{
        RED(0), YELLOW(1), BLUE(2), GREEN(4);
        private Rows(int number){
            this.number = number;
        }

        private final int number;
        public int getNumber() {
            return number;
        }
        public static Rows getRow(int number){
            return switch (number){
                case 0 -> RED;
                case 1 -> YELLOW;
                case 2 -> BLUE;
                case 3 -> GREEN;
                default -> null;
            };
        }
    }
}
