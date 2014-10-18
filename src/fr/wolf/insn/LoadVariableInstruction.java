/*******************************************************************************
* Copyright (c) 2014, Superloup10
*
* This work is made available under the terms of the GNU GENERAL PUBLIC LICENSE:
* http://www.gnu.org/licenses/gpl-3.0.en.html
* Contact the author for use the sources
*
* Cette œuvre est mise à disposition selon les termes de la GNU GENERAL PUBLIC LICENSE:
* http://www.gnu.org/licenses/gpl-3.0.fr.html
* Contacter l'auteur pour utiliser les sources
******************************************************************************/
package fr.wolf.insn;

public class LoadVariableInstruction extends Instruction
{
    private int varIndex;
    
    public LoadVariableInstruction(int index)
    {
        super(OpCodes.VAR_LOAD);
        this.varIndex = index;
    }
    
    public int getVarIndex()
    {
        return varIndex;
    }
    
    public String toString()
    {
        return super.toString() + " " + varIndex;
    }
}