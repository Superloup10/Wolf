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