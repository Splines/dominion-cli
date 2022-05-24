package me.splines.dominion.interaction;

import java.util.List;

import me.splines.dominion.game.PlayerResult;

public class PlayerInformationCLI implements PlayerInformation {

    @Override
    public void startActionPhase() {
        System.out.println("⚡ Action Phase");
    }

    @Override
    public void startBuyingPhase() {
        System.out.println("🤑 Buying Phase");
    }

    @Override
    public void noActionCardsPlayable() {
        System.out.println("No action cards in your hand");
        System.out.println();
    }

    @Override
    public void noCardsBuyableWithMoney(int money) {
        System.out.println("Can't buy any cards with money " + money);
    }

    @Override
    public void yourTurn(String name) {
        System.out.println();
        System.out.println("🎴🎴");
        System.out.println("🎴🎴  " + name);
        System.out.println("🎴🎴");
    }

    @Override
    public void results(List<PlayerResult> results) {
        System.out.println("These are your results");
        for (PlayerResult playerResult : results) {
            System.out.println(playerResult.getName() + ": "
                    + playerResult.getPoints() + " Points");
        }
    }

    @Override
    public void winners(String... names) {
        if (names.length >= 1) {
            System.out.println("There are multiple winners:");
        }
        System.out.println("Congratulations: " + String.join(",", names));
    }

}
