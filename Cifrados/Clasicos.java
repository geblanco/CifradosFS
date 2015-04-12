package Cifrados;

import Util.Constants;
import Util.Formatter;
import Util.Math;
import Util.ParamsException;

public class Clasicos
{
    public static final int TRANSPOSICION = 0;
    public static final int VIGENERE = 1;
    public static final int SUSAFIN = 2;

    public class CifradoClasico
    {
        String text;

        public CifradoClasico() { new CifradoClasico(""); }
        public CifradoClasico(String text) {this.text = text;}

        public String cifrar(int t, Object... args) throws ParamsException
        {
            return work(t, true, args);
        }

        public String descifrar(int t, Object... args) throws ParamsException
        {
            return work(t, false, args);
        }

        private String work(int t, boolean op, Object... args) throws ParamsException
        {
            if (t == TRANSPOSICION) {
                if ( args.length == 0 || (args.length == 1 && !(args[0] instanceof Integer)))
                    throw new ParamsException(Constants.TRANS_PARAM_ERR);

                boolean CoF = false;
                if (args.length > 1 && args[0] instanceof Boolean)
                    CoF = (Boolean)args[0];

                return op ? Transposicion.cifrar(this.text, CoF, (Integer)args[1]) : Transposicion.descifrar(this.text, CoF, (Integer) args[1]);
            } else if (t == VIGENERE) {
                if ( args.length < 2 || (!(args[0] instanceof String) || !(args[1] instanceof String)) )
                    throw new ParamsException(Constants.VIG_PARAM_ERR);

                return op ? Vigenere.cifrar((String)args[0], (String)args[1]) : Vigenere.descifrar((String) args[0], (String) args[1]);
            } else if (t == SUSAFIN) {
                if ( args.length < 3 || (!(args[0] instanceof String) || !(args[1] instanceof Integer) || !(args[2] instanceof Integer)) )
                    throw new ParamsException(Constants.SIMAFIN_PARAM_ERR);

                return op ? SustitucionAfin.cifrar((String)args[0], (Integer)args[1], (Integer)args[2]) : SustitucionAfin.descifrar((String) args[0], (Integer) args[1], (Integer) args[2]);
            } else {
                throw new ParamsException(Constants.OP_NO_CONOCIDA);
            }
        }

        public String getText()
        {
            return text;
        }

        public void setText(String text)
        {
            this.text = text;
        }
    }

    /**
     * Obligatorio para toda operacion:
     *
     * String text
     * int n
     *
     *
     * Opcionales:
     *
     * boolean columna o fila, columna por defecto
     */
    private static class Transposicion
    {
        /**
         * Codifica el texto dado mediante transposicion de columnas o filas
         *
         * @param text Texto a codificar
         * @param CoF  Codificacion por columnas o por filas (true/false)
         * @param n    Numero de columnas o filas
         * @return Texto codificado
         */

        public static String cifrar(String text, boolean CoF, int n)
        {
            int c = Math.roundDiv(text.length(), n);
            return cod(text, CoF ? n : c, CoF ? c : n);
        }

        public static String descifrar(String text, boolean CoF, int n)
        {
            int c = Math.roundDiv(text.length(), n);
            return cod(text, CoF ? c : n, CoF ? n : c);
        }

        private static String cod(String text, int nC, int nF)
        {
            String ret = "";

            for (int j = 0; j < nC; j++)
            {
                for (int i = j; i < nF * nC; i += nC)
                {
                    ret += i >= text.length() ? "X" : text.charAt(i);
                }
            }

            return Formatter.formatOutput(Formatter.replaceSpace(ret)).toUpperCase();
        }
    }

    /**
     * Obligatorio para toda operacion:
     *
     * String text
     * String key
     */
    private static class Vigenere
    {
        public static String cifrar(String text, String key)
        {//Ci = Mi + Ki mod27
            String ret = "";
            int count = 0, res;
            for (int i = 0; i < text.length(); i++) {
                if (text.charAt(i) == ' ') {
                    ret += " ";
                } else {
                    res = Math.mod(Constants.getIdx(text.charAt(i)) + Constants.getIdx(key.charAt(count++)));
                    ret += Constants.ALFABETO[res];
                    count %= key.length();
                }
            }
            return ret;
        }

        public static String descifrar(String text, String key)
        {//Mi = Ci - Ki mod27
            String ret = "";
            int count = 0, res;
            for (int i = 0; i < text.length(); i++) {
                if (text.charAt(i) == ' ') {
                    ret += " ";
                } else {
                    res = Math.mod(Constants.getIdx(text.charAt(i)) - Constants.getIdx(key.charAt(count++)));
                    ret += Constants.ALFABETO[res];
                    count %= key.length();
                }
            }
            return ret;
        }
    }

    private static class SustitucionAfin
    {
        /**
         * Cifra un texto dado mediante la tecnica de sustitucion afin
         * @param text Texto a cifrar
         * @param a    Constante de decimacion (Debe ser primo relativo con el modulo del alfabeto)
         * @param b    Constante de desplazamiento
         * @return     Texto cifrado
         */
        //(Mi + b) * a mod27
        public static String cifrar(String text, int a, int b)
        {
            //Test 'a' to be prime with mod
            if (!Math.isPrime(a))
                return ("La constante de decimación (a) debe ser primo relativo con el modulo del alfabeto: " + Constants.ALFABETO.length);
            String ret = "";
            int mod = 27, code;
            for (int i = 0; i < text.length(); i++)
            {
                if (text.charAt(i) != ' ') {
                    code = Constants.getIdx(text.charAt(i));
                    int decode = Math.mod((code + b) * a);
                    ret += Constants.ALFABETO[decode];
                }
            }
            return Formatter.formatOutput(ret);
        }

        //(Ci – b) * inv(a, 27) mod27
        public static String descifrar(String text, int a, int b)
        {
            //Test 'a' to be prime with mod
            if (!Math.isPrime(a))
                return ("La constante de decimación (a) debe ser primo relativo con el modulo del alfabeto: " + Constants.ALFABETO.length);
            String ret = "";
            int mod = 27, code;
            for (int i = 0; i < text.length(); i++)
            {
                if (text.charAt(i) != ' ') {
                    code = Constants.getIdx(text.charAt(i));
                    int decode = Math.mod((code - b) * Math.getInverse(a, mod));
                    ret += Constants.ALFABETO[decode];
                }
            }
            return Formatter.formatOutput(ret);
        }
    }
}
