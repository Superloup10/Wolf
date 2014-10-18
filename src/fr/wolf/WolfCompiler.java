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

import fr.wolf.WolfMethod.MethodDesc;
import fr.wolf.insn.BaseInstruction;
import fr.wolf.insn.Instruction;
import fr.wolf.insn.LoadConstantInstruction;
import fr.wolf.insn.LoadVariableInstruction;
import fr.wolf.insn.MethodCallInstruction;
import fr.wolf.insn.MethodStartInstruction;
import fr.wolf.insn.OpCodes;
import fr.wolf.insn.OperationInstruction;
import fr.wolf.insn.OperationInstruction.Operation;
import fr.wolf.insn.StoreVariableInstruction;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Stack;

public class WolfCompiler implements OpCodes
{
    public enum BlockType
    {
        NONE, METHOD_DEFINITION
    }
    
    private ArrayList<WolfLib> included;
    
    public WolfCompiler()
    {
        included = new ArrayList<>();
        included.add(new StdLib());
    }
    
    private float sourceLine = 1;
    private WolfMethod compilingMethod = null;
    
    public void compileAndRun(String prototypes, String implementation) throws WolfException
    {
        prototypes = prototypes.replace("\r", "\n");
        implementation = implementation.replace("\r", "\n");
        included.add(parseLib(prototypes, "__MAIN__"));
        char[] chars = implementation.toCharArray();
        StringBuffer buffer = new StringBuffer();
        ArrayList<Instruction> instructions = new ArrayList<>();
        BlockType blockType = BlockType.NONE;
        String methodReturnType = "void";
        String methodName = null;
        ArrayList<String> methodArgsNames = new ArrayList<>();
        ArrayList<String> methodArgsTypes = new ArrayList<>();
        
        for(int i = 0; i < chars.length; i++)
        {
            char current = chars[i];
            if(current == '\t')
                continue;
            if(current == '\n' || current == '\r')
            {
                continue;
            }
            else if(current == '>')
            {
                String s = buffer.toString();
                if(s.contains("import"))
                {
                    String s2 = s.substring(s.indexOf("import") + "import".length());
                    if(s.contains("<"))
                    {
                        String s1 = s2.substring(s2.indexOf("<") + 1, s2.length()).replace(" ", "");
                        if(included.stream().filter(lib -> lib.getName().equals(s2)).count() == 0)
                        {
                            if(!includeStandart(s1))
                            {
                                String requestedHeader = read("/" + s1 + "." + WolfHelper.HEADER_EXTENSION);
                                included.add(parseLib(requestedHeader, s1));
                                String requestedLib = read("/" + s1 + "." + WolfHelper.IMPL_EXTENSION);
                                int end = i - s.length() - 1;
                                if(end < 0)
                                    end = 0;
                                implementation = implementation.substring(0, end).replaceFirst(s1, "") + requestedLib + implementation.substring(i + 1);
                                chars = implementation.toCharArray();
                            }
                            else
                            {
                                int end = i - s.length() - s.indexOf("import") - 1;
                                if(end < 0)
                                    end = 0;
                                implementation = implementation.substring(0, end).replaceFirst(s1, "") + implementation.substring(i + 1);
                                chars = implementation.toCharArray();
                                i = i - s.length();
                            }
                        }
                        buffer.delete(0, buffer.length());
                    }
                }
            }
            else
            {
                buffer.append(current);
            }
        }
        buffer.delete(0, buffer.length());
        for(int i = 0; i < chars.length; i++)
        {
            char current = chars[i];
            if(current == '\t')
                continue;
            if(blockType == BlockType.METHOD_DEFINITION)
            {
                if(current == ' ')
                {
                    buffer.append(current);
                }
                else if(current == '\n')
                {
                    sourceLine += 0.5f;
                }
                else if(current == ';')
                {
                    endOfInsn(instructions, buffer.toString());
                    buffer.delete(0, buffer.length());
                }
                else if(current == '}')
                {
                    instructions.add(new BaseInstruction(METHOD_END));
                    blockType = BlockType.NONE;
                }
                else
                {
                    buffer.append(current);
                }
            }
            else if(blockType == BlockType.NONE)
            {
                if(current == '\n' || current == '\r')
                {
                    sourceLine++;
                    continue;
                }
                else if(current == ' ')
                {
                    if(buffer.length() > 0 && !buffer.toString().contains("import"))
                    {
                        methodReturnType = buffer.toString();
                        buffer.delete(0, buffer.length());
                    }
                }
                else if(current == '(')
                {
                    methodName = buffer.toString();
                    methodArgsNames.clear();
                    methodArgsTypes.clear();
                    String argsStr = implementation.substring(i + 1, implementation.indexOf(")", i + 1));
                    String[] tokens = null;
                    
                    if(argsStr.contains(","))
                    {
                        tokens = argsStr.split(",");
                    }
                    else
                    {
                        tokens = new String[]{ argsStr };
                    }
                    String methodDesc = "";
                    int varIndex = 0;
                    ArrayList<WolfVariable> varList = new ArrayList<>();
                    for(String token : tokens)
                    {
                        if(token.length() > 0)
                        {
                            String[] split = token.split(" ");
                            String type = split[0];
                            String name = split[split.length - 1];
                            methodArgsNames.add(name);
                            methodArgsTypes.add(type);
                            methodDesc += type + ";";
                            varList.add(new WolfVariable(varIndex, name, WolfType.get(type), true, false, null));
                            varIndex++;
                        }
                    }
                    blockType = BlockType.METHOD_DEFINITION;
                    i = implementation.indexOf("{", i + 1) + 1;
                    WolfMethod method = new WolfMethod("__MAIN__", methodName, methodReturnType + "(" + methodDesc + ")");
                    method.addLocals(varList);
                    MethodStartInstruction  insn = new MethodStartInstruction(method);
                    instructions.add(insn);
                    buffer.delete(0, buffer.length());
                    compilingMethod = method;
                }
                else
                {
                    buffer.append(current);
                }
            }
        }
        new WolfInterpreter().run(included, instructions);
    }

