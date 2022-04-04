package me.splines.dominion.Instruction;

import java.util.List;

public final class Action {

    private final List<Instruction> instructions;

    public Action(Instruction... instructions) {
        this.instructions = List.of(instructions);
    }

    public List<Instruction> getInstruction() {
        return this.instructions;
    }

}
