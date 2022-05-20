package me.splines.dominion.Game;

public interface MoveInterface {

    void doActionPhase(PlayerAbstract player);

    void doBuyPhase(PlayerAbstract player);

    void doCleanUpPhase(PlayerAbstract player);

}
