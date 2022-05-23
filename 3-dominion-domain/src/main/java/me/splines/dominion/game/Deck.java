package me.splines.dominion.game;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import me.splines.dominion.card.Card;

public class Deck {

    public static class EmptyDeckException extends RuntimeException {
        public EmptyDeckException() {
            super("Deck is empty, cannot draw from it");
        }
    }

    public static class NotEnoughCardsOnDeckException extends RuntimeException {
        public NotEnoughCardsOnDeckException(String msg) {
            super(msg);
        }
    }

    private final LinkedList<Card> cards = new LinkedList<>();

    public Deck() {
    }

    public Card draw() {
        if (cards.isEmpty()) {
            throw new EmptyDeckException();
        }
        return cards.pop();
    }

    public void put(Card card) {
        cards.push(card);
    }

    public void putCardSeveralTimes(Card card, int count) {
        for (int i = 0; i < count; i++) {
            this.put(card);
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public int size() {
        return cards.size();
    }

    public List<Card> asList() {
        return List.copyOf(cards);
    }

}
