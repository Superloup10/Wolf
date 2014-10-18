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

public class WolfVariable extends WolfValue
{
    public boolean isGlobal;
    public boolean isLocal;
    public String name;
    public int index;  
    
    public WolfVariable(int index, String name, WolfType type, boolean isLocal, boolean isGlobal, Object initialValue)
    {
        super(initialValue, type);
        this.index = index;
        this.name = name;
        this.isLocal = isLocal;
        this.isGlobal = isGlobal;
    }
}