package org.darkmarlin.quixx.players;

import org.darkmarlin.quixx.game.DiceRoll;
import org.darkmarlin.quixx.game.PlaySheet;
import org.darkmarlin.quixx.game.Turn;

public abstract class PlayerBrain {
    protected String name;

    public PlayerBrain(final String name) {
        this.name = name;
    }

    public abstract void weighSelfTurns(Turn[] turns, DiceRoll diceRoll, PlaySheet[] otherSheets, PlaySheet playSheet);

    public abstract void weighOtherTurns(Turn[] turns, DiceRoll diceRoll, PlaySheet[] otherSheets, PlaySheet playSheet);

    public String getName() {
        return name;
    }
}
