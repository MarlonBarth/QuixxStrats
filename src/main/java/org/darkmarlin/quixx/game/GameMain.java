package org.darkmarlin.quixx.game;

import org.darkmarlin.quixx.players.Player;
import org.darkmarlin.quixx.players.PlayerBrain;

import java.util.Arrays;

public class GameMain {
    private boolean redLock = false;
    private boolean yellowLock = false;
    private boolean blueLock = false;
    private boolean greenLock = false;

    private PlaySheet[] sheets;

    private Player[] players;
    private int currentPlayer = 0;

    private boolean done = false;
    private boolean gameOver = false;

    public GameMain(PlayerBrain... playerBrains) {
        players = new Player[playerBrains.length];
        PlaySheet[] sheets = new PlaySheet[playerBrains.length];
        for (int i = 0; i < players.length; i++) {
            players[i] = new Player((sheets[i] = new PlaySheet(this)),playerBrains[i],this);
        }
        for (int i = 0; i < players.length; i++) {
            PlaySheet[] otherSheets = new PlaySheet[players.length - 1];
            int counter = 0;
            for(int j = 0; j < sheets.length; j++) {
                if(j != i) otherSheets[counter++] = sheets[j];
            }
            players[i].setOtherPlaySheets(otherSheets);
        }
    }

    public void start(){
        while(!gameOver){
            turn();
            checkGameOver();
        }

        for(Player player : players) {
            player.setScore(player.getPlaySheet().getPoints());
        }

        Arrays.sort(players);
        done = true;
    }

    private void turn(){
        DiceRoll roll = DiceRoll.newRoll();
        players[currentPlayer].selfTurn(roll);
        for(int i = ((currentPlayer + 1) % players.length); i != currentPlayer; i = ((i+1) % players.length)){
            players[i].otherTurn(roll);
        }
    }

    public void fiveNoTurns(){
        gameOver = true;
    }

    private void checkGameOver(){
        if(redLock){
            if(yellowLock){
                gameOver = true;
            }
            if(blueLock){
                gameOver = true;
            }
            if(greenLock){
                gameOver = true;
            }
        }else{
            if(yellowLock){
                if(blueLock){
                    gameOver = true;
                }
                if(greenLock){
                    gameOver = true;
                }
            }else{
                if(blueLock){
                    if(greenLock){
                        gameOver = true;
                    }
                }
            }
        }
    }


    public PlaySheet[] getSheets() {
        return sheets;
    }

    public boolean isRedLock() {
        return redLock;
    }

    public boolean isYellowLock() {
        return yellowLock;
    }

    public boolean isBlueLock() {
        return blueLock;
    }

    public boolean isGreenLock() {
        return greenLock;
    }

    public boolean lock(PlaySheet.Rows row){
        switch (row) {
            case RED -> {
                boolean ret = !redLock;
                redLock = true;
                return ret;
            }
            case YELLOW -> {
                boolean ret = !yellowLock;
                yellowLock = true;
                return ret;
            }
            case BLUE -> {
                boolean ret = !blueLock;
                blueLock = true;
                return ret;
            }
            case GREEN -> {
                boolean ret = !greenLock;
                greenLock = true;
                return ret;
            }
            case null, default -> throw new IllegalStateException("Unexpected value: " + row);
        }
    }
}
