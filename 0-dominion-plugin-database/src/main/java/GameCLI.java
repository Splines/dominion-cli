import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import me.splines.dominion.Card.ActionCard;
import me.splines.dominion.Card.Card;
import me.splines.dominion.Card.MoneyCard;
import me.splines.dominion.Game.PlayerDecision;

public class GameCLI implements PlayerDecision {

    private Scanner sc = new Scanner(System.in);

    private int getIntFromUser() {
        while (true) {
            try {
                return Integer.parseInt(sc.next());
            } catch (NumberFormatException e) {
                System.out.println("That's not a whole number, try again...");
            }
        }
    }

    private boolean getBooleanFromUser() {
        while (true) {
            String line = sc.nextLine();
            if (line.equalsIgnoreCase("yes"))
                return true;
            else if (line.equalsIgnoreCase("no"))
                return false;
            System.out.println("That's not a valid answer (yes/no), try again...");
        }
    }

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
            cardIndex = getIntFromUser();
            if (cardIndex >= 1 && cardIndex <= cards.size())
                break;
            System.out.println("That's not a valid card");
        }

        return cards.get(cardIndex);
    }

    @Override
    public boolean checkWantToPlayActionCard() {
        System.out.println("Do you want to play one of your action cards?");
        boolean wantToPlay = getBooleanFromUser();
        return wantToPlay;
    }

    @Override
    public boolean checkWantToBuy() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void informAboutBuyableCards(List<Card> cards) {
        // TODO Auto-generated method stub

    }

    @Override
    public List<Card> chooseCards(List<Card> cards) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public MoneyCard chooseMoneyCard(List<MoneyCard> cards) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<MoneyCard> chooseOptionalMoneyCard(List<MoneyCard> cards) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void informYourTurn(String name) {
        System.out.println("It's your turn, " + name);
    }

    @Override
    public ActionCard chooseActionCard(List<ActionCard> cards) {
        List<Card> cardsGeneric = new ArrayList<>();
        cardsGeneric.addAll(cards);
        return (ActionCard) this.chooseCard(cardsGeneric);
    }

}
