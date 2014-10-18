package fr.wolf.insn;

public class VarInstruction extends Instruction
{
    private int varIndex;

    public VarInstruction(int opcode, int varIndex)
    {
        super(opcode);
        this.varIndex = varIndex;
    }
    
    public int getVarIndex()
    {
        return varIndex;
    }
}