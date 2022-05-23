package me.splines.dominion.game;

import java.util.ArrayList;
import java.util.List;

import me.splines.dominion.card.Card;

public final class MoveState {

    private int cardsCount = 0;
    private int actionsCount = 1;
    private int buyingsCount = 1;
    private int money = 0;

    // Cards that are currently played in this move
    private final List<Card> playingCards = new ArrayList<>();

    public MoveState() {

    }

    public int getCardsCount() {
        return this.cardsCount;
    }

    public void earnCards(int cardsCount) {
        this.cardsCount += cardsCount;
    }

    public int getActionsCount() {
        return this.actionsCount;
    }

    public void earnActions(int actionsCount) {
        this.actionsCount += actionsCount;
    }

    public void looseAction() {
        this.actionsCount--;
    }

    public int getBuyingsCount() {
        return this.buyingsCount;
    }

    public void earnBuyings(int buyingsCount) {
        this.buyingsCount += buyingsCount;
    }

    public void looseBuying() {
        this.buyingsCount--;
    }

    public int getMoney() {
        return this.money;
    }

    public void earnMoney(int money) {
        this.money += money;
    }

    public void looseMoney(int money) {
        this.money -= money;
    }

    public void playCard(Card card) {
        playingCards.add(card);
    }

    public List<Card> getPlayedCards() {
        return playingCards;
    }

}
