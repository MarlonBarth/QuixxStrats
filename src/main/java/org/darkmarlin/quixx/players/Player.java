package org.darkmarlin.quixx.players;

import org.darkmarlin.quixx.game.DiceRoll;
import org.darkmarlin.quixx.game.GameMain;
import org.darkmarlin.quixx.game.PlaySheet;
import org.darkmarlin.quixx.game.Turn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Player implements Comparable<Player> {
    private GameMain game;
    private PlaySheet playSheet;
    private PlayerBrain brain;
    private PlaySheet[] otherPlaySheets;

    private int score;

    public Player(PlaySheet playSheet, PlayerBrain brain, GameMain game) {
        this.playSheet = playSheet;
        this.game = game;
        this.brain = brain;
    }

    public void setOtherPlaySheets(PlaySheet[] otherPlaySheets) {
        this.otherPlaySheets = otherPlaySheets;
    }

    public void selfTurn(DiceRoll roll){
        Turn[] turns = determineSelfTurns(roll);
        weighTurns(turns,true, roll);
        Turn turn = getHighestTurn(turns);
        playSheet.makeTurn(turn);
    };
    public void otherTurn(DiceRoll roll){
        Turn[] turns = determineOtherTurns(roll);
        weighTurns(turns,false, roll);
        Turn turn = getHighestTurn(turns);
        playSheet.makeTurn(turn);
    };

    private Turn[] determineSelfTurns(DiceRoll roll){
        return null;
    }

    private Turn[] determineOtherTurns(DiceRoll roll){
        List<Turn> turns = new ArrayList<Turn>();

        int[][] availableTurns = playSheet.getAllowedMoves();
        int white = roll.getWhite();
        for(int i = 0; i < availableTurns.length; i++){
            boolean possibleTurn = false;
            for(int number: availableTurns[i]){
                if(number == white){
                    possibleTurn = true;
                }
            }
            if(possibleTurn){
                turns.add(new Turn(PlaySheet.Rows.getRow(i),white,false));
            }
        }
        turns.add(new Turn(false));
        return turns.toArray(new Turn[0]);
    }

    private void weighTurns(Turn[] turns, boolean selfTurn, DiceRoll roll) {
        if(selfTurn){
            brain.weighSelfTurns(turns,roll,otherPlaySheets);
        }else{
            brain.weighOtherTurns(turns,roll,otherPlaySheets);
        }

    }

    private Turn getHighestTurn(Turn[] turns) {
        Arrays.sort(turns);
        return turns[0];
    }

    public int getScore() {
        return score;
    }

    public PlaySheet getPlaySheet() {
        return playSheet;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName(){
        return brain.getName();
    }

    @Override
    public int compareTo(Player o) {
        return o.getScore() - getScore();
    }
}
