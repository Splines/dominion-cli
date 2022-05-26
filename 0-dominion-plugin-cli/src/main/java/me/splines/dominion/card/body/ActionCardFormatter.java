package me.splines.dominion.card.body;

import me.splines.dominion.action.Instruction;
import me.splines.dominion.card.ActionCard;
import me.splines.dominion.card.CardFormatter;
import me.splines.dominion.card.CardFormatterUtil;

public final class ActionCardFormatter extends CardBodyFormatter<ActionCard> {

    public String getBody(ActionCard card) {
        StringBuilder body = new StringBuilder();
        body.append(getEmptyLineWithBorder());
        int lineCounter = 0;
        for (Instruction instruction : card.getAction().getInstructions()) {
            String text = CardFormatterUtil.wrapLine(instruction.getName(),
                    CardFormatter.CARD_WIDTH - 4, "\n");
            String[] lines = text.split("\n");
            for (String line : lines) {
                lineCounter++;
                body.append(getLine(line));
            }
        }
        body.append(getEmptyLinesWithBorder(CardFormatter.CARD_BODY_HEIGHT - lineCounter));
        return body.toString();
    }

    private String getLine(String line) {
        StringBuilder str = new StringBuilder();

        // Left part
        str.append(CardFormatter.SYMBOL_VERTICAL_BORDER + " ");

        // Middle part
        int spacesToTheRight = CardFormatter.CARD_WIDTH - 2 - line.length() - 2;
        str.append(line);
        for (int j = 0; j < spacesToTheRight; j++) {
            str.append(" ");
        }

        // Right part
        str.append(" " + CardFormatter.SYMBOL_VERTICAL_BORDER);

        str.append("\n");

        return str.toString();
    }

}
