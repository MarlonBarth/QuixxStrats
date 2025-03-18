package org.darkmarlin.quixx.players.brains;

import org.darkmarlin.quixx.game.DiceRoll;
import org.darkmarlin.quixx.game.PlaySheet;
import org.darkmarlin.quixx.game.Turn;
import org.darkmarlin.quixx.players.Player;
import org.darkmarlin.quixx.players.PlayerBrain;

import java.util.Random;

public class RandomPlayer extends PlayerBrain{

    Random rand = new Random();

    public RandomPlayer(String name) {
        super(name);
    }

    @Override
    public void weighSelfTurns(Turn[] turns, DiceRoll diceRoll, PlaySheet[] otherSheets, PlaySheet playSheet) {
        turns[rand.nextInt(turns.length)].setWeight(100);
    }

    @Override
    public void weighOtherTurns(Turn[] turns, DiceRoll diceRoll, PlaySheet[] otherSheets, PlaySheet playSheet) {
        turns[rand.nextInt(turns.length)].setWeight(100);
    }
}
