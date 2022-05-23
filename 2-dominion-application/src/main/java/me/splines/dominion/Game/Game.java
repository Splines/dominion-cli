package me.splines.dominion.Game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import me.splines.dominion.Card.CardPool;

public class Game {

    private PlayerDecision playerDecision;
    private List<Player> players = new ArrayList<>();
    private Stock stock = new GameStock();

    public Game(PlayerDecision playerDecision, List<String> playerNames) {
        this.playerDecision = playerDecision;

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

    public void start() {
        gameLoop();
        announceResultsAndWinners();
    }

    private void gameLoop() {
        while (!hasGameEnded()) {
            for (Player player : players) {
                player.makeMove();
            }
        }
    }

    private boolean hasGameEnded() {
        boolean provinceCardStockEmpty = stock.getCardStock(CardPool.provinceCard).isEmpty();
        boolean threeStocksEmpty = stock.getNumberOfEmptyCardStocks() >= 3;
        return provinceCardStockEmpty || threeStocksEmpty;
    }

    private void announceResultsAndWinners() {
        HashMap<Player, Integer> playerPoints = new HashMap<>();
        players.forEach(p -> playerPoints.put(p, p.calculatePoints()));
        int maxPoints = Collections.max(playerPoints.values());

        List<PlayerResult> results = new ArrayList<>();
        List<String> winners = new ArrayList<>();

        playerPoints.forEach((player, points) -> {
            results.add(new PlayerResult(player.getName(), points));
            if (points == maxPoints)
                winners.add(player.getName());
        });

        playerDecision.announceResults(results);

        String[] winnersArray = new String[playerPoints.size()];
        playerDecision.announceWinners(winners.toArray(winnersArray));
    }

}
