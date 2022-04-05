package me.splines.dominion.Instruction;

import me.splines.dominion.Game.Move;
import me.splines.dominion.Game.PlayerAbstract;
import me.splines.dominion.Game.PlayerDecision;
import me.splines.dominion.Game.Stock;

public interface Instruction {

    void execute(PlayerAbstract player, Move move, PlayerDecision decision, Stock stock);

}
