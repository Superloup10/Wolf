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
import java.util.HashMap;

public class WolfType
{
    public static class NumberType extends WolfType
    {
        public NumberType(String id)
        {
            super(id);
        }
        
        public WolfValue add(WolfValue a, WolfValue b)
        {
            if(this.isCompatible(a.type) && this.isCompatible(b.type))
            {
                Object newVal = null;
                if(getID().equals("int"))
                {
                    newVal = ((Integer)convert(a.value, this) + (Integer)convert(b.value, this));
                }
                else if(getID().equals("float"))
                {
                    newVal = ((Float)convert(a.value, this) + (Float)convert(b.value, this));
                }
                else if(getID().equals("double"))
                {
                    newVal = ((Double)convert(a.value, this) + (Double)convert(b.value, this));
                }
                else if(getID().equals("long"))
                {
                    newVal = ((Long)convert(a.value, this) + (Long)convert(b.value, this));
                }
                return new WolfValue(newVal, WolfType.intType);
            }
            return null;
        }
        
        public WolfValue sub(WolfValue a, WolfValue b)
        {
            if(this.isCompatible(a.type) && this.isCompatible(b.type))
            {
                Object newVal = null;
                if(getID().equals("int"))
                {
                    newVal = ((Integer)convert(a.value, this) - (Integer)convert(b.value, this));
                }
                else if(getID().equals("float"))
                {
                    newVal = ((Float)convert(a.value, this) - (Float)convert(b.value, this));
                }
                else if(getID().equals("double"))
                {
                    newVal = ((Double)convert(a.value, this) - (Double)convert(b.value, this));
                }
                else if(getID().equals("long"))
                {
                    newVal = ((Long)convert(a.value, this) - (Long)convert(b.value, this));
                }
                return new WolfValue(newVal, WolfType.intType);
            }
            return null;
        }
        
        public WolfValue mul(WolfValue a, WolfValue b)
        {
            if(this.isCompatible(a.type) && this.isCompatible(b.type))
            {
                Object newVal = null;
                if(getID().equals("int"))
                {
                    newVal = ((Integer)convert(a.value, this) * (Integer)convert(b.value, this));
                }
                else if(getID().equals("float"))
                {
                    newVal = ((Float)convert(a.value, this) * (Float)convert(b.value, this));
                }
                else if(getID().equals("double"))
                {
                    newVal = ((Double)convert(a.value, this) * (Double)convert(b.value, this));
                }
                else if(getID().equals("long"))
                {
                    newVal = ((Long)convert(a.value, this) * (Long)convert(b.value, this));
                }
                return new WolfValue(newVal, WolfType.intType);
            }
            return null;
        }
        
        public WolfValue div(WolfValue a, WolfValue b)
        {
            if(this.isCompatible(a.type) && this.isCompatible(b.type))
            {
                Object newVal = null;
                if(getID().equals("int"))
                {
                    newVal = ((Integer)convert(a.value, this) / (Integer)convert(b.value, this));
                }
                else if(getID().equals("float"))
                {
                    newVal = ((Float)convert(a.value, this) / (Float)convert(b.value, this));
                }
                else if(getID().equals("double"))
                {
                    newVal = ((Double)convert(a.value, this) / (Double)convert(b.value, this));
                }
                else if(getID().equals("long"))
                {
                    newVal = ((Long)convert(a.value, this) / (Long)convert(b.value, this));
                }
                return new WolfValue(newVal, WolfType.intType);
            }
            return null;
        }
        
        public WolfValue mod(WolfValue a, WolfValue b)
        {
            if(this.isCompatible(a.type) && this.isCompatible(b.type))
            {
                Object newVal = null;
                if(getID().equals("int"))
                {
                    newVal = ((Integer)convert(a.value, this) % (Integer)convert(b.value, this));
                }
                else if(getID().equals("float"))
                {
                    newVal = ((Float)convert(a.value, this) % (Float)convert(b.value, this));
                }
                else if(getID().equals("double"))
                {
                    newVal = ((Double)convert(a.value, this) % (Double)convert(b.value, this));
                }
                else if(getID().equals("long"))
                {
                    newVal = ((Long)convert(a.value, this) % (Long)convert(b.value, this));
                }
                return new WolfValue(newVal, WolfType.intType);
            }
            return null;
        }
        
