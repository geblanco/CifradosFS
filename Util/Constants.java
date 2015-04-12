package Util;

public class Constants
{
    public static char ALFABETO[] = {
             'A', 'B', 'C', 'D', 'E', 'F', 'G'
            ,'H', 'I', 'J', 'K', 'L', 'M', 'N'
            ,'Ñ', 'O', 'P', 'Q', 'R', 'S', 'T'
            ,'U', 'V', 'W', 'X', 'Y', 'Z'   };

    public static int getIdx(char input)
    {
        int i = 0;
        for (; i < ALFABETO.length && ALFABETO[i] != input; i++);
        return i;
    }

}
