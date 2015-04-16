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

    public static class CifradoClasico
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
                if ( args.length < 3 || (!(args[0] instanceof String) || (!(args[1] instanceof Boolean) || !(args[2] instanceof Integer))) )
                    throw new ParamsException(Constants.TRANS_PARAM_ERR);

                return op ? Transposicion.cifrar((String)args[0], (Boolean)args[1], (Integer)args[2]) : Transposicion.descifrar((String) args[0], (Boolean) args[1], (Integer) args[2]);
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
     * boolean columna o fila, true/false
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

            int c = 1;
            for (int j = 0; j < nC; j++) {
                for (int i = j; i < nF * nC; i += nC) {
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

    public static void main(String[] args)
    {
        //Test Vigenere
        CifradoClasico c = new CifradoClasico("HSSFL CQHDP QMIZX OGFVP NAQOD ZSBAS SQGIE QOPQM IZXOS UXPNA ZASJU YQONK JSBOW ATYAN KONQN NEQOS OUSOX PAJPI OQOSG UZNVD CFGXE JGLQW WZKWS LÑMEQ ONYNM ENZFQ HBBLV EHOMI JSNIJ TQAWA SJUXJ QOVMI PQODU FANVI OIYXE XQHUÑ IZCDL QLCQA WAHOM GJGDU YUNYG EDXME JDLMC LNAZA TCMOQ BUUPM PQOSO WCNAS NKUUB LVEJX PVXVO BUGNM WOJSM XYDRK UHBBD NFWWY XJNJC ÑZXRE YHBQA GOWUK UXBSF NXQLV OHOMN BWSUV CETOB QUBEJ KEJXM XXZVY XWODE ITJLQ UWNFH WCQRO GYZPX BPQMI DDSMU PINYS RTJUN AEOIL CQVOD QFMUU EOIÑI FQSMG MMGDK OBUNE NBTUG CLJZT QFIXN BGLUT HHZAI AILTO FQFLN UJYSJ ZGJ");
        Object[] obj = {c.getText(),"JOAQUIN"};
        try
        {
            String g = c.descifrar(VIGENERE, obj);
            System.out.println("Descifrado: " + g);
            Object[] obj2 = {g, "JOAQUIN"};
            System.out.println("Check: " + c.getText().equals(c.cifrar(VIGENERE, obj2)));
        } catch (ParamsException e)
        {
            e.printStackTrace();
        }
        //Test Sustitucion afin, no simetrico??
        c = new CifradoClasico("UV IW GZ VC DF ZN QV PD VN FZ CQ WD WP VB CS QO FC QW NI VN QW VP ZN EO DS QV PC KW FC QW GZ VP ON BO XM CQ VC BL VN PO WN CB LW EV MK WZ NC WM CP OW NG ZV VD ML VS WB LW BO MO CS WH EO CS BC FW CM OV LU WH CL WN MW NC DK CT CP QV PZ NW EO CS IV CN ZD WS IV CU LO HZ DW SI OK WN WL NW QO PO IZ DW BZ VP CZ NG ZV VD NW IH LV UV CP WI HL VG ZO VN WH LC CP OU OV NV ZN NW IH LV SV PV NW IH LV VP VD QV MK ZD WD CE VN FC NX CQ VQ WN IV NQ WB VQ LW IZ RW XP VM CT WL NC QC BL OI VL C");
        obj = new Object[]{c.getText(), 5, 2};
        try {
            String g = c.descifrar(SUSAFIN, obj);
            System.out.println("Descifrado: " + g);
            Object[] obj2 = new Object[]{g, 5, 2};
            System.out.println("Check: " + Formatter.replaceSpace(c.getText()).equals(Formatter.replaceSpace(c.cifrar(SUSAFIN, obj2))));
        } catch (ParamsException e) {
            e.printStackTrace();
        }
        //Test transposicion, no simetrico por la sustitucion hasta rellenar los huecos
        c = new CifradoClasico("NENPANUSAURTNEDEPACNODONAURARACNQSUXOOULNXNREAAXFDNNVXIEOZEX");
        obj = new Object[]{c.getText(), true, 10};
        try {
            String g = c.descifrar(TRANSPOSICION, obj);
            System.out.println("Descifrado: " + g);
            Object[] obj2 = new Object[]{Formatter.replaceSpace(g), true, 10};
            System.out.println("Check: " + c.getText().equals(c.cifrar(TRANSPOSICION, obj2)));
        } catch (ParamsException e) {
            e.printStackTrace();
        }
    }
}
