package me.splines.dominion.game;

public abstract class Move {

    protected PlayerAbstract player;
    protected Stock stock;
    protected MoveState moveState = new MoveState();

    protected Move(PlayerAbstract player, Stock stock) {
        this.player = player;
        this.stock = stock;
    }

    abstract void doActionPhase();

    abstract void doBuyPhase();

    abstract void doCleanUpPhase();

}
