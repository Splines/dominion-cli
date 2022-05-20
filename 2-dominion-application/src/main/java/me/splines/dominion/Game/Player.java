package me.splines.dominion.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import me.splines.dominion.Card.ActionCard;
import me.splines.dominion.Card.Card;
import me.splines.dominion.Card.CardType;
import me.splines.dominion.Card.MoneyCard;
import me.splines.dominion.Game.Deck.EmptyDeckException;
import me.splines.dominion.Instruction.Instruction;

public final class Player extends PlayerAbstract {

    private final PlayerDecision playerDecision;

    public Player(String name, PlayerDecision playerDecision, Deck drawDeck) {
        super(name, drawDeck);
        this.playerDecision = playerDecision;
        drawHandCards();
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
            return draw();
        }
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
                discardDeck.draw();
                Card card = discardDeck.draw();
                drawDeck.put(card);
            } catch (EmptyDeckException e) {
                break;
            }
        }
    }

    @Override
    public void makeMove() {
        playerDecision.informYourTurn(this.name);
        Move move = new Move();
        doActionPhase(move);
        doBuyPhase(move);
        doCleanUpPhase(move);
    }

    /**
     * 1st PHASE - Action phase
     * Player MAY play as many action card as he/she wants.
     *
     * @param move
     */
    private void doActionPhase(Move move) {
        while (move.getActionsCount() > 0 && playerDecision.checkWantToPlayActionCard()) {
            move.looseAction();
            // Choose action card to play
            List<ActionCard> actionCardsInHand = hand.stream()
                    .filter(card -> card.getType() == CardType.ACTION)
                    .map(ActionCard.class::cast)
                    .collect(Collectors.toList());
            ActionCard actionCard = playerDecision.chooseActionCard(actionCardsInHand);
            // Execute all instructions of action card
            List<Instruction> instructions = actionCard.getAction().getInstructions();
            instructions.forEach((i) -> i.execute(this, move, this.playerDecision, GameState.stock));
        }
    }

    /**
     * 2nd PHASE - Buy phase
     * Player MAY buy cards according to his/her available money.
     *
     * @param move
     */
    private void doBuyPhase(Move move) {
        // Earn money from money cards
        for (Card card : hand) {
            if (card instanceof MoneyCard) {
                int money = ((MoneyCard) card).getMoney();
                move.earnMoney(money);
            }
        }

        // Buy cards
        List<Card> buyableCards = GameState.stock.getAvailableCardsWithMaxCosts(move.getMoney());
        if (!buyableCards.isEmpty()) {
            while (!buyableCards.isEmpty() && move.getMoney() >= 1 &&
                    playerDecision.checkWantToBuy()) {
                List<Card> boughtCards = new ArrayList<>();
                playerDecision.chooseCardsToBuy(buyableCards);
                Card boughtCard = playerDecision.chooseCard(buyableCards);
                boughtCards.add(boughtCard);

                move.looseBuying();
                move.looseMoney(boughtCard.getCost());

                buyableCards = GameState.stock.getAvailableCardsWithMaxCosts(move.getMoney());
            }
        }
    }

    /**
     * 3rd PHASE - "Clean up" phase
     * Player MUST put all hand & table cards to the discard deck.
     * Player MUST draw 5 new cards for the next move.
     *
     * @param move
     */
    private void doCleanUpPhase(Move move) {
        hand.forEach(h -> discardDeck.put(h));
        hand = new ArrayList<>();
        drawHandCards();
    }

    private void drawHandCards() {
        for (int i = 0; i < 5; i++) {
            Card card = this.draw();
            hand.add(card);
        }
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
