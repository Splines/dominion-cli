package me.splines.dominion.card;

public class PointCardPrinter {

    public static void printBody(PointCard card) {
        CardPrinter.printEmptyLineWithBorder();

        CardPrinterUtil.printToTheRight("📀📀📀");
        // Assumption: card money is just one digit
        CardPrinterUtil.printToTheRight("📀" + card.getPoints() + " 📀");
        CardPrinterUtil.printToTheRight("📀📀📀");

        for (int j = 0; j < CardPrinter.CARD_BODY_HEIGHT - 3; j++) {
            CardPrinter.printEmptyLineWithBorder();
        }
    }

}
