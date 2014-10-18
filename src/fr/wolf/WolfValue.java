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

public class WolfValue
{
    public WolfType type;
    public Object value;

    public WolfValue(Object v, WolfType type)
    {
        this.value = v;
        this.type = type;
    }
    
    public String toString()
    {
        if(value == null)
            return "null";
        return value.toString();
    }
}