        public WolfValue lsh(WolfValue a, WolfValue b)
        {
            if(this.isCompatible(a.type) && this.isCompatible(b.type))
            {
                Object newVal = null;
                if(getID().equals("int"))
                {
                    newVal = ((Integer)convert(a.value, this) << (Integer)convert(b.value, this));
                }
                else if(getID().equals("long"))
                {
                    newVal = ((Long)convert(a.value, this) << (Long)convert(b.value, this));
                }
                return new WolfValue(newVal, WolfType.intType);
            }
            return null;
        }
        
        public WolfValue rsh(WolfValue a, WolfValue b)
        {
            if(this.isCompatible(a.type) && this.isCompatible(b.type))
            {
                Object newVal = null;
                if(getID().equals("int"))
                {
                    newVal = ((Integer)convert(a.value, this) >> (Integer)convert(b.value, this));
                }
                else if(getID().equals("long"))
                {
                    newVal = ((Long)convert(a.value, this) >> (Long)convert(b.value, this));
                }
                return new WolfValue(newVal, WolfType.intType);
            }
            return null;
        }
        
        public WolfValue ursh(WolfValue a, WolfValue b)
        {
            if(this.isCompatible(a.type) && this.isCompatible(b.type))
            {
                Object newVal = null;
                if(getID().equals("int"))
                {
                    newVal = ((Integer)convert(a.value, this) >>> (Integer)convert(b.value, this));
                }
                else if(getID().equals("long"))
                {
                    newVal = ((Long)convert(a.value, this) >>> (Long)convert(b.value, this));
                }
                return new WolfValue(newVal, WolfType.intType);
            }
            return null;
        }
        
        public WolfValue bitAnd(WolfValue a, WolfValue b)
        {
            if(this.isCompatible(a.type) && this.isCompatible(b.type))
            {
                Object newVal = null;
                if(getID().equals("int"))
                {
                    newVal = ((Integer)convert(a.value, this) & (Integer)convert(b.value, this));
                }
                else if(getID().equals("long"))
                {
                    newVal = ((Long)convert(a.value, this) & (Long)convert(b.value, this));
                }
                return new WolfValue(newVal, WolfType.intType);
            }
            return null;
        }
        
        public WolfValue bitOr(WolfValue a, WolfValue b)
        {
            if(this.isCompatible(a.type) && this.isCompatible(b.type))
            {
                Object newVal = null;
                if(getID().equals("int"))
                {
                    newVal = ((Integer)convert(a.value, this) | (Integer)convert(b.value, this));
                }
                else if(getID().equals("long"))
                {
                    newVal = ((Long)convert(a.value, this) | (Long)convert(b.value, this));
                }
                return new WolfValue(newVal, WolfType.intType);
            }
            return null;
        }
        
        public WolfValue bitXor(WolfValue a, WolfValue b)
        {
            if(this.isCompatible(a.type) && this.isCompatible(b.type))
            {
                Object newVal = null;
                if(getID().equals("int"))
                {
                    newVal = ((Integer)convert(a.value, this) ^ (Integer)convert(b.value, this));
                }
                else if(getID().equals("long"))
                {
                    newVal = ((Long)convert(a.value, this) ^ (Long)convert(b.value, this));
                }
                return new WolfValue(newVal, WolfType.intType);
            }
            return null;
        }

