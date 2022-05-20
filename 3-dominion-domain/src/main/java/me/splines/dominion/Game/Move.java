package me.splines.dominion.Game;

public interface Move {

    void doActionPhase(PlayerAbstract player);

    void doBuyPhase(PlayerAbstract player);

    void doCleanUpPhase(PlayerAbstract player);

}
