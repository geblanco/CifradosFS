package Util;

public class Formatter
{
    public static String formatOutput(String text)
    {
        return formatOutput(text, 5);
    }

    public static String replaceSpace(String text)
    {
        return replace(text, " ");
    }

    public static String formatOutput(String text, int spaceDist)
    {
        String ret = "";
        int i;

        for (i = 0; i <= text.length()-spaceDist; i += spaceDist) {
            String aux = text.substring(i, i + spaceDist);
            ret += aux + " ";
        }
        ret += text.substring(i, text.length());
        return ret;
    }

    public static String replace(String text, String c)
    {
        String ret = "";

        if (text.contains( c )) {
            String[] a = text.split( c );
            for (String k : a)
                ret += k;
            return ret;
        }
        return text;
    }
}
