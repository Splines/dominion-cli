package me.splines.dominion.card;

import java.util.Map;
import java.util.function.Function;

import me.splines.dominion.card.FormatExceptions.CardNameTooLongException;
import me.splines.dominion.card.body.ActionCardFormatter;
import me.splines.dominion.card.body.MoneyCardFormatter;
import me.splines.dominion.card.body.PointCardFormatter;

public final class CardFormatter {

    public static final int CARD_WIDTH = 22;
    public static final int CARD_BODY_HEIGHT = 10;

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

    public static String getFormatted(Card card) {
        return getFormattedWithIndex(card, -1);
    }

    public static String getFormattedWithIndex(Card card, int index) {
        StringBuilder str = new StringBuilder();

        // Header
        str.append(getHeader(card, index));

        // Body
        // will fail if class not in map, which is intended
        Function<Card, String> formatFunction = formatterMap.get(card.getClass());
        str.append(formatFunction.apply(card));

        // Footer
        str.append(getFooter(card));

        return str.toString();
    }

    private static String getHeader(Card card, int index) {
        StringBuilder header = new StringBuilder();

        // Calculate Card name
        String name = card.getName().toUpperCase();
        if (index != -1) {
            name = index + ":" + name;
        }
        String headerText = " " + name + " ";

        // Calculate spaces
        int spacesBothSides = CARD_WIDTH - headerText.length();
        if (spacesBothSides <= 2) {
            throw new CardNameTooLongException(card.getName());
        }
        Spaces spaces = getRemainingSpaces(spacesBothSides);

        // Symbols to the left of card name
        header.append(SYMBOL_TOP_LEFT_CORNER);
        for (int i = 0; i < spaces.spacesToTheLeft - 1; i++) {
            header.append(SYMBOL_HORIZONTAL_BORDER);
        }

        // Card name
        header.append(headerText);

        // Symbols to the right of card name
        for (int i = 0; i < spaces.spacesToTheRight - 1; i++) {
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
        Spaces spaces = getRemainingSpaces(spacesBothSides);

        // Spaces from symbols so far until card type text
        int spacesSoFar = 1 + 1 + moneyStr.length() + 1;
        for (int i = 0; i < spaces.spacesToTheLeft - spacesSoFar; i++) {
            footer.append(SYMBOL_HORIZONTAL_BORDER);
        }

        // Card type
        footer.append(" " + typeName + " ");

        // Symbols to the right of card type
        for (int i = 0; i < spaces.spacesToTheRight - 1; i++) {
            footer.append(SYMBOL_HORIZONTAL_BORDER);
        }
        footer.append(SYMBOL_BOTTOM_RIGHT_CORNER);
        footer.append("\n");

        return footer.toString();
    }

    private static record Spaces(int spacesToTheLeft, int spacesToTheRight) {

    };

    private static Spaces getRemainingSpaces(int spacesRemainingOnBothSides) {
        int spacesToTheLeft = (int) Math.floor(spacesRemainingOnBothSides / 2.0);
        int spacesToTheRight = (int) Math.ceil(spacesRemainingOnBothSides / 2.0);
        return new Spaces(spacesToTheLeft, spacesToTheRight);
    }

}
