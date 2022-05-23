package me.splines.dominion.Game;

public abstract class Move {

    protected PlayerAbstract player;
    protected Stock stock;
    protected MoveState moveState = new MoveState();

    public Move(PlayerAbstract player, Stock stock) {
        this.player = player;
        this.stock = stock;
    }

    abstract void doActionPhase();

    abstract void doBuyPhase();

    abstract void doCleanUpPhase();

}
