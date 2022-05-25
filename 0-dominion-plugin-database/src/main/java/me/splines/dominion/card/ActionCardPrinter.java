package me.splines.dominion.card;

import me.splines.dominion.action.Instruction;

public final class ActionCardPrinter {

    private ActionCardPrinter() {
    }

    public static void printBody(ActionCard card) {
        CardPrinter.printEmptyLineWithBorder();

        int lineCounter = 0;

        String linebreak = "\n";
        for (Instruction instruction : card.getAction().getInstructions()) {
            String text = CardPrinterUtil.wrapLine(instruction.getName(), CardPrinter.CARD_WIDTH - 4, linebreak);
            String[] lines = text.split(linebreak);

            for (String line : lines) {
                // Left part
                System.out.print(CardPrinter.SYMBOL_VERTICAL_BORDER);
                System.out.print(" ");

                // Middle part
                int spacesToTheRight = CardPrinter.CARD_WIDTH - 2 - line.length() - 2;
                System.out.print(line);
                for (int j = 0; j < spacesToTheRight; j++) {
                    System.out.print(" ");
                }

                // Right part
                System.out.print(" ");
                System.out.println(CardPrinter.SYMBOL_VERTICAL_BORDER);

                lineCounter++;
            }
        }

        for (int j = 0; j < CardPrinter.CARD_BODY_HEIGHT - lineCounter; j++) {
            CardPrinter.printEmptyLineWithBorder();
        }
    }
}
