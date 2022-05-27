package me.splines.dominion.action;

import me.splines.dominion.game.MoveState;
import me.splines.dominion.game.Player;
import me.splines.dominion.game.Stock;

public interface Instruction {

    void execute(Player player, MoveState moveState, Stock stock);

    String getName();

}
