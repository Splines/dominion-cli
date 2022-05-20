package me.splines.dominion.Game;

import java.util.ArrayList;
import java.util.List;

import me.splines.dominion.Card.CardPool;

public class Game {

    private List<Player> players = new ArrayList<>();

    public Game(PlayerDecision playerDecision, List<String> playerNames) {
        for (String name : playerNames) {
            Deck initialDrawDeck = getInitialDrawDeck();
            Player player = new Player(name, playerDecision, initialDrawDeck);
            players.add(player);
        }
    }

    private Deck getInitialDrawDeck() {
        Deck initialDrawDeck = new Deck();
        initialDrawDeck.putCardSeveralTimes(CardPool.copperCard, 7);
        initialDrawDeck.putCardSeveralTimes(CardPool.estateCard, 3);
        initialDrawDeck.shuffle();
        return initialDrawDeck;
    }

    public void start() {
        // TODO condition when game ends
        while (true) {
            for (Player player : players) {
                player.makeMove();
            }
        }
    }

}
