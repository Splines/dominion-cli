package me.splines.dominion.Game;

public class GameState {

    private final Stock stock;

    public GameState(Stock stock) {
        this.stock = stock;
    }

    public Stock getStock() {
        return stock;
    }

}
