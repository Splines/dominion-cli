import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import me.splines.dominion.Card.ActionCard;
import me.splines.dominion.Card.Card;
import me.splines.dominion.Card.MoneyCard;
import me.splines.dominion.Game.PlayerDecision;

public class GameCLI implements PlayerDecision {

    @Override
    public Card chooseCard(List<Card> cards) {
        System.out.println("Choose one of these cards");
        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
            System.out.print((i + 1) + ": " + card.getName());
        }
        System.out.println();

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
        System.out.println("Choose one of these cards (optional)");
        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
            System.out.print((i + 1) + ": " + card.getName());
        }
        System.out.println();

        Optional<Integer> cardIndex;
        cardIndex = ConsoleUtil.getOptionalIntFromUser();
        if (cardIndex.isEmpty())
            return Optional.empty();
        if (cardIndex.get() >= 1 && cardIndex.get() <= cards.size())
            return Optional.of(cards.get(cardIndex.get()));
        return Optional.empty();
    }

    @Override
    public boolean checkWantToPlayActionCard() {
        System.out.println("Do you want to play one of your action cards?");
        boolean wantToPlay = ConsoleUtil.getBooleanFromUser();
        return wantToPlay;
    }

    @Override
    public boolean checkWantToBuy() {
        System.out.println("Do you want to buy a new card?");
        boolean wantToBuy = ConsoleUtil.getBooleanFromUser();
        return wantToBuy;
    }

    @Override
    public List<Card> chooseCardsToBuy(List<Card> cards) {
        return this.chooseCards(cards);
    }

    @Override
    public List<Card> chooseCards(List<Card> cards) {
        System.out.println("Choose any of these cards (separate by comma)");
        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
            System.out.print((i + 1) + ": " + card.getName());
        }
        System.out.println();

        List<Integer> cardIndices = new ArrayList<>();
        outer: while (true) {
            cardIndices = ConsoleUtil.getIntsFromUser();
            for (Integer cardIndex : cardIndices) {
                if (cardIndex < 1 || cardIndex > cards.size()) {
                    System.out.println(cardIndex + " is not a valid card number");
                    continue outer;
                }
            }
            break;
        }

        return cardIndices.stream()
                .map(i -> cards.get(i))
                .collect(Collectors.toList());
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

    @Override
    public void informYourTurn(String name) {
        System.out.println("It's your turn, " + name);
    }

    @Override
    public ActionCard chooseActionCard(List<ActionCard> cards) {
        List<Card> cardsGeneric = List.copyOf(cards);
        return (ActionCard) this.chooseCard(cardsGeneric);
    }

}
