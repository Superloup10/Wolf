package fr.wolf;

public class WolfHelper
{
    public static final String HEADER_EXTENSION = "wi";
    public static final String IMPL_EXTENSION = "wh";
    
    public static int countChar(String s, char c)
    {
        int n = 0;
        for(char c1 : s.toCharArray())
        {
            if(c1 == c)
            {
                n++;
            }
        }
        return n;
    }
}