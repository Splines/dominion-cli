package me.splines.dominion;

import java.util.List;

import me.splines.dominion.game.Game;

public class Dominion {

    public static void main(String[] args) {
        GameCLI cli = new GameCLI();
        List<String> names = cli.getPlayerNames();
        Game game = new Game(cli, names);
        game.start();
    }

}
