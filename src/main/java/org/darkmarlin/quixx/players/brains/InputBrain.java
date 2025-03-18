package org.darkmarlin.quixx.players.brains;

import org.darkmarlin.quixx.game.DiceRoll;
import org.darkmarlin.quixx.game.PlaySheet;
import org.darkmarlin.quixx.game.Turn;
import org.darkmarlin.quixx.players.PlayerBrain;

import java.util.Scanner;

public class InputBrain extends PlayerBrain {

    public InputBrain(String name) {
        super(name);
    }

    @Override
    public void weighSelfTurns(Turn[] turns, DiceRoll diceRoll, PlaySheet[] otherSheets, PlaySheet playSheet) {
        System.out.println("Current Board:");
        System.out.println();
        System.out.println("Own sheet: (" + name +")");
        System.out.println(playSheet);
        System.out.println();
        System.out.println("Other sheets:");
        for (PlaySheet otherSheet : otherSheets) {
            System.out.println(otherSheet);
            System.out.println();
        }
        System.out.println("DICE ROLL:");
        System.out.println(diceRoll);
        System.out.println("Please select one of the following options: ");
        for(int i = 0; i < turns.length; i++) {
            System.out.println(i + ": " + turns[i]);
        }
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        turns[choice].setWeight(100);
        System.out.println();
        System.out.println();
        System.out.println();
    }

    @Override
    public void weighOtherTurns(Turn[] turns, DiceRoll diceRoll, PlaySheet[] otherSheets, PlaySheet playSheet) {
        weighSelfTurns(turns, diceRoll, otherSheets, playSheet);
    }
}
