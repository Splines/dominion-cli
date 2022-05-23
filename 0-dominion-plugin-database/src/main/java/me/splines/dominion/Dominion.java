package me.splines.dominion;

import java.util.List;

import me.splines.dominion.Game.Game;

public class Dominion {

    public static void main(String[] args) {
        GameCLI cli = new GameCLI();
        List<String> names = List.of("Player1", "Player2", "Player3");
        Game game = new Game(cli, names);
        game.startGameLoop();
    }

}
