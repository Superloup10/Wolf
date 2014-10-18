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

import java.util.ArrayList;
import java.util.Stack;

public class WolfHelper
{
    public static final String HEADER_EXTENSION = "wh";
    public static final String IMPL_EXTENSION = "wi";
    
    public static int countChar(String s, char c)
    {
        System.out.println("C:" + c);
        int n = 0;
        for(char c1 : s.toCharArray())
        {
            System.out.println("C1:" + c1);
            if(c1 == c)
            {
                n++;
                System.out.println("Ntres" + n);
            }
            System.out.println("Nbis:" + n);
        }
        return n;
    }
    
    public static boolean areDescEquals(String a, String b)
    {
        WolfType aReturnType = WolfType.get(a.substring(0, a.indexOf("(")));
        WolfType bReturnType = WolfType.get(b.substring(0, b.indexOf("(")));
        if(!aReturnType.isCompatible(bReturnType) && !bReturnType.isCompatible(aReturnType))
            return false;
        String aArgs = a.substring(a.indexOf("(") + 1, a.indexOf(")"));
        String bArgs = b.substring(b.indexOf("(") + 1, b.indexOf(")"));
        if(countChar(aArgs, ';') != countChar(bArgs, ';'))
            return false;
        String[] aToken = aArgs.split(";");
        String[] bToken = bArgs.split(";");
        if(aToken.length != bToken.length)
            return false;
        for(int i = 0; i < aToken.length - 1; i++)
        {
            WolfType aType  = WolfType.get(aToken[i]);
            WolfType bType  = WolfType.get(bToken[i]);
            if(!aType.isCompatible(bType) && !bType.isCompatible(aType))
                return false;
        }
        return true;
    }
    
    public static String getMethodName(String txt)
    {
        String n = txt;
        if(n.contains("."))
            n = n.substring(n.lastIndexOf(".") + 1);
        return n;
    }
    
    public static boolean isMethod(WolfLib lib, String name, String methodDesc)
    {
        String owner = null;
        String n = name;
        if(n.contains("."))
        {
            owner = n.split(".")[0];
            n = n.substring(n.lastIndexOf(".") + 1);
            if(!owner.equals(lib.getName()))
                return false;
        }
        for(WolfMethod m : lib.getMethods())
        {
            if(m.getName().equals(n) && WolfHelper.areDescEquals(m.getDesc().toString(), methodDesc))
                return true;
        }
        return false;
    }
    
    public static WolfValue getAsNumber(String var)
    {
        if(!var.contains("."))
        {
            try
            {
                WolfType type = WolfType.intType;
                return new WolfValue(Integer.parseInt(var), type);
            }
            catch(Exception e)
            {
                ;
            }
            
            try
            {
                WolfType type = WolfType.longType;
                return new WolfValue(Long.parseLong(var), type);
            }
            catch(Exception e)
            {
                ;
            }
        }
        else
        {
            try
            {
                WolfType type = WolfType.floatType;
                return new WolfValue(Float.parseFloat(var), type);
            }
            catch(Exception e)
            {
                ;
            }
            
            try
            {
                WolfType type = WolfType.doubleType;
                return new WolfValue(Double.parseDouble(var), type);
            }
            catch(Exception e)
            {
                ;
            }
        }
        return null;
    }
    
    public static ArrayList<String> getRPNOutputFromInFix(String arg, int line) throws WolfException
    {
        String[] tokens = getTokens(arg, line);
        
        ArrayList<String> outputQueue = new ArrayList<String>();
        Stack<String> operatorStack = new Stack<String>();
        for(String token : tokens)
        {
            if(token.equals("("))
            {
                operatorStack.push(token);
            }
            else if(token.equals(")"))
            {
                while(!operatorStack.isEmpty())
                {
                    String operator = operatorStack.pop();
                    if(operator.equals("("))
                    {
                        break;
                    }
                    else
                    {
                        outputQueue.add(operator);
                    }
                }
            }
            else
            {
                if(isTokenOperator(token))
                {
                    while(!operatorStack.isEmpty())
                    {
                        String operator = operatorStack.peek();
                        if(getPrecedence(operator) >= getPrecedence(token))
                        {
                            outputQueue.add(operatorStack.pop());
                        }
                        else
                        {
                            break;
                        }
                    }
                    operatorStack.push(token);
                }
                else
                {
                    outputQueue.add(token);
                }
            }
        }
        while(!operatorStack.isEmpty())
        {
            String operator = operatorStack.pop();
            if(operator.equals("(") || operator.equals(")"))
                continue;
            outputQueue.add(operator);
        }
        return outputQueue;
    }

