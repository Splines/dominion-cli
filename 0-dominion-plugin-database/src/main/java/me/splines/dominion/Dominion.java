package me.splines.dominion;

import java.util.List;

import me.splines.dominion.game.Game;
import me.splines.dominion.interaction.PlayerDecisionCLI;
import me.splines.dominion.interaction.PlayerInformationCLI;
import me.splines.dominion.interaction.PlayerInteraction;

public class Dominion {

    public static void main(String[] args) {
        printStartScreen();

        PlayerDecisionCLI decisionCLI = new PlayerDecisionCLI();
        PlayerInformationCLI informationCLI = new PlayerInformationCLI();
        PlayerInteraction interaction = new PlayerInteraction(decisionCLI, informationCLI);

        List<String> names = decisionCLI.getPlayerNames();
        Game game = new Game(interaction, names);
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
