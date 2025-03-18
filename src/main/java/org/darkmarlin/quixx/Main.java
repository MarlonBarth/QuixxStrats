package org.darkmarlin.quixx;

import org.darkmarlin.quixx.game.GameMain;
import org.darkmarlin.quixx.players.Player;
import org.darkmarlin.quixx.players.PlayerBrain;
import org.darkmarlin.quixx.players.brains.InputBrain;
import org.darkmarlin.quixx.players.brains.RandomPlayer;

public class Main {
    public static void main(String[] args) {
        PlayerBrain[] playerBrains = new PlayerBrain[6];
        for (int i = 0; i < playerBrains.length; i++) {
            playerBrains[i] = new RandomPlayer("Player: " + i);
        }
        GameMain[] games = new GameMain[50];
        GameRunner[] gameRunners = new GameRunner[50];
        for (int i = 0; i < games.length; i++) {
            games[i] = new GameMain(playerBrains);
            gameRunners[i] = new GameRunner(games[i]);
        }
        for (int i = 0; i < gameRunners.length; i++) {
            gameRunners[i].start();
        }

        for(int i = 0; i < gameRunners.length; i++){
            try {
                gameRunners[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        int[] points = new int[6];
        for (int i = 0; i < points.length; i++) {
            points[i] = 0;
            for(int j = 0; j < games.length; j++) {
                points[i] += games[j].getPlayers()[i].getScore();
            }
        }

        for (int i = 0; i < points.length; i++) {
            System.out.println(points[i]);
        }
    }

    private static class GameRunner extends Thread {
        private final GameMain game;
        public GameRunner(final GameMain game) {
            this.game = game;
        }

        @Override
        public void run() {
            game.start();
        }
    }
}