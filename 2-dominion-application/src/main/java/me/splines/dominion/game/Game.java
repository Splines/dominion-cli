package me.splines.dominion.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import me.splines.dominion.card.CardPool;
import me.splines.dominion.interaction.PlayerInteraction;

public class Game {

    private PlayerInteraction playerInteraction;
    private List<GamePlayer> players = new ArrayList<>();
    private Stock stock = new GameStock();

    public Game(PlayerInteraction playerInteraction, List<String> playerNames) {
        this.playerInteraction = playerInteraction;

        for (String name : playerNames) {
            Deck initialDrawDeck = getInitialDrawDeck();
            GamePlayer player = new GamePlayer(name, playerInteraction, initialDrawDeck, stock);
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
        while (true) {
            for (GamePlayer player : players) {
                if (hasGameEnded())
                    return;
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
        HashMap<GamePlayer, Integer> playerPoints = new HashMap<>();
        players.forEach(p -> playerPoints.put(p, p.calculatePoints()));
        int maxPoints = Collections.max(playerPoints.values());

        List<PlayerResult> results = new ArrayList<>();
        List<String> winners = new ArrayList<>();

        playerPoints.forEach((player, points) -> {
            results.add(new PlayerResult(player.getName(), points));
            if (points == maxPoints)
                winners.add(player.getName());
        });

        playerInteraction.information().results(results);

        String[] winnersArray = new String[winners.size()];
        playerInteraction.information().winners(winners.toArray(winnersArray));
    }

}
