package me.splines.dominion.Game;

import java.util.Collections;
import java.util.Stack;

import me.splines.dominion.Card.Card;

public class Deck {

    public class EmptyDeckException extends RuntimeException {

        public EmptyDeckException() {
            super("Deck is empty, cannot draw from it");
        }

    }

    private final Stack<Card> cards = new Stack<>();

    public Deck() {
    }

    public Card draw() {
        if (cards.empty()) {
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

}
