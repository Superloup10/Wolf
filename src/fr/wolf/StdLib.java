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

public class StdLib extends WolfLib
{
    private static class StdOut extends WolfMethod
    {
        public StdOut()
        {
            super("std", "out", "void(*;)");
        }
        
        public WolfValue invoke(WolfValue... args)
        {
            System.out.println("[Program Out] " + args[0].toString() + " type is " + args[0].type.getID());
            return null;
        }
    }
    
    @Override
    public String getName()
    {
        return "std";
    }

    @Override
    public WolfMethod[] getMethods()
    {
        return new WolfMethod[]
        {
            new StdOut()
        };
    }
}