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
        List<Turn> turns = new ArrayList<>();
        int[][] availableTurns = playSheet.getAllowedMoves();

        //WHITE
        List<Turn> whiteTurns = new ArrayList<>();
        int white = roll.getWhite();
        for(int i = 0; i < availableTurns.length; i++){
            boolean possibleTurn = false;
            for(int number: availableTurns[i]){
                if(number == white){
                    possibleTurn = true;
                    break;
                }
            }
            if(possibleTurn){
                whiteTurns.add(new Turn(PlaySheet.Rows.getRow(i),white,true));
            }
        }

        //RED
        List<Turn> redTurns = new ArrayList<>();
        int[] red = roll.getRed();
        for(int i:red){
            boolean possibleTurn = false;
            for(int number: availableTurns[i]){
                if(number == red[i]){
                    possibleTurn = true;
                    break;
                }
            }
            if(possibleTurn){
                redTurns.add(new Turn(PlaySheet.Rows.RED,red[i],true));
            }
        }

        //YELLOW
        List<Turn> yellowTurns = new ArrayList<>();
        int[] yellow = roll.getYellow();
        for(int i:yellow){
            boolean possibleTurn = false;
            for(int number: availableTurns[i]){
                if(number == yellow[i]){
                    possibleTurn = true;
                    break;
                }
            }
            if(possibleTurn){
                yellowTurns.add(new Turn(PlaySheet.Rows.YELLOW,yellow[i],true));
            }
        }

        //GREEN
        List<Turn> greenTurns = new ArrayList<>();
        int[] green = roll.getGreen();
        for(int i:green){
            boolean possibleTurn = false;
            for(int number: availableTurns[i]){
                if(number == green[i]){
                    possibleTurn = true;
                    break;
                }
            }
            if(possibleTurn){
                greenTurns.add(new Turn(PlaySheet.Rows.GREEN,green[i],true));
            }
        }

        //BLUE
        List<Turn> blueTurns = new ArrayList<>();
        int[] blue = roll.getBlue();
        for(int i:blue){
            boolean possibleTurn = false;
            for(int number: availableTurns[i]){
                if(number == blue[i]){
                    possibleTurn = true;
                    break;
                }
            }
            if(possibleTurn){
                blueTurns.add(new Turn(PlaySheet.Rows.BLUE,blue[i],true));
            }
        }

        turns.addAll(redTurns);
        turns.addAll(yellowTurns);
        turns.addAll(greenTurns);
        turns.addAll(blueTurns);

        //Double Turns
        List<Turn> doubleTurns = new ArrayList<>();
        if(!whiteTurns.isEmpty()){
            for(Turn turn: whiteTurns){
                for(Turn otherTurn: turns){
                    if(turn.getPlayRow() != otherTurn.getPlayRow()){
                        doubleTurns.add(new Turn(turn.getPlayRow(),turn.getPlayNumber(),otherTurn.getPlayRow(),otherTurn.getPlayNumber(),true));
                    }
                    else{
                        if(turn.getPlayNumber() < otherTurn.getPlayNumber()){
                            doubleTurns.add(new Turn(turn.getPlayRow(),turn.getPlayNumber(),otherTurn.getPlayRow(),otherTurn.getPlayNumber(),true));
                        }
                    }
                }
            }
        }
        turns.addAll(doubleTurns);
        turns.addAll(whiteTurns);
        turns.add(new Turn(true));
        return turns.toArray(new Turn[0]);
    }

    private Turn[] determineOtherTurns(DiceRoll roll){
        List<Turn> turns = new ArrayList<>();

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
