package org.darkmarlin.quixx.players;

import org.darkmarlin.quixx.game.DiceRoll;
import org.darkmarlin.quixx.game.PlaySheet;
import org.darkmarlin.quixx.game.Turn;

public abstract class PlayerBrain {
    protected PlaySheet playSheet;
    protected String name;

    public PlayerBrain(final PlaySheet playSheet, final String name) {
        this.playSheet = playSheet;
        this.name = name;
    }

    public abstract void weighSelfTurns(Turn[] turns, DiceRoll diceRoll, PlaySheet[] otherSheets);

    public abstract void weighOtherTurns(Turn[] turns, DiceRoll diceRoll, PlaySheet[] otherSheets);

    public String getName() {
        return name;
    }
}
