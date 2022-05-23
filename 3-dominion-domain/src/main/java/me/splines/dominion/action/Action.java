package me.splines.dominion.action;

import java.util.List;

public final class Action {

    private final List<Instruction> instructions;

    public Action(Instruction... instructions) {
        this.instructions = List.of(instructions);
    }

    public List<Instruction> getInstructions() {
        return this.instructions;
    }

}
