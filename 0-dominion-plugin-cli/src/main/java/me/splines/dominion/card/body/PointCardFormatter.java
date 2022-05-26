package me.splines.dominion.card.body;

import me.splines.dominion.card.CardFormatter;
import me.splines.dominion.card.PointCard;

public class PointCardFormatter extends CardBodyFormatter<PointCard> {

    @Override
    public String getBody(PointCard card) {
        StringBuilder body = new StringBuilder();
        body.append(getEmptyLineWithBorder());
        body.append(getTextToTheRight("🟩🟩🟩"));
        // Assumption: card money is just one digit
        body.append(getTextToTheRight("🟩" + card.getPoints() + " 🟩"));
        body.append(getTextToTheRight("🟩🟩🟩"));
        body.append(getEmptyLinesWithBorder(CardFormatter.CARD_BODY_HEIGHT - 3));
        return body.toString();
    }

}