    public static String[] getTokens(String arg, int line) throws WolfException
    {
        ArrayList<String> tokensList = new ArrayList<String>();
        String currentNumber = "";
        boolean inDecimalPart = false;
        boolean inString = false;
        StringBuffer buffer = new StringBuffer();
        
        for(int i = 0; i < arg.length(); i++)
        {
            char c = arg.charAt(i);
            if(c >= '0' && c <= '9' && !inString)
            {
                if(buffer.length() > 0)
                {
                    tokensList.add(buffer.toString());
                    buffer.delete(0, buffer.length());
                }
                currentNumber += c;
            }
            else if(c == '"')
            {
                buffer.append('"');
                inString = !inString;
            }
            else if(c == '.' && !inString)
            {
                if(buffer.length() > 0)
                {
                    tokensList.add(buffer.toString());
                    buffer.delete(0, buffer.length());
                }
                if(!inDecimalPart)
                {
                    inDecimalPart = true;
                    currentNumber += c;
                }
                else
                {
                    throwError("Invalid decimal separator" + arg, line);
                }
            }
            else if(isTokenOperator("" + c) && !inString)
            {
                if(buffer.length() > 0)
                {
                    tokensList.add(buffer.toString());
                    buffer.delete(0, buffer.length());
                }
                if(!isNaN(currentNumber))
                {
                    tokensList.add(currentNumber);
                    currentNumber = "NaN";
                    inDecimalPart = false;
                }
                tokensList.add("" + c);
            }
            else if(isTokenOperator(buffer.toString() + c) && !inString)
            {
                tokensList.add(buffer.toString() + c);
                buffer.delete(0, buffer.length());
            }
            else
            {
                if(!isNaN(currentNumber) && !inString)
                {
                    tokensList.add(currentNumber);
                    currentNumber = "";
                    inDecimalPart = false;
                }
                buffer.append(c);
            }
        }
        if(buffer.length() > 0)
        {
            tokensList.add(buffer.toString());
            buffer.delete(0, buffer.length());
        }
        if(!isNaN(currentNumber))
        {
            tokensList.add(currentNumber);
        }
        return tokensList.toArray(new String[0]);
    }

    public static void throwError(String message, int line) throws WolfException
    {
        throw new WolfException(message + " " + getInfos(line));
    }

    public static String getInfos(int currentLine)
    {
        return "(in __MAIN__ at line:" + currentLine + ")";
    }

    public static boolean isNaN(String currentNumber)
    {
        if(currentNumber.equals("NaN"))
            return true;
        try
        {
            return Double.isNaN(Double.parseDouble(currentNumber));
        }
        catch(Exception e)
        {
            ;
        }
        return true;
    }

    public static int getPrecedence(String operator)
    {
        if(operator.equals("+"))
            return 2;
        if(operator.equals("-"))
            return 2;
        if(operator.equals("*"))
            return 3;
        if(operator.equals("/"))
            return 3;
        if(operator.equals("%"))
            return 3;
        if(operator.equals("<<"))
            return 7;
        if(operator.equals(">>"))
            return 7;
        if(operator.equals(">>>"))
            return 7;
        if(operator.equals("&"))
            return 10;
        if(operator.equals("^"))
            return 11;
        if(operator.equals("|"))
            return 12;
        return 0;
    }

    public static boolean isTokenOperator(String token)
    {
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/") || token.equals("%") || token.equals(">>") || token.equals("<<") || token.equals(">>>") || token.equals("^") || token.equals("|") || token.equals("&");
    }
}