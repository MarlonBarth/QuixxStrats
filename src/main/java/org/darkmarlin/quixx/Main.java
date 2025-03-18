package org.darkmarlin.quixx;

import org.darkmarlin.quixx.game.GameMain;
import org.darkmarlin.quixx.players.Player;
import org.darkmarlin.quixx.players.PlayerBrain;
import org.darkmarlin.quixx.players.brains.InputBrain;

public class Main {
    public static void main(String[] args) {
        PlayerBrain[] brains = new PlayerBrain[]{new InputBrain("Tim"),new InputBrain("Alex")};
        GameMain game = new GameMain(brains);
        game.start();
        Player[] players = game.getPlayers();
        System.out.println(players[0]);
        System.out.println(players[1]);
    }
}