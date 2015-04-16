package Cifrados;

import Util.*;
import Util.Math;

/**
 * Created by guillermo on 16/04/15.
 */
public class Vigenere extends CifradoGenerico implements Cifrado
{
    String key;

    public Vigenere(Object[] args) throws ParamsException
    {
        super( (String)args[0], (String)args[1] );
        if ( args.length < 3 || !(args[2] instanceof String) )
            throw new ParamsException(Constants.VIG_PARAM_ERR);
        key = (String)args[2];
    }

    @Override
    public void cifrar() throws ParamsException
    {
        String ret = "";
        int count = 0, res;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == ' ') {
                ret += " ";
            } else {
                res = Util.Math.mod(Constants.getIdx(input.charAt(i)) + Constants.getIdx(key.charAt(count++)));
                ret += Constants.ALFABETO[res];
                count %= key.length();
            }
        }
        output = ret;
    }

    @Override
    public void descifrar() throws ParamsException
    {
        //Mi = Ci - Ki mod27
        String ret = "";
        int count = 0, res;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == ' ') {
                ret += " ";
            } else {
                res = Math.mod(Constants.getIdx(input.charAt(i)) - Constants.getIdx(key.charAt(count++)));
                ret += Constants.ALFABETO[res];
                count %= key.length();
            }
        }
        output = ret;
    }

    @Override
    public void set(Object[] args)
    {
        input = (String)args[0];
        output = (String)args[1];
        key = (String)args[2];
    }

    @Override
    public Object[] get()
    {
        return new Object[]{input, output, key};
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public static void main(String[] args)
    {
        //Test Vigenere
        Object[] obj = {"HSSFL CQHDP QMIZX OGFVP NAQOD ZSBAS SQGIE QOPQM IZXOS UXPNA ZASJU YQONK JSBOW ATYAN KONQN NEQOS OUSOX PAJPI OQOSG UZNVD CFGXE JGLQW WZKWS LÑMEQ ONYNM ENZFQ HBBLV EHOMI JSNIJ TQAWA SJUXJ QOVMI PQODU FANVI OIYXE XQHUÑ IZCDL QLCQA WAHOM GJGDU YUNYG EDXME JDLMC LNAZA TCMOQ BUUPM PQOSO WCNAS NKUUB LVEJX PVXVO BUGNM WOJSM XYDRK UHBBD NFWWY XJNJC ÑZXRE YHBQA GOWUK UXBSF NXQLV OHOMN BWSUV CETOB QUBEJ KEJXM XXZVY XWODE ITJLQ UWNFH WCQRO GYZPX BPQMI DDSMU PINYS RTJUN AEOIL CQVOD QFMUU EOIÑI FQSMG MMGDK OBUNE NBTUG CLJZT QFIXN BGLUT HHZAI AILTO FQFLN UJYSJ ZGJ", "","JOAQUIN"};
        try
        {
            Vigenere c = new Vigenere(obj);
            c.descifrar();
            String g = c.getOutput();
            System.out.println("Descifrado: " + g);
            c.setInput(g);
            c.cifrar();
            System.out.println("Check: " + Formatter.replaceSpace((String) obj[0]).equals(Formatter.replaceSpace(c.getOutput())));
        } catch (ParamsException e)
        {
            e.printStackTrace();
        }
    }
}
