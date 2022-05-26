package me.splines.dominion.interaction;

import java.util.List;

import me.splines.dominion.card.Card;
import me.splines.dominion.game.PlayerResult;

public interface PlayerInformation {

    void startActionPhase();

    void startBuyingPhase();

    void noActionCardsPlayable();

    void noCardsBuyableWithMoney(int money);

    void yourTurn(String name, List<Card> handCards);

    void results(List<PlayerResult> results);

    void winners(String... names);

}