    private void endOfInsn(ArrayList<Instruction> instructions, String buffer) throws WolfException
    {
        int endOfName = buffer.length();
        if(buffer.indexOf("(") >= 0)
        {
            endOfName = buffer.indexOf("(");
        }
        else if(buffer.indexOf(" ") >= 0)
        {
            endOfName = buffer.indexOf(" ");
        }
        String suggestedName = buffer.substring(0, endOfName);
        if(suggestedName.startsWith("return"))
        {
            boolean mustReturnValue = false;
            if(compilingMethod != null)
            {
                MethodDesc desc = compilingMethod.getDesc();
                mustReturnValue = desc.getReturnType() != WolfType.voidType;
            }
            boolean hasReturnValue = buffer.length() - "return".length() > 0;
            if(hasReturnValue && mustReturnValue && compilingMethod != null)
            {
                String returned = buffer.substring(buffer.lastIndexOf(" ") + 1);
                newPush(instructions, returned);
            }
            else if(!hasReturnValue && mustReturnValue && compilingMethod != null)
            {
                throwCompileError("Method type is not void, must return a value");
            }
            else if(!mustReturnValue && hasReturnValue && compilingMethod != null)
            {
                throwCompileError("Method type is not void, returned a value is not possible");
            }
            instructions.add(new BaseInstruction(RETURN));
        }
        else
        {
            if(buffer.contains("="))
            {
                newPush(instructions, buffer.split("=")[1]);
                String var = buffer.split("=")[0];
                while(var.charAt(0) == ' ')
                    var = var.substring(1);
                while(var.charAt(var.length() - 1) == ' ')
                    var = var.substring(0, var.length() - 1);
                boolean found = false;
                if(!var.contains(" "))
                {
                    for(WolfVariable local : compilingMethod.getLocals())
                    {
                        if(local.name.equals(var))
                        {
                            instructions.add(new StoreVariableInstruction(local.index));
                            found = true;
                            break;
                        }
                    }
                }
                else
                {
                    String[] split = var.split(" ");
                    String typeN = split[0];
                    while(typeN.charAt(0) == ' ')
                        typeN = typeN.substring(1);
                    while(typeN.charAt(typeN.length() - 1) == ' ')
                        typeN = typeN.substring(0, typeN.length() - 1);
                    WolfType type = WolfType.get(typeN);
                    String name = split[split.length - 1];
                    while(name.charAt(0) == ' ')
                        name = name.substring(1);
                    while(name.charAt(name.length() - 1) == ' ')
                        name = name.substring(0, name.length() - 1);
                    boolean alreadyExists = false;
                    int maxIndex = 0;
                    for(WolfVariable local : compilingMethod.getLocals())
                    {
                        if(local.index > maxIndex)
                        {
                            maxIndex = local.index;
                        }
                        if(local.name.equals(name))
                        {
                            alreadyExists = true;
                        }
                    }
                    if(!alreadyExists)
                    {
                        WolfVariable newVariable = new WolfVariable(maxIndex + 1, name, type, true, false, null);
                        compilingMethod.getLocals().add(newVariable);
                        instructions.add(new StoreVariableInstruction(maxIndex + 1));
                        found = true;
                    }
                    else
                    {
                        throwCompileError("Trying to overwrite already existing variable: " + name);
                    }
                }
                if(!found)
                {
                    throwCompileError(var + " cannot be resolved to a variable");
                }
            }
            else
            {
                newPush(instructions, buffer);
            }
        }
    }

    private void throwCompileError(String message) throws WolfCompileException
    {
        throw new WolfCompileException(message + " " + WolfHelper.getInfos((int)sourceLine));
    }

