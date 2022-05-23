package me.splines.dominion.Game;

import java.util.ArrayList;
import java.util.List;

import me.splines.dominion.Card.CardPool;

public class Game {

    private List<Player> players = new ArrayList<>();
    private Stock stock = new GameStock();

    public Game(PlayerDecision playerDecision, List<String> playerNames) {
        for (String name : playerNames) {
            Deck initialDrawDeck = getInitialDrawDeck();
            Player player = new Player(name, playerDecision, initialDrawDeck, stock);
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

    private boolean hasGameEnded() {
        boolean provinceCardStockEmpty = stock.getCardStock(CardPool.provinceCard).isEmpty();
        boolean threeStocksEmpty = stock.getNumberOfEmptyCardStocks() >= 3;
        return provinceCardStockEmpty || threeStocksEmpty;
    }

    private void notifyWinnerLooser() {
        // TODO: Implement notify winner & looser
    }

    public void startGameLoop() {
        // Game loop
        while (!hasGameEnded()) {
            for (Player player : players) {
                player.makeMove();
            }
        }
        notifyWinnerLooser();
    }

}
