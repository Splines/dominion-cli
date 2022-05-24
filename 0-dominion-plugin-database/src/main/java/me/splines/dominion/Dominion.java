package me.splines.dominion;

import java.util.List;

import me.splines.dominion.game.Game;

public class Dominion {

    public static void main(String[] args) {
        printStartScreen();

        GameCLI cli = new GameCLI();
        List<String> names = cli.getPlayerNames();
        Game game = new Game(cli, names);
        game.start();
    }

    private static void printStartScreen() {
        System.out.println("🃏🃏🃏🃏🃏🃏🃏🃏🃏🃏🃏");
        System.out.println("🃏🃏 Dominion CLI 🃏🃏");
        System.out.println("🃏🃏  by Splines  🃏🃏");
        System.out.println("🃏🃏🃏🃏🃏🃏🃏🃏🃏🃏🃏");
        System.out.println();
        System.out.println("Welcome to Dominion - a deck-building card game.");
        System.out.println("Have fun with this CLI, e.g. use it to train");
        System.out.println("an artificial intelligence.");
        System.out.println("If there are any bugs, please report them on GitHub:");
        System.out.println("https://github.com/splines/dominion-cli");
        System.out.println();
    }

}
