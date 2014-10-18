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

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class Test
{
    public static void main(String[] args) throws WolfException
    {
        String source = read("/test." + WolfHelper.IMPL_EXTENSION);
        String headers = read("/test." + WolfHelper.HEADER_EXTENSION);
        new WolfCompiler().compileAndRun(headers, source);
    }

    private static String read(String file)
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
}