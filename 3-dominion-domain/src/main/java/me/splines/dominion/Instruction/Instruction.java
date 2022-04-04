package me.splines.dominion.Instruction;

import me.splines.dominion.Game.Move;
import me.splines.dominion.Game.PlayerAbstract;

public interface Instruction {

    void execute(PlayerAbstract player, Move move);

}
