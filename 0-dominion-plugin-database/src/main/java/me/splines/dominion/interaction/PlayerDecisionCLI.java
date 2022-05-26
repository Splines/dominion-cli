package me.splines.dominion.interaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import me.splines.dominion.ConsoleUtil;
import me.splines.dominion.card.ActionCard;
import me.splines.dominion.card.Card;
import me.splines.dominion.card.CardFormatter;
import me.splines.dominion.card.MoneyCard;

public class PlayerDecisionCLI implements PlayerDecision {

    private void printCardsWithNumbers(List<Card> cards) {
        String grid = CardFormatter.getFormattedGrid(cards);
        System.out.println(grid);
    }

    ////////////////////////// Choose players //////////////////////////////////

    public List<String> getPlayerNames() {
        System.out.println("▶ Knock knock, who do we have here?");
        System.out.println("Write down your names in a comma-separated list");
        System.out.println("(e.g. Sigrid, Thomas, Eleonore, Ella):");
        List<String> stringListFromUser;
        while (true) {
            stringListFromUser = ConsoleUtil.getStringListFromUser();
            if (stringListFromUser.size() != 1)
                break;
            System.out.println("Sorry, but playing Dominion alone is really no fun");
            System.out.println("Write down your names again (separated by comma)");
        }
        System.out.println();
        return stringListFromUser;
    }

    //////////////////////// Choose cards (general) ////////////////////////////

    @Override
    public Card chooseCard(List<Card> cards) {
        System.out.println("Choose one of these cards");
        printCardsWithNumbers(cards);

        int cardIndex = -1;
        while (true) {
            cardIndex = ConsoleUtil.getIntFromUser();
            if (cardIndex >= 1 && cardIndex <= cards.size())
                break;
            System.out.println("That's not a valid card number");
        }

        return cards.get(cardIndex);
    }

    private Optional<Card> chooseOptionalCard(List<Card> cards) {
        System.out.println("Choose one of these cards (or - for no card)");
        printCardsWithNumbers(cards);

        Optional<Integer> cardIndex;
        while (true) {
            cardIndex = ConsoleUtil.getOptionalIntFromUser("-");
            if (cardIndex.isEmpty())
                return Optional.empty();
            if (cardIndex.get() >= 1 && cardIndex.get() <= cards.size())
                return Optional.of(cards.get(cardIndex.get() - 1));
            System.out.println("This is not a vaid card, try again...");
        }
    }

    @Override
    public List<Card> chooseCards(List<Card> cards) {
        System.out.println("Choose any of these cards (separate by comma)");
        printCardsWithNumbers(cards);

        List<Integer> cardIndices = new ArrayList<>();
        boolean passed = false;
        while (!passed) {
            passed = true;

            cardIndices = ConsoleUtil.getIntsFromUser();
            for (Integer cardIndex : cardIndices) {
                if (cardIndex < 1 || cardIndex > cards.size()) {
                    System.out.println(cardIndex + " is not a valid card number");
                    passed = false;
                    break; // aka continue while loop and try again
                }
            }
        }

        return cardIndices.stream().map(cards::get).toList();
    }

    ////////////////////// Choose cards (specific) /////////////////////////////

    @Override
    public ActionCard chooseActionCard(List<ActionCard> cards) {
        List<Card> cardsGeneric = List.copyOf(cards);
        return (ActionCard) this.chooseCard(cardsGeneric);
    }

    @Override
    public Optional<ActionCard> chooseOptionalActionCard(List<ActionCard> cards) {
        List<Card> cardsGeneric = List.copyOf(cards);
        return this.chooseOptionalCard(cardsGeneric).map(ActionCard.class::cast);
    }

    @Override
    public List<Card> chooseCardsToBuy(List<Card> cards) {
        return this.chooseCards(cards);
    }

    @Override
    public Optional<Card> chooseOptionalCardToBuy(List<Card> cards) {
        return this.chooseOptionalCard(cards);
    }

    @Override
    public MoneyCard chooseMoneyCard(List<MoneyCard> cards) {
        List<Card> cardsGeneric = List.copyOf(cards);
        return (MoneyCard) this.chooseCard(cardsGeneric);
    }

    @Override
    public Optional<MoneyCard> chooseOptionalMoneyCard(List<MoneyCard> cards) {
        List<Card> cardsGeneric = List.copyOf(cards);
        return this.chooseOptionalCard(cardsGeneric).map(MoneyCard.class::cast);
    }

}
