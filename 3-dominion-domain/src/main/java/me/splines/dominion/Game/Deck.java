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

    public Card drawCard() {
        if (cards.empty()) {
            throw new EmptyDeckException();
        }
        return cards.pop();
    }

    public void putCard(Card card) {
        cards.push(card);
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

}