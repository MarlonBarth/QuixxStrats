package org.darkmarlin.quixx.game;


import java.util.Random;

public class DiceRoll {

    private final DiceNumber[] white;
    private final DiceNumber red;
    private final DiceNumber yellow;
    private final DiceNumber green;
    private final DiceNumber blue;

    private DiceRoll(DiceNumber white1, DiceNumber white2, DiceNumber red, DiceNumber yellow, DiceNumber green, DiceNumber blue) {
        this.white = new DiceNumber[]{white1,white2};
        this.red = red;
        this.yellow = yellow;
        this.green = green;
        this.blue = blue;
    }

    private DiceRoll(DiceNumber[] rolls) {
        this.white = new DiceNumber[]{rolls[0],rolls[1]};
        this.red = rolls[2];
        this.yellow = rolls[3];
        this.green = rolls[4];
        this.blue = rolls[5];
    }


    public static DiceRoll newRoll(){
        Random random = new Random();
        DiceNumber[] rolls = new DiceNumber[6];
        for(int i = 0; i < rolls.length; i++){
            rolls[i] = getRandomNumber();
        }
        return new DiceRoll(rolls);
    }

    private static DiceNumber getRandomNumber(){
        Random random = new Random();
        return DiceNumber.getNumber(random.nextInt(1,7));
    }

    public int getWhite(){
        return DiceNumber.getInteger(white[0]) + DiceNumber.getInteger(white[1]);
    }

    public int[] getRed(){
        int first = DiceNumber.getInteger(red) + DiceNumber.getInteger(white[0]);
        int second = DiceNumber.getInteger(red) + DiceNumber.getInteger(white[1]);
        return new int[]{Math.min(first, second), Math.max(first, second)};
    }

    public int[] getYellow(){
        int first = DiceNumber.getInteger(yellow) + DiceNumber.getInteger(white[0]);
        int second = DiceNumber.getInteger(yellow) + DiceNumber.getInteger(white[1]);
        return new int[]{Math.min(first, second), Math.max(first, second)};
    }

    public int[] getGreen(){
        int first = DiceNumber.getInteger(green) + DiceNumber.getInteger(white[0]);
        int second = DiceNumber.getInteger(green) + DiceNumber.getInteger(white[1]);
        return new int[]{Math.min(first, second), Math.max(first, second)};
    }

    public int[] getBlue(){
        int first = DiceNumber.getInteger(blue) + DiceNumber.getInteger(white[0]);
        int second = DiceNumber.getInteger(blue) + DiceNumber.getInteger(white[1]);
        return new int[]{Math.min(first, second), Math.max(first, second)};
    }


    @Override
    public String toString() {
        return "WHITE: " + DiceNumber.getInteger(white[0]) + ", " + DiceNumber.getInteger(white[1]) + "; RED: " + DiceNumber.getInteger(red) + "; YELLOW: " + DiceNumber.getInteger(yellow) + ", GREEN: " + DiceNumber.getInteger(green) + ", BLUE: " + DiceNumber.getInteger(blue);
    }

    public enum DiceNumber{
        DICE_1,DICE_2,DICE_3,DICE_4,DICE_5,DICE_6;

        private static DiceNumber getNumber(int number){
            return switch (number){
              case 1 -> DICE_1;
              case 2 -> DICE_2;
              case 3 -> DICE_3;
              case 4 -> DICE_4;
              case 5 -> DICE_5;
              case 6 -> DICE_6;
              default -> throw new IllegalArgumentException("Invalid number: " + number);
            };
        }
        public static int getInteger(DiceNumber number){
            return switch (number){
                case DICE_1 -> 1;
                case DICE_2 -> 2;
                case DICE_3 -> 3;
                case DICE_4 -> 4;
                case DICE_5 -> 5;
                case DICE_6 -> 6;
                default -> throw new IllegalArgumentException("Invalid number: " + number);
            };
        }
    }
}
