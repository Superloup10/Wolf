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

public class StringType extends WolfType
{
    public StringType(String id)
    {
        super(id);
    }
    
    public WolfValue add(WolfValue a, WolfValue b)
    {
        return new WolfValue(a.value + "" + b.value, stringType);
    }
    
    public WolfValue sub(WolfValue a, WolfValue b)
    {
        String value = "" + a.value;
        return new WolfValue(value.replace(b.value + "", ""), stringType);
    }
}