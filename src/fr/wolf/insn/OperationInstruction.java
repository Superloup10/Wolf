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

public class OperationInstruction extends Instruction
{
    public static enum Operation
    {
        ADDITION("+"), SUBTRACTION("-"), MULTIPLICATION("*"), DIVISION("/"), MODULO("%"), LEFT_SHIFT("<<"), RIGHT_SHIFT(">>"), UNSIGNED_RIGHT_SHIFT(">>>"), BITWISE_AND("&"), BITWISE_OR("|"), BITWISE_XOR("^");
        
        private String id;
        
        Operation(String id)
        {
            this.id = id;
        }
        
        public String getID()
        {
            return id;
        }
        
        public static Operation get(String id)
        {
            for(Operation v : values())
            {
                if(v.getID().equals(id))
                    return v;
            }
            return null;
        }
    }
    
    private Operation operation;
    
    public OperationInstruction(Operation op)
    {
        super(OpCodes.OPERATION);
        this.operation = op;
    }
    
    public Operation getOperation()
    {
        return operation;
    }
}