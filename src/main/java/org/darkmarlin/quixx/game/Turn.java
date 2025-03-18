package org.darkmarlin.quixx.game;

public class Turn  implements Comparable<Turn>{
    private final boolean doubleTurn;
    private final boolean noTurn;
    private final boolean selfTurn;

    private final PlaySheet.Rows playRow;
    private final int playNumber;

    private final PlaySheet.Rows secondPlayRow;
    private final int SecondPlayNumber;

    int weight = -1;

    public Turn(boolean selfTurn) {
        this.doubleTurn = false;
        this.noTurn = true;
        playRow = null;
        playNumber = -1;
        secondPlayRow = null;
        SecondPlayNumber = -1;
        this.selfTurn = selfTurn;
    }

    public Turn(PlaySheet.Rows playRow, int playNumber, boolean selfTurn) {
        this.playRow = playRow;
        this.playNumber = playNumber;
        this.secondPlayRow = null;
        this.SecondPlayNumber = -1;
        doubleTurn = false;
        noTurn = false;
        this.selfTurn = selfTurn;
    }

    public Turn(PlaySheet.Rows playRow, int playNumber, PlaySheet.Rows secondPlayRow, int secondPlayNumber, boolean selfTurn) {
        this.playRow = playRow;
        this.playNumber = playNumber;
        this.secondPlayRow = secondPlayRow;
        this.SecondPlayNumber = secondPlayNumber;
        doubleTurn = true;
        noTurn = false;
        this.selfTurn = selfTurn;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public boolean isNoTurn() {
        return noTurn;
    }

    public boolean isSelfTurn() {
        return selfTurn;
    }

    public boolean isDoubleTurn() {
        return doubleTurn;
    }

    public PlaySheet.Rows getPlayRow() {
        return playRow;
    }

    public int getPlayNumber() {
        return playNumber;
    }

    public PlaySheet.Rows getSecondPlayRow() {
        return secondPlayRow;
    }

    public int getSecondPlayNumber() {
        return SecondPlayNumber;
    }

    @Override
    public int compareTo(Turn o) {
        return o.weight - weight;
    }
}
