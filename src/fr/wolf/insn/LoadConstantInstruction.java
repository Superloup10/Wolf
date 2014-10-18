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

import fr.wolf.WolfType;
import fr.wolf.WolfValue;

public class LoadConstantInstruction extends Instruction
{
    private WolfValue constant;

    public LoadConstantInstruction(WolfValue constant)
    {
        super(OpCodes.LOAD_CONST);
        this.constant = constant;
    }

    public WolfValue getConstant()
    {
        return constant;
    }
    
    public String toString()
    {
        String constStr = constant.toString();
        if(constant.type == WolfType.stringType)
            constStr = "\"" + constStr + "\"";
        return super.toString() + " " + constStr;
    }
}