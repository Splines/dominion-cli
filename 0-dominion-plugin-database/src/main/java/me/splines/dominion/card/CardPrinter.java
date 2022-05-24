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
        // Header

        // ### NAME ### (two spaces around name)
        int spaceLeft = CARD_WIDTH - card.getName().length() - 2;
        if (spaceLeft <= 2) {
            throw new CardNameTooLongException(card.getName());
        }

        int numSymbolsLeft = (int) Math.floor(spaceLeft / 2.0);
        int numSymbolsRight = (int) Math.ceil(spaceLeft / 2.0);

        System.out.print(SYMBOL_TOP_LEFT_CORNER);
        for (int i = 0; i < numSymbolsLeft - 1; i++) {
            System.out.print(SYMBOL_HORIZONTAL_BORDER);
        }
        System.out.print(" " + card.getName().toUpperCase() + " ");
        for (int i = 0; i < numSymbolsRight - 1; i++) {
            System.out.print(SYMBOL_HORIZONTAL_BORDER);
        }
        System.out.print(SYMBOL_TOP_RIGHT_CORNER);
        System.out.println();

        // Body

        printEmptyLineWithBorder();
        printEmptyLineWithBorder();
        printEmptyLineWithBorder();
        printEmptyLineWithBorder();
        printEmptyLineWithBorder();
        printEmptyLineWithBorder();
        printEmptyLineWithBorder();
        printEmptyLineWithBorder();
        printEmptyLineWithBorder();

        // Footer

        System.out.print(SYMBOL_BOTTOM_LEFT_CORNER);
        String moneyStr = card.getCost() + "💰";
        System.out.print(" " + moneyStr + " ");

        String typeStr = card.getType().getName();
        int footerSpacesBothSides = CARD_WIDTH - typeStr.length() - 2;
        int footerSpacesLeft = (int) Math.floor(footerSpacesBothSides / 2.0);
        int footerSpacesRight = (int) Math.ceil(footerSpacesBothSides / 2.0);

        int symbolsToCome = footerSpacesLeft - (1 + 1 + moneyStr.length() + 1);
        for (int i = 0; i < symbolsToCome; i++) {
            System.out.print(SYMBOL_HORIZONTAL_BORDER);
        }
        System.out.print(" " + typeStr + " ");

        for (int i = 0; i < footerSpacesRight - 1; i++) {
            System.out.print(SYMBOL_HORIZONTAL_BORDER);
        }
        System.out.print(SYMBOL_BOTTOM_RIGHT_CORNER);
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
