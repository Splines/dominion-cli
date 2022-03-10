package me.splines.dominion.Game;

import java.util.EmptyStackException;
import java.util.Stack;

import me.splines.dominion.Card.Card;

public class Pile {

    public class EmptyPileException extends RuntimeException {

        public EmptyPileException() {
            super("Pile is empty, cannot draw from it");
        }

    }

    private final Stack<Card> stack = new Stack<>();

    public Pile() {

    }

    public Card drawCard() {
        try {
            return stack.pop();
        } catch (EmptyStackException e) {
            throw new EmptyPileException();
        }
    }

    public void putCard(Card card) {
        stack.push(card);
    }

}
