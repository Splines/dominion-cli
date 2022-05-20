package me.splines.dominion.Instruction;

import me.splines.dominion.Game.MoveState;
import me.splines.dominion.Game.PlayerAbstract;
import me.splines.dominion.Game.StockAbstract;

public interface Instruction {

    void execute(PlayerAbstract player, MoveState moveState, StockAbstract stock);

}
