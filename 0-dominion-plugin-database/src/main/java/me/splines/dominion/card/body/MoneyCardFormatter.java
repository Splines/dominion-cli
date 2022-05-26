package me.splines.dominion.card.body;

import me.splines.dominion.card.CardFormatter;
import me.splines.dominion.card.MoneyCard;

public class MoneyCardFormatter extends CardBodyFormatter<MoneyCard> {

    @Override
    public String getBody(MoneyCard card) {
        StringBuilder body = new StringBuilder();
        body.append(getEmptyLineWithBorder());
        body.append(getTextToTheRight("💰💰💰"));
        // Assumption: card money is just one digit
        body.append(getTextToTheRight("💰" + card.getMoney() + " 💰"));
        body.append(getTextToTheRight("💰💰💰"));
        body.append(getEmptyLinesWithBorder(CardFormatter.CARD_BODY_HEIGHT - 3));
        return body.toString();
    }

}
