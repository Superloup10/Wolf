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
package fr.wolf;

import fr.wolf.insn.Instruction;
import fr.wolf.insn.LoadConstantInstruction;
import fr.wolf.insn.LoadVariableInstruction;
import fr.wolf.insn.MethodCallInstruction;
import fr.wolf.insn.MethodStartInstruction;
import fr.wolf.insn.OpCodes;
import fr.wolf.insn.OperationInstruction;
import fr.wolf.insn.OperationInstruction.Operation;
import fr.wolf.insn.StoreVariableInstruction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class WolfInterpreter implements OpCodes
{
    private ArrayList<WolfLib> included;
    private int line;
    
    public void run(ArrayList<WolfLib> included, ArrayList<Instruction> instructions) throws WolfException
    {
        this.included = included;
        System.out.println("exit code: " + invokeMethod(instructions, "__MAIN__", "main", "int()", new Stack<>()));
    }

    private WolfValue invokeMethod(ArrayList<Instruction> insns, String owner, String methodName, String methodDesc, Stack<WolfValue> stack) throws WolfException
    {
        boolean execute = false;
        boolean invoked = false;
        boolean hasReturn = false;
        HashMap<Integer, WolfVariable> varMap = new HashMap<>();
        for(WolfLib lib : included)
        {
            if(lib.getName().equals("__MAIN__"))
                continue;
            if(lib.getName().equals(owner))
            {
                for(WolfMethod method : lib.getMethods())
                {
                    if(method.getName().equals(methodName) && WolfHelper.areDescEquals(methodDesc, method.getDesc().toString()))
                    {
                        int n = WolfHelper.countChar(method.getDesc().toString(), ';');
                        WolfValue[] args = new WolfValue[0];
                        if(n > 0)
                        {
                            args = new WolfValue[n];
                            for(int i = n - 1; i >= 0; i--)
                            {
                                args[i] = stack.pop();
                            }
                        }
                        WolfValue returned = method.invoke(args);
                        invoked = true;
                        return returned;
                    }
                }
            }
        }
        for(Instruction insn : insns)
        {
            if(insn instanceof MethodStartInstruction)
            {
                System.out.println("StartInstruction");
                MethodStartInstruction start = (MethodStartInstruction)insn;
                if(start.getMethod().getName().equals(methodName) && WolfHelper.areDescEquals(start.getMethod().getDesc().toString(), methodDesc))
                {
                    System.out.println("A une methode");
                    execute = true;
                    invoked = true;
                    varMap.clear();
                    int index = 0;
                    for(WolfVariable v : start.getMethod().getLocals())
                    {
                        varMap.put(index++, v);
                    }
                    int n = WolfHelper.countChar(methodDesc, ';');
                    System.out.println("N:" + n);
                    if(n > 0)
                    {
                        for(int i = n - 1; i >= 0; i--)
                        {
                            WolfValue val = stack.pop();
                            WolfVariable var = new WolfVariable(i, start.getMethod().getLocals().get(i).name, val.type, true, false, val.value);
                            varMap.put(i, var);
                        }
                    }
                }
                else if(insn.getOpcode() == METHOD_END && execute)
                {
                    System.out.println("Est terminer");
                    execute = false;
                    if(invoked)
                        break;
                }
                else if(execute)
                {
                    System.out.println("Est Appeler");
                    execute(insns, insn, stack, varMap);
                    if(insn.getOpcode() == RETURN)
                        hasReturn = true;
                }
            }
        }
        WolfValue returnedValue = null;
        if(!invoked)
        {
            System.out.println("Pas Appeler");
            WolfHelper.throwError("No found method : " + methodName + " " + methodDesc, line);
            hasReturn = false;
        }
        if(hasReturn)
        {
            System.out.println("A un retour");
            returnedValue = stack.pop();
        }
        return returnedValue;
    }

    private void execute(ArrayList<Instruction> insns, Instruction insn, Stack<WolfValue> stack, HashMap<Integer, WolfVariable> varMap) throws WolfException
    {
        if(insn.getOpcode() == LOAD_CONST)
        {
            WolfValue constant = ((LoadConstantInstruction)insn).getConstant();
            stack.push(constant);
        }
        else if(insn.getOpcode() == METHOD_CALL)
        {
            MethodCallInstruction callInsn = (MethodCallInstruction)insn;
            WolfValue returned = invokeMethod(insns, callInsn.getMethod().getOwner(), callInsn.getMethod().getName(), callInsn.getMethod().getDesc().toString(), stack);
            if(returned != null)
                stack.push(returned);
        }
        else if(insn.getOpcode() == RETURN)
        {
            WolfValue returned = stack.pop();
            stack.push(returned);
        }
        else if(insn.getOpcode() == OPERATION)
        {
            WolfValue b = stack.pop();
            WolfValue a = stack.pop();
            Operation op = ((OperationInstruction)insn).getOperation();
            if(op == Operation.ADDITION)
            {
                stack.push(a.type.add(a, b));
            }
            else if(op == Operation.SUBTRACTION)
            {
                stack.push(a.type.sub(a, b));
            }
            else if(op == Operation.MULTIPLICATION)
            {
                stack.push(a.type.mul(a, b));
            }
            else if(op == Operation.DIVISION)
            {
                stack.push(a.type.div(a, b));
            }
            else if(op == Operation.MODULO)
            {
                stack.push(a.type.mod(a, b));
            }
            else if(op == Operation.LEFT_SHIFT)
            {
                stack.push(a.type.lsh(a, b));
            }
            else if(op == Operation.RIGHT_SHIFT)
            {
                stack.push(a.type.rsh(a, b));
            }
            else if(op == Operation.UNSIGNED_RIGHT_SHIFT)
            {
                stack.push(a.type.ursh(a, b));
            }
            else if(op == Operation.BITWISE_AND)
            {
                stack.push(a.type.bitAnd(a, b));
            }
            else if(op == Operation.BITWISE_OR)
            {
                stack.push(a.type.bitOr(a, b));
            }
            else if(op == Operation.BITWISE_XOR)
            {
                stack.push(a.type.bitXor(a, b));
            }
        }
        else if(insn.getOpcode() == VAR_LOAD)
        {
            WolfVariable var = varMap.get(((LoadVariableInstruction)insn).getVarIndex());
            stack.push(var);
        }
        else if(insn.getOpcode() == VAR_STORE)
        {
            WolfVariable var = varMap.get(((StoreVariableInstruction)insn).getVarIndex());
            WolfValue val = stack.pop();
            if(!val.type.isCompatible(val.type))
            {
                WolfHelper.throwError("Cannot cast " + val.type.getID() + " to " + var.type.getID(), line);
            }
            var.value = var.value;
            varMap.put(var.index, var);
        }
    }
}