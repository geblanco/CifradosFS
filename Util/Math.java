package Util;

public class Math
{
    int module;

    public Math(int m) {module = m;}

    public int getModule()
    {
        return module;
    }

    public void setModule(int module)
    {
        this.module = module;
    }

    private int getInverse(int toI)
    {
        int i;
        for (i = 0; i < module && ((toI * i) % module) != 1; i++) ;
        return i;
    }

    //Non object methods
    public static int getInverse(int toI, int mod)
    {
        int i;
        for (i = 0; i < mod && ((toI * i) % mod) != 1; i++);
        return i;
    }

    public static int mod(int n)
    {
        if (n < 0) return (n + Constants.ALFABETO.length);
        return n;
    }

    public static int roundDiv(int x, int y)
    {
        return ( (((x * 10) - y) % 10) > 0 ? ((x / y) + 1) : (x / y) );
    }

    public static boolean isPrime(int a)
    {
        return getInverse(a, Constants.ALFABETO.length) != Constants.ALFABETO.length;
    }

    public class Frequency
    {
        private String sentece;
        private int[] f = new int[Constants.ALFABETO.length];

        public Frequency(String t) { sentece = t; }

        public void analize()
        {
            for (int i = 0; i < sentece.length(); i++) {
                f[Constants.getIdx(sentece.charAt(i))]++;
            }
        }

        public String toString()
        {
            String ret = "";
            if (sentece == null)
                return "Se necesita una frase para analizar";
            else {
                for (int i = 0; i < f.length; i++) {
                    ret += Constants.ALFABETO[i] + "->" + f[i] + "\t";
                    if (i > 0 && i % 5 == 0)
                        ret += "\n";
                }
            }
            return ret;
        }
    }
}
