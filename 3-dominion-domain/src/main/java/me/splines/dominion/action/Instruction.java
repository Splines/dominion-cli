package me.splines.dominion.action;

import me.splines.dominion.game.MoveState;
import me.splines.dominion.game.PlayerAbstract;
import me.splines.dominion.game.Stock;

public interface Instruction {

    void execute(PlayerAbstract player, MoveState moveState, Stock stock);

    String getName();

}
