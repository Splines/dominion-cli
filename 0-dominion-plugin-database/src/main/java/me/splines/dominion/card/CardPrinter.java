package me.splines.dominion.card;

import me.splines.dominion.action.Instruction;

public final class CardPrinter {

    private static final int CARD_WIDTH = 21;
    private static final int CARD_BODY_HEIGHT = 10;

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

    public static void printCard(Card card) {
        printHeader(card);

        if (card instanceof ActionCard) {
            printActionCardBody((ActionCard) card);
        } else {
            printDefaultCardBody();
        }

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

    private static void printActionCardBody(ActionCard card) {
        printEmptyLineWithBorder();

        int lineCounter = 0;

        String linebreak = "\n";
        for (Instruction instruction : card.getAction().getInstructions()) {
            String text = wrapLine(instruction.getName(), CARD_WIDTH - 4, linebreak);
            String[] lines = text.split(linebreak);

            for (String line : lines) {
                // Left part
                System.out.print(SYMBOL_VERTICAL_BORDER);
                System.out.print(" ");

                // Middle part
                int spacesToTheRight = CARD_WIDTH - 2 - line.length() - 2;
                System.out.print(line);
                for (int j = 0; j < spacesToTheRight; j++) {
                    System.out.print(" ");
                }

                // Right part
                System.out.print(" ");
                System.out.println(SYMBOL_VERTICAL_BORDER);

                lineCounter++;
            }
        }

        // Fill with empty lines until card body height reached
        for (int j = 0; j < CARD_BODY_HEIGHT - lineCounter; j++) {
            printEmptyLineWithBorder();
        }
    }

    private static void printDefaultCardBody() {
        for (int i = 0; i < CARD_BODY_HEIGHT; i++) {
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

    private static String wrapLine(String line, int lineLength, String linebreak) {
        // inspired by https://stackoverflow.com/a/45614206
        if (line.length() == 0)
            return "";
        if (line.length() <= lineLength)
            return line;

        String[] words = line.split(" ");
        StringBuilder allLines = new StringBuilder();
        StringBuilder trimmedLine = new StringBuilder();

        for (String word : words) {
            if (trimmedLine.length() + 1 + word.length() <= lineLength) {
                trimmedLine.append(word).append(" ");
            } else {
                // remove last whitespace
                trimmedLine.setLength(trimmedLine.length() - 1);
                allLines.append(trimmedLine).append(linebreak);
                trimmedLine = new StringBuilder();
                trimmedLine.append(word).append(" ");
            }
        }

        if (trimmedLine.length() > 0) {
            allLines.append(trimmedLine);
        }

        return allLines.toString();
    }

}
