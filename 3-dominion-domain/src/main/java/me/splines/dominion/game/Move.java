package me.splines.dominion.game;

public abstract class Move {

    protected Player player;
    protected Stock stock;
    protected MoveState moveState = new MoveState();

    protected Move(Player player, Stock stock) {
        this.player = player;
        this.stock = stock;
    }

    abstract void doActionPhase();

    abstract void doBuyPhase();

    abstract void doCleanUpPhase();

}
