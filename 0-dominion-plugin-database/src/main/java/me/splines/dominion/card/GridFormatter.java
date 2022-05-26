package me.splines.dominion.card;

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

        int i = 0;
        int printIndex;
        // Go through all rows
        while (i < cards.size()) {
            Card leftmostCard = cards.get(i);
            printIndex = withIndex ? i + 1 : -1;
            String[] linesLeftmostCard = CardFormatter
                    .getFormattedWithIndex(leftmostCard, printIndex)
                    .split("\n");
            String[] cardRowLines = linesLeftmostCard.clone();

            // Cards to the right in same row
            for (int j = 0; j < GRID_WIDTH - 1; j++) {
                i++;
                if (i >= cards.size())
                    break;

                Card cardToTheRight = cards.get(i);
                printIndex = withIndex ? i + 1 : -1;
                String[] linesCardToTheRight = CardFormatter
                        .getFormattedWithIndex(cardToTheRight, printIndex)
                        .split("\n");
                if (linesCardToTheRight.length != linesLeftmostCard.length)
                    throw new CardsDifferentHeightsException(List.of(leftmostCard, cardToTheRight));

                // Combine lines of one card row
                for (int k = 0; k < cardRowLines.length; k++) {
                    cardRowLines[k] += " ".repeat(GRID_HGAP);
                    cardRowLines[k] += linesCardToTheRight[k];
                }
            }

            i++;

            // Combine all lines of one card row
            String row = String.join("\n", cardRowLines);
            if (i < cards.size())
                row += "\n".repeat(GRID_VGAP + 1);
            grid.append(row);
        }

        return grid.toString();
    }

}
