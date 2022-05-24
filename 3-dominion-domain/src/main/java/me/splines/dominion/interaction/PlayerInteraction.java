package me.splines.dominion.interaction;

public final class PlayerInteraction {

    private PlayerDecision decision;
    private PlayerInformation information;

    public PlayerInteraction(PlayerDecision decision, PlayerInformation information) {
        this.decision = decision;
        this.information = information;
    }

    public PlayerDecision decision() {
        return decision;
    }

    public PlayerInformation information() {
        return information;
    }

}
