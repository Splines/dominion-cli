package me.splines.dominion.game;

public class GameState {

    private final Stock stock;

    public GameState(GameStock stock) {
        this.stock = stock;
    }

    public Stock getStock() {
        return this.stock;
    }

}
