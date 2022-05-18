package me.splines.dominion.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import me.splines.dominion.Card.ActionCard;
import me.splines.dominion.Card.Card;
import me.splines.dominion.Card.MoneyCard;
import me.splines.dominion.Game.Deck.EmptyDeckException;
import me.splines.dominion.Instruction.Instruction;

public final class Player extends PlayerAbstract {

    private final PlayerDecision playerDecision;
    private final GameState state;

    public Player(String name, PlayerDecision playerDecision, GameState state) {
        super(name);
        this.playerDecision = playerDecision;
        this.state = state;
    }

    public PlayerDecision getDecisionHandle() {
        return this.playerDecision;
    }

    @Override
    public Card draw() {
        try {
            Card card = drawDeck.draw();
            hand.add(card);
            return card;
        } catch (EmptyDeckException e) {
            makeDrawDeckFromDiscardDeck();
            draw();
        }
        return null;
    }

    /**
     * Make a new draw deck from the discard deck
     * by shuffling the cards and putting them on the draw deck.
     *
     * Afterwards the discardDeck is empty and the drawDeck has all the cards
     * from the drawDeck.
     */
    private void makeDrawDeckFromDiscardDeck() {
        discardDeck.shuffle();
        while (true) {
            try {
                Card card = discardDeck.draw();
                drawDeck.put(card);
            } catch (EmptyDeckException e) {
                break;
            }
        }
    }

    @Override
    public void makeMove() {
        Move move = new Move();

        // 1st PHASE - Action phase
        // player MAY play as many action card
        while (true) {
            // Choose action card to play
            Optional<ActionCard> actionCard = playerDecision.chooseOptionalActionCard(hand);
            if (actionCard.isEmpty()) {
                // Player does not want to play another action card
                break;
            }
            // Execute all instructions of action card
            List<Instruction> instructions = actionCard.get().getAction().getInstructions();
            instructions.forEach(
                    (instruction) -> instruction.execute(this, move, this.playerDecision, this.state.getStock()));
        }

        // 2nd PHASE - Buy phase
        // player MAY buy cards according to money available

        // 3rd PHASE - "Clean up" phase
        // player MUST put all hand & table cards to the discard deck ("discard" these
        // cards)
        // player MUST draw 5 new cards for the next move
    }

    @Override
    public void discard(Card card) {
        removeFromHand(card);
        discardDeck.put(card);
    }

    @Override
    public void dispose(Card card) {
        removeFromHand(card);
    }

    private void removeFromHand(Card card) {
        boolean contained = hand.remove(card);
        if (!contained) {
            throw new HandDoesNotHaveCard(card);
        }
    }

    @Override
    public List<Card> getHand() {
        return new ArrayList<>(hand); // copy by value
    }

    @Override
    public List<MoneyCard> getMoneyCardsOnHand() {
        return getHand()
                .stream()
                .filter(MoneyCard.class::isInstance)
                .map(MoneyCard.class::cast)
                .collect(Collectors.toList());
    }

    @Override
    public void take(Card card) {
        discardDeck.put(card);
    }

    @Override
    public void takeToHand(Card card) {
        hand.add(card);
    }

}
