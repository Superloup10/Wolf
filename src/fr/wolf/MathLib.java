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

import fr.wolf.WolfType.NumberType;

public class MathLib extends WolfLib
{
    public class AbsMethod extends WolfMethod
    {
        public AbsMethod()
        {
            super("math", "abs", new MethodDesc(WolfType.doubleType, WolfType.doubleType));
        }
        
        public WolfValue invoke(WolfValue... args)
        {
            WolfValue toRaise = args[0];
            return new WolfValue(Math.abs((double)NumberType.convert(toRaise.value, WolfType.doubleType)), WolfType.doubleType);
        }
    }
    
    public class SqrtMethod extends WolfMethod
    {
        public SqrtMethod()
        {
            super("math", "sqrt", new MethodDesc(WolfType.doubleType, WolfType.doubleType));
        }
        
        public WolfValue invoke(WolfValue... args)
        {
            WolfValue toRaise = args[0];
            return new WolfValue(Math.sqrt((double)NumberType.convert(toRaise.value, WolfType.doubleType)), WolfType.doubleType);
        }
    }
    
    public class PowMethod extends WolfMethod
    {
        public PowMethod()
        {
            super("math", "pow", new MethodDesc(WolfType.doubleType, WolfType.doubleType, WolfType.intType));
        }
        
        public WolfValue invoke(WolfValue... args)
        {
            WolfValue toRaise = args[0];
            WolfValue power = args[1];
            return new WolfValue(Math.pow((double)NumberType.convert(toRaise.value, WolfType.doubleType), (int)NumberType.convert(power.value, WolfType.intType)), WolfType.doubleType);
        }
    }
    
    @Override
    public String getName()
    {
        return "math";
    }

    @Override
    public WolfMethod[] getMethods()
    {
        return new WolfMethod[]
        {
            new PowMethod(), new SqrtMethod(), new AbsMethod()
        };
    }
}