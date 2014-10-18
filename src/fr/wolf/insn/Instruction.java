/*******************************************************************************
* Copyright (c) 2014, Superloup10
*
* This work is made available under the terms of the GNU GENERAL PUBLIC LICENSE:
* http://www.gnu.org/licenses/gpl-3.0.en.html
* Contact the author for use the source
*
* Cette œuvre est mise à disposition selon les termes de la GNU GENERAL PUBLIC LICENSE:
* http://www.gnu.org/licenses/gpl-3.0.fr.html
* Contacter l'auteur pour utiliser les sources
******************************************************************************/
package fr.wolf.insn;

public abstract class Instruction implements OpCodes
{
    private int opcode;

    public Instruction(int opcode)
    {
        this.opcode = opcode;
    }
    
    public String toString()
    {
        return OpCodes.toString(opcode);
    }
    
    public int getOpcode()
    {
        return opcode;
    }
}