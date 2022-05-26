package me.splines.dominion.card;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import me.splines.dominion.card.body.ActionCardFormatter;
import me.splines.dominion.card.body.MoneyCardFormatter;
import me.splines.dominion.card.body.PointCardFormatter;

public final class CardFormatter {

    public static final int CARD_WIDTH = 22;
    public static final int CARD_BODY_HEIGHT = 10;

    public static final int GRID_WIDTH = 3; // 3 cards in one row
    public static final int GRID_HGAP = 4; // number of spacings between cards in one row
    public static final int GRID_VGAP = 2; // number of spacings between cards in one column

    public static final String SYMBOL_TOP_LEFT_CORNER = "┏";
    public static final String SYMBOL_TOP_RIGHT_CORNER = "┓";
    public static final String SYMBOL_BOTTOM_LEFT_CORNER = "┗";
    public static final String SYMBOL_BOTTOM_RIGHT_CORNER = "┛";
    public static final String SYMBOL_VERTICAL_BORDER = "┃";
    public static final String SYMBOL_HORIZONTAL_BORDER = "━";

    private static final Map<Class<? extends Card>, Function<Card, String>> formatterMap = Map.of(
            ActionCard.class, c -> new ActionCardFormatter().getBody((ActionCard) c),
            MoneyCard.class, c -> new MoneyCardFormatter().getBody((MoneyCard) c),
            PointCard.class, c -> new PointCardFormatter().getBody((PointCard) c));

    private CardFormatter() {
    }

    public static class CardNameTooLongException extends RuntimeException {
        public CardNameTooLongException(String name) {
            super("The card name " + name + " is too long");
        }
    }

    public static class CardsDifferentHeightsException extends RuntimeException {
        public CardsDifferentHeightsException(List<Card> cards) {
            super("The cards have different lengths, cannot print them in a grid: "
                    + String.join(",",
                            cards.stream().map(c -> c.getName()).toList()));
        }
    }

    public static String getFormattedGrid(List<Card> cards) {
        StringBuilder grid = new StringBuilder();

        int i = 0;
        // Go through all rows
        while (i < cards.size()) {
            Card leftmostCard = cards.get(i);
            String[] linesLeftmostCard = getFormatted(leftmostCard)
                    .split("\n");
            String[] cardRowLines = linesLeftmostCard.clone();

            // Cards to the right in same row
            for (int j = 0; j < GRID_WIDTH - 1; j++) {
                i++;
                if (i >= cards.size())
                    break;

                Card cardToTheRight = cards.get(i);
                String[] linesCardToTheRight = getFormatted(cardToTheRight)
                        .split("\n");
                if (linesCardToTheRight.length != linesLeftmostCard.length)
                    throw new CardsDifferentHeightsException(List.of(leftmostCard, cardToTheRight));

                // Combine lines of one card row
                for (int k = 0; k < cardRowLines.length; k++) {
                    cardRowLines[k] += " ".repeat(GRID_HGAP);
                    cardRowLines[k] += linesCardToTheRight[k];
                }
            }

            // Combine all lines of one card row
            String row = String.join("\n", cardRowLines);
            row += "\n".repeat(GRID_VGAP + 1);
            grid.append(row);

            i++;
        }

        return grid.toString();
    }

    public static String getFormatted(Card card) {
        StringBuilder str = new StringBuilder();

        // Header
        str.append(getHeader(card));

        // Body
        // will fail if class not in map, which is intended
        Function<Card, String> formatFunction = formatterMap.get(card.getClass());
        str.append(formatFunction.apply(card));

        // Footer
        str.append(getFooter(card));

        return str.toString();
    }

    private static String getHeader(Card card) {
        StringBuilder header = new StringBuilder();

        // Calculate spaces
        int spaceLeft = CARD_WIDTH - card.getName().length() - 2;
        if (spaceLeft <= 2) {
            throw new CardNameTooLongException(card.getName());
        }
        int spacesLeft = (int) Math.floor(spaceLeft / 2.0);
        int spacesRight = (int) Math.ceil(spaceLeft / 2.0);

        // Symbols to the left of card name
        header.append(SYMBOL_TOP_LEFT_CORNER);
        for (int i = 0; i < spacesLeft - 1; i++) {
            header.append(SYMBOL_HORIZONTAL_BORDER);
        }

        // Card name
        header.append(" " + card.getName().toUpperCase() + " ");

        // Symbols to the right of card name
        for (int i = 0; i < spacesRight - 1; i++) {
            header.append(SYMBOL_HORIZONTAL_BORDER);
        }
        header.append(SYMBOL_TOP_RIGHT_CORNER);
        header.append("\n");

        return header.toString();
    }

    private static String getFooter(Card card) {
        StringBuilder footer = new StringBuilder();

        footer.append(SYMBOL_BOTTOM_LEFT_CORNER);

        // Card costs
        String moneyStr = card.getCost() + "💰";
        footer.append(" " + moneyStr + " ");

        // Calculate spaces
        String typeName = card.getType().getName();
        int spacesBothSides = CARD_WIDTH - typeName.length() - 2;
        int spacesToTheLeft = (int) Math.floor(spacesBothSides / 2.0);
        int spacesToTheRight = (int) Math.ceil(spacesBothSides / 2.0);

        // Spaces from symbols so far until card type text
        int spacesSoFar = 1 + 1 + moneyStr.length() + 1;
        for (int i = 0; i < spacesToTheLeft - spacesSoFar; i++) {
            footer.append(SYMBOL_HORIZONTAL_BORDER);
        }

        // Card type
        footer.append(" " + typeName + " ");

        // Symbols to the right of card type
        for (int i = 0; i < spacesToTheRight - 1; i++) {
            footer.append(SYMBOL_HORIZONTAL_BORDER);
        }
        footer.append(SYMBOL_BOTTOM_RIGHT_CORNER);
        footer.append("\n");

        return footer.toString();
    }

}
