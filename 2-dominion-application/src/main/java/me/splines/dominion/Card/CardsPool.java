package me.splines.dominion.Card;

import java.util.List;

import me.splines.dominion.Instruction.Action;
import me.splines.dominion.Instruction.DiscardAndDrawCardsInstruction;
import me.splines.dominion.Instruction.DisposeHandCardTakeMoneyCardInstruction;
import me.splines.dominion.Instruction.DrawCardsInstruction;
import me.splines.dominion.Instruction.EarnActionsInstruction;
import me.splines.dominion.Instruction.EarnBuyingsInstruction;
import me.splines.dominion.Instruction.EarnMoneyInstruction;

public final class CardsPool {

	private final List<Card> cards = List.of(

			// TODO: Maybe simplify the construction of action cards with a factory

			new ActionCard("Jahrmarkt", CardType.ACTION, 5, new Action(
					new EarnActionsInstruction(2),
					new EarnBuyingsInstruction(1),
					new EarnMoneyInstruction(2))),

			new ActionCard("Markt", CardType.ACTION, 2, new Action(
					new DrawCardsInstruction(1),
					new EarnActionsInstruction(1),
					new EarnBuyingsInstruction(1),
					new EarnMoneyInstruction(1))),

			new ActionCard("Keller", CardType.ACTION, 2, new Action(
					new EarnActionsInstruction(1),
					new DiscardAndDrawCardsInstruction())),

			new ActionCard("Schmiede", CardType.ACTION, 4, new Action(new DrawCardsInstruction(3))),

			new ActionCard("Laboratorium", CardType.ACTION, 5, new Action(
					new DrawCardsInstruction(2),
					new EarnActionsInstruction(1))),

			new ActionCard("Mine", CardType.ACTION, 5, new Action(
					new DisposeHandCardTakeMoneyCardInstruction()))

	);

	public CardsPool() {
	}

}
