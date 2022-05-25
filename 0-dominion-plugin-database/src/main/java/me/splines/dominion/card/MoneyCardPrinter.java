package me.splines.dominion.card;

public class MoneyCardPrinter {

    public static void printBody(MoneyCard card) {
        CardPrinter.printEmptyLineWithBorder();

        printToTheRight("💰💰💰");
        // Assumption: card money is just one digit
        printToTheRight("💰" + card.getMoney() + " 💰");
        printToTheRight("💰💰💰");

        for (int j = 0; j < CardPrinter.CARD_BODY_HEIGHT - 3; j++) {
            CardPrinter.printEmptyLineWithBorder();
        }
    }

    private static void printToTheRight(String text) {
        CardPrinter.printLeftBorderWithSpace();
        CardPrinterUtil.printEmptySpaces(CardPrinter.CARD_WIDTH - 4 - 4 - 2);
        System.out.print(text);
        CardPrinter.printRightBorderWithSpace();
        System.out.println();
    }

}
