package me.splines.dominion.card;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import me.splines.dominion.action.Action;
import me.splines.dominion.instruction.DiscardAndDrawCardsInstruction;
import me.splines.dominion.instruction.DisposeMoneyCardTakeMoneyCardToHandInstruction;
import me.splines.dominion.instruction.DrawCardsInstruction;
import me.splines.dominion.instruction.EarnActionsInstruction;
import me.splines.dominion.instruction.EarnBuyingsInstruction;
import me.splines.dominion.instruction.EarnMoneyInstruction;

public final class CardPool {

    private CardPool() {
        throw new IllegalStateException("CardPool is a utility class serving as a pool");
    }

    public static final List<ActionCard> actionCards = List.of(

            new ActionCardBuilder("Jahrmarkt", 5).with(
                    new Action(
                            new EarnActionsInstruction(2),
                            new EarnBuyingsInstruction(1),
                            new EarnMoneyInstruction(2)))
                    .build(),

            new ActionCardBuilder("Markt", 5).with(
                    new Action(
                            new DrawCardsInstruction(1),
                            new EarnActionsInstruction(1),
                            new EarnBuyingsInstruction(1),
                            new EarnMoneyInstruction(1)))
                    .build(),

            new ActionCardBuilder("Keller", 2).with(
                    new Action(
                            new EarnActionsInstruction(1),
                            new DiscardAndDrawCardsInstruction()))
                    .build(),

            new ActionCardBuilder("Schmiede", 4).with(
                    new Action(
                            new DrawCardsInstruction(3)))
                    .build(),

            new ActionCardBuilder("Laboratorium", 5).with(
                    new Action(
                            new DrawCardsInstruction(2),
                            new EarnActionsInstruction(1)))
                    .build(),

            new ActionCardBuilder("Mine", 5).with(
                    new Action(
                            new DisposeMoneyCardTakeMoneyCardToHandInstruction()))
                    .build()

    );

    // Money cards
    public static final MoneyCard copperCard = new MoneyCard("Kupfer", 0, 1);
    public static final MoneyCard silverCard = new MoneyCard("Silber", 3, 2);
    public static final MoneyCard goldCard = new MoneyCard("Gold", 6, 3);
    public static final List<MoneyCard> moneyCards = List.of(copperCard, silverCard, goldCard);

    // Point cards
    public static final PointCard provinceCard = new PointCard("Provinz", 8, 6);
    public static final PointCard duchyCard = new PointCard("Herzogtum", 5, 3);
    public static final PointCard estateCard = new PointCard("Anwesen", 2, 1);

    // Curse cards
    public static final CurseCard curseCard = new CurseCard("Fluch", 0, -1);

    // All cards
    public static final List<Card> allCards = Stream.of(
            List.of(copperCard, silverCard, goldCard, provinceCard, duchyCard,
                    estateCard, curseCard),
            actionCards)
            .flatMap(Collection::stream)
            .collect(Collectors.toList());

}
