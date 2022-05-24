package me.splines.dominion.card;

public final class CardPrinter {

    private static final int CARD_WIDTH = 21;

    private static final String SYMBOL_TOP_LEFT_CORNER = "┏";
    private static final String SYMBOL_TOP_RIGHT_CORNER = "┓";
    private static final String SYMBOL_BOTTOM_LEFT_CORNER = "┗";
    private static final String SYMBOL_BOTTOM_RIGHT_CORNER = "┛";
    private static final String SYMBOL_VERTICAL_BORDER = "┃";
    private static final String SYMBOL_HORIZONTAL_BORDER = "━";

    public static class CardNameTooLongException extends RuntimeException {
        public CardNameTooLongException(String name) {
            super("The card name " + name + " is too long");
        }
    }

    public static void printActionCard(Card card) {
        printHeader(card);
        printBody(card);
        printFooter(card);
    }

    private static void printHeader(Card card) {
        // Calculate spaces
        int spaceLeft = CARD_WIDTH - card.getName().length() - 2;
        if (spaceLeft <= 2) {
            throw new CardNameTooLongException(card.getName());
        }
        int spacesLeft = (int) Math.floor(spaceLeft / 2.0);
        int spacesRight = (int) Math.ceil(spaceLeft / 2.0);

        // Symbols to the left of card name
        System.out.print(SYMBOL_TOP_LEFT_CORNER);
        for (int i = 0; i < spacesLeft - 1; i++) {
            System.out.print(SYMBOL_HORIZONTAL_BORDER);
        }

        // Card name
        System.out.print(" " + card.getName().toUpperCase() + " ");

        // Symbols to the right of card name
        for (int i = 0; i < spacesRight - 1; i++) {
            System.out.print(SYMBOL_HORIZONTAL_BORDER);
        }
        System.out.print(SYMBOL_TOP_RIGHT_CORNER);

        System.out.println();
    }

    private static void printBody(Card card) {
        for (int i = 0; i < 10; i++) {
            printEmptyLineWithBorder();
        }
    }

    private static void printFooter(Card card) {
        System.out.print(SYMBOL_BOTTOM_LEFT_CORNER);

        // Card costs
        String moneyStr = card.getCost() + "💰";
        System.out.print(" " + moneyStr + " ");

        // Calculate spaces
        String typeName = card.getType().getName();
        int spacesBothSides = CARD_WIDTH - typeName.length() - 2;
        int spacesToTheLeft = (int) Math.floor(spacesBothSides / 2.0);
        int spacesToTheRight = (int) Math.ceil(spacesBothSides / 2.0);

        // Spaces from symbols so far until card type text
        int spacesSoFar = 1 + 1 + moneyStr.length() + 1;
        for (int i = 0; i < spacesToTheLeft - spacesSoFar; i++) {
            System.out.print(SYMBOL_HORIZONTAL_BORDER);
        }

        // Card type
        System.out.print(" " + typeName + " ");

        // Symbols to the right of card type
        for (int i = 0; i < spacesToTheRight - 1; i++) {
            System.out.print(SYMBOL_HORIZONTAL_BORDER);
        }
        System.out.print(SYMBOL_BOTTOM_RIGHT_CORNER);

        System.out.println();
    }

    private static void printEmptyLineWithBorder() {
        System.out.print(SYMBOL_VERTICAL_BORDER);
        for (int i = 0; i < CARD_WIDTH - 2; i++) {
            System.out.print(" ");
        }
        System.out.print(SYMBOL_VERTICAL_BORDER);
        System.out.println();
    }

}
