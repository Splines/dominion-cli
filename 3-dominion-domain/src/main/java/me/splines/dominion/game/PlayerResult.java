package me.splines.dominion.game;

public class PlayerResult {

    private String name;
    private int points;

    public PlayerResult(String name, int points) {
        this.name = name;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

}
