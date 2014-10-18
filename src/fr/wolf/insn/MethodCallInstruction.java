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

import fr.wolf.WolfMethod;

public class MethodCallInstruction extends Instruction
{
    private WolfMethod method;
    public MethodCallInstruction(WolfMethod method)
    {
        super(OpCodes.METHOD_CALL);
        this.method = method;
    }

    public WolfMethod getMethod()
    {
        return method;
    }
    
    public String toString()
    {
        return super.toString() + " " + method.getOwner() + "." + method.getName() + " " + method.getDesc();
    }
}