    private void newPush(ArrayList<Instruction> insns, String str) throws WolfException
    {
        char[] chars = str.toCharArray();
        StringBuffer buffer = new StringBuffer();
        Stack<String> methodNames = new Stack<>();
        Stack<String> argStack = new Stack<>();
        for(int i = 0; i < chars.length; i++)
        {
            char current = chars[i];
            if(current == '(')
            {
                methodNames.push(buffer.toString());
                buffer.delete(0, buffer.length());
            }
            else if(current == ' ')
            {
                
            }
            else if(current == ',')
            {
                if(buffer.length() > 0)
                {
                    argStack.push(buffer.toString());
                }
                else
                {
                    argStack.push("null");
                }
                buffer.delete(0, buffer.length());
            }
            else if(current == ')')
            {
                if(buffer.length() > 0)
                {
                    argStack.push(buffer.toString());
                }
                Stack<String> callStack = new Stack<>();
                while(!argStack.isEmpty())
                {
                    String pop = argStack.pop();
                    callStack.push(pop);
                }
                buffer.delete(0, buffer.length());
                String desc = "*(";
                while(!callStack.isEmpty())
                {
                    WolfType type = newPushVar(insns, callStack.pop());
                    if(type == null)
                        desc += "*;";
                    else
                        desc += type.getID() + ";";
                }
                desc += ")";
                String method = methodNames.pop();
                if(method.length() > 0)
                {
                    String mOwner = "std";
                    boolean found = false;
                    for(WolfLib lib : included)
                    {
                        mOwner = lib.getName();
                        if(WolfHelper.isMethod(lib, method, desc));
                        {
                            insns.add(new MethodCallInstruction(new WolfMethod(mOwner, WolfHelper.getMethodName(method), desc)));
                            found = true;
                            break;
                        }
                    }
                    if(!found)
                    {
                        throwCompileError("Unknown method: " + method + " " + desc);
                    }
                }
            }
            else
            {
                buffer.append(current);
            }
        }
        if(buffer.length() > 0)
            newPushVar(insns, buffer.toString());
    }

    private WolfType newPushVar(ArrayList<Instruction> insns, String var) throws WolfException
    {
        ArrayList<String> l = WolfHelper.getRPNOutputFromInFix(var, (int)sourceLine);
        WolfType type = null;
        for(String s : l)
        {
            if(s.equals(","))
                continue;
            Operation op = Operation.get(s);
            if(op != null)
            {
                insns.add(new OperationInstruction(op));
            }
            else
            {
                WolfValue nbr = WolfHelper.getAsNumber(s);
                if(nbr != null)
                {
                    insns.add(new LoadConstantInstruction(nbr));
                    type = nbr.type;
                    if(type.isCompatible(nbr.type))
                        type = nbr.type;
                }
                else
                {
                    if(s != null && !s.equals("null"))
                    {
                        if(s.startsWith("\"") && s.endsWith("\""))
                        {
                            insns.add(new LoadConstantInstruction(new WolfValue(s.substring(1, s.length() - 1), WolfType.stringType)));
                        }
                        else if(compilingMethod != null)
                        {
                            boolean found = false;
                            for(WolfVariable variable : compilingMethod.getLocals())
                            {
                                if(variable.name.equals(s))
                                {
                                    found = true;
                                    insns.add(new LoadVariableInstruction(variable.index));
                                }
                            }
                            if(!found)
                                throwCompileError(s + " cannot be resolved to a variable");
                        }
                        else
                        {
                            throwCompileError("Global variables are not allowed");
                        }
                    }
                }
            }
        }
        return type;
    }

    private String read(String file)
    {
        try
        {
            InputStream input = Test.class.getResourceAsStream(file);
            byte[] buffer = new byte[65565];
            int i;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            while((i = input.read(buffer, 0, buffer.length)) != -1)
            {
                out.write(buffer, 0, i);
            }
            out.flush();
            out.close();
            return new String(out.toByteArray(), "UTF-8");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private WolfLib parseLib(String prototypesSource, String name)
    {
        String[] prototypes = prototypesSource.split(";");
        ArrayList<WolfMethod> methods = new ArrayList<>();
        for(String prototype : prototypes)
        {
            if(prototype.length() > 0 && !prototype.equals("\n"))
            {
                prototype = prototype.replace("  ", " ").replace("\n", "").replace("\r", "").replace("\t", "");
                String argsStr = prototype.substring(prototype.indexOf("(") + 1, prototype.indexOf(")"));
                String mName = prototype.substring(prototype.indexOf(" ") + 1, prototype.indexOf("("));
                String desc = prototype.substring(0, prototype.indexOf(" ")) + "(";
                if(argsStr.length() > 0)
                {
                    String[] tokens = argsStr.split(",");
                    for(int i = 0; i < tokens.length; i++)
                    {
                        int start = 0;
                        while(tokens[0].charAt(start) == ' ')
                            start++;
                        tokens[i] = tokens[i].substring(start);
                        String split[] = tokens[i].split(" ");
                        String type = split[0];
                        desc += type + ";";
                    }
                }
                desc += ")";
                methods.add(new WolfMethod(name, mName, desc));
            }
        }
        WolfMethod[] methodsArray = methods.toArray(new WolfMethod[0]);
        WolfLib lib = new WolfLib()
        {
            @Override
            public String getName()
            {
                return name;
            }

            @Override
            public WolfMethod[] getMethods()
            {
                return methodsArray;
            }
        };
        return lib;
    }

    private boolean includeStandart(String s1)
    {
        switch(s1)
        {
        case "math":
            included.add(new MathLib());
        case "time":
            included.add(new TimeLib());
            return true;
        default:
            return false;
        }
    }
}