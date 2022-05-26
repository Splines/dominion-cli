package me.splines.dominion.card;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.splines.dominion.card.FormatExceptions.CardsDifferentHeightsException;

public class GridFormatter {

    private static final int GRID_WIDTH = 3; // 3 cards in one row
    private static final int GRID_HGAP = 4; // number of spacings between cards in one row
    private static final int GRID_VGAP = 2; // number of spacings between cards in one column

    public static String getFormattedGrid(List<Card> cards) {
        return constructFormattedGrid(cards, false);
    }

    public static String getFormattedGridWithIndex(List<Card> cards) {
        return constructFormattedGrid(cards, true);
    }

    private static String constructFormattedGrid(List<Card> cards, boolean withIndex) {
        StringBuilder grid = new StringBuilder();

        // Go through all rows
        int i = 0;
        while (i < cards.size()) {
            List<String> cardRowLines = getCardStringForOneRow(i, cards, withIndex);
            i += GRID_WIDTH; // i is now at the first card of next row

            // Combine all lines of one card row
            String row = String.join("\n", cardRowLines);
            if (i < cards.size())
                row += "\n".repeat(GRID_VGAP + 1);
            grid.append(row);
        }

        return grid.toString();
    }

    /**
     * Returns the card in the same row as string array.
     *
     * @param i
     * @param cardsSize
     * @return
     */
    private static List<String> getCardStringForOneRow(int i, List<Card> cards, boolean withIndex) {
        // Leftmost card
        Card leftmostCard = cards.get(i);
        String[] linesLeftmostCard = CardFormatter
                .getFormattedWithIndex(leftmostCard, withIndex ? i + 1 : -1)
                .split("\n");
        List<String> cardRowLines = new ArrayList<>(Arrays.asList(linesLeftmostCard));

        // Cards to the right in same row
        for (int j = 0; j < GRID_WIDTH - 1; j++) {
            i++;
            if (i >= cards.size())
                break;

            Card cardToTheRight = cards.get(i);
            String[] linesCardToTheRight = CardFormatter
                    .getFormattedWithIndex(cardToTheRight, withIndex ? i + 1 : -1)
                    .split("\n");
            if (linesCardToTheRight.length != linesLeftmostCard.length)
                throw new CardsDifferentHeightsException(List.of(leftmostCard, cardToTheRight));

            combineLinesOfOneCardRow(cardRowLines, linesCardToTheRight);
        }

        return cardRowLines;
    }

    /**
     * Adds rest of cards in the row to the lines (inline!).
     *
     * @param cardRowLines
     * @param newLines
     */
    private static void combineLinesOfOneCardRow(List<String> cardRowLines, String[] newLines) {
        for (int k = 0; k < cardRowLines.size(); k++) {
            String append = " ".repeat(GRID_HGAP) + newLines[k];
            cardRowLines.set(k, cardRowLines.get(k) + append);
        }
    }

}
