package me.splines.dominion.card.body;

import me.splines.dominion.card.Card;
import me.splines.dominion.card.CardFormatter;

public abstract class CardBodyFormatter<T extends Card> {

    public abstract String getBody(T card);

    //////////////////////// Common Formatting /////////////////////////////////

    protected String getEmptySpaces(int count) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < count; i++) {
            str.append(" ");
        }
        return str.toString();
    }

    protected String getEmptyLineWithBorder() {
        StringBuilder str = new StringBuilder();
        str.append(CardFormatter.SYMBOL_VERTICAL_BORDER);
        for (int i = 0; i < CardFormatter.CARD_WIDTH - 2; i++) {
            str.append(" ");
        }
        str.append(CardFormatter.SYMBOL_VERTICAL_BORDER);
        str.append("\n");
        return str.toString();
    }

    protected String getEmptyLinesWithBorder(int count) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < count; i++) {
            str.append(getEmptyLineWithBorder());
        }
        return str.toString();
    }

    protected String getLeftBorderWithSpace() {
        StringBuilder str = new StringBuilder();
        str.append(CardFormatter.SYMBOL_VERTICAL_BORDER);
        str.append(" ");
        return str.toString();
    }

    protected String getRightBorderWithSpace() {
        StringBuilder str = new StringBuilder();
        str.append(" ");
        str.append(CardFormatter.SYMBOL_VERTICAL_BORDER);
        return str.toString();
    }

    protected String getTextToTheRight(String text) {
        StringBuilder str = new StringBuilder();
        str.append(getLeftBorderWithSpace());
        str.append(getEmptySpaces(CardFormatter.CARD_WIDTH - 4 - 4 - 2));
        str.append(text);
        str.append(getRightBorderWithSpace());
        str.append("\n");
        return str.toString();
    }

}