        public static Object convert(Object value, WolfType type)
        {
            if(value == null)
            {
                if(type == WolfType.intType)
                {
                    return 0;
                }
                if(type == WolfType.longType)
                {
                    return 0;
                }
                if(type == WolfType.floatType)
                {
                    return 0;
                }
                if(type == WolfType.doubleType)
                {
                    return 0;
                }
            }
            else if(type.equals(WolfType.intType))
            {
                if(value instanceof Double)
                {
                    return (int)((double)value);
                }
                if(value instanceof Integer)
                {
                    return (int)value;
                }
                if(value instanceof Float)
                {
                    return (int)((float)value);
                }
                if(value instanceof Long)
                {
                    return (int)((long)value);
                }
            }
            else if(type.equals(WolfType.longType))
            {
                if(value instanceof Double)
                {
                    return (long)((double)value);
                }
                if(value instanceof Integer)
                {
                    return (long)((int)value);
                }
                if(value instanceof Float)
                {
                    return (long)((float)value);
                }
                if(value instanceof Long)
                {
                    return (long)value;
                }
            }
            else if(type.equals(WolfType.floatType))
            {
                if(value instanceof Double)
                {
                    return (float)((double)value);
                }
                if(value instanceof Integer)
                {
                    return (float)((int)value);
                }
                if(value instanceof Float)
                {
                    return (float)value;
                }
                if(value instanceof Long)
                {
                    return (float)((long)value);
                }
            }
            else if(type.equals(WolfType.doubleType))
            {
                if(value instanceof Double)
                {
                    return (double)value;
                }
                if(value instanceof Integer)
                {
                    return (double)((int)value);
                }
                if(value instanceof Float)
                {
                    return (double)((float)value);
                }
                if(value instanceof Long)
                {
                    return (double)((long)value);
                }
            }
            return null;
        }
    }
    
    private static HashMap<String, WolfType> registred = new HashMap<>();    
    public static final WolfType intType = new NumberType("int");
    public static final WolfType longType = new NumberType("long");
    public static final WolfType voidType = new WolfType("void");
    public static final WolfType floatType = new NumberType("float");
    public static final WolfType doubleType = new NumberType("double");
    public static final WolfType stringType = new StringType("string");
    public static final WolfType wildcartType = new WolfType("*")
    {
        public boolean isCompatible(WolfType other)
        {
            return true;
        }
    };
    
    static
    {
        intType.addCompatible(longType);
        intType.addCompatible(floatType);
        intType.addCompatible(doubleType);
        
        longType.addCompatible(floatType);
        longType.addCompatible(doubleType);

        floatType.addCompatible(doubleType);
        
    }
    
    private String id;
    private ArrayList<WolfType> compatibles;
    
    public WolfType(String id)
    {
        compatibles = new ArrayList<>();
        this.id = id;
        registred.put(id, this);
    }

    private void addCompatible(WolfType other)
    {
        compatibles.add(other);
    }

    public static WolfType get(String id)
    {
        if(registred.containsKey(id))
            return registred.get(id);
        return null;
    }

    public boolean isCompatible(WolfType other)
    {
        if(other == null)
            return false;
        return other.compatibles.contains(this) || other == this;
    }

    public String getID()
    {
        return id;
    }
    
    public boolean equals(Object o)
    {
        if(o instanceof String)
        {
            return ((String)o).equals(id);
        }
        else if(o instanceof WolfType)
        {
            return ((WolfType)o).getID().equals(id);
        }
        return false;
    }
    
    public WolfValue add(WolfValue a, WolfValue b)
    {
        return a;
    }
    
    public WolfValue sub(WolfValue a, WolfValue b)
    {
        return a;
    }
    
    public WolfValue mul(WolfValue a, WolfValue b)
    {
        return a;
    }
    
    public WolfValue div(WolfValue a, WolfValue b)
    {
        return a;
    }
    
    public WolfValue mod(WolfValue a, WolfValue b)
    {
        return a;
    }
    
    public WolfValue lsh(WolfValue a, WolfValue b)
    {
        return a;
    }
    
    public WolfValue rsh(WolfValue a, WolfValue b)
    {
        return a;
    }
    
    public WolfValue ursh(WolfValue a, WolfValue b)
    {
        return a;
    }
    
    public WolfValue bitAnd(WolfValue a, WolfValue b)
    {
        return a;
    }
    
    public WolfValue bitOr(WolfValue a, WolfValue b)
    {
        return a;
    }
    
    public WolfValue bitXor(WolfValue a, WolfValue b)
    {
        return a;
    }
}