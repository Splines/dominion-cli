package me.splines.dominion.Action;

import me.splines.dominion.Game.MoveState;
import me.splines.dominion.Game.PlayerAbstract;
import me.splines.dominion.Game.Stock;

public interface Instruction {

    void execute(PlayerAbstract player, MoveState moveState, Stock stock);

}
