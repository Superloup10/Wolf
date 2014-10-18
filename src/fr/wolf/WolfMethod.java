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

public class WolfMethod
{
    public static class MethodDesc
    {
        private String from;
        private WolfType returnType;
        
        public MethodDesc(String from)
        {
            this.from = from;
            String returnTypeStr = from.substring(0, from.indexOf("("));
            returnType = WolfType.get(returnTypeStr);
        }
        
        public MethodDesc(WolfType returnType, WolfType... args)
        {
            this.returnType = returnType;
            this.from = returnType.getID() + "(";
            for(WolfType type : args)
            {
                from += type.getID() + ";";
            }
            from += ")";
        }
        
        public String toString()
        {
            return from;
        }

        public WolfType getReturnType()
        {
            return returnType;
        }
    }
    
    private String owner;
    private String name;
    private MethodDesc desc;
    private ArrayList<WolfVariable> locals;
    
    public WolfMethod(String owner, String name, String desc)
    {
        this(owner, name, new MethodDesc(desc.replace("\n", "").replace("\r", "")));
    }
    
    public WolfMethod(String owner, String name, MethodDesc desc)
    {
        this.owner = owner.replace("\n", "").replace("\r", "");
        this.name = name.replace("\n", "").replace("\r", "");
        this.desc = desc;
        locals = new ArrayList<>();
    }

    public String getName()
    {
        return name;
    }

    public MethodDesc getDesc()
    {
        return desc;
    }

    public WolfValue invoke(WolfValue... args)
    {
        return null;
    }

    public String getOwner()
    {
        return owner;
    }
    
    public void addLocals(ArrayList<WolfVariable> varList)
    {
        locals.addAll(varList);
    }
    
    
    public ArrayList<WolfVariable> getLocals()
    {
        return locals;
    }
}