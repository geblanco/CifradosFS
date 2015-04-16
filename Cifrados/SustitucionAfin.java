package Cifrados;

import Util.*;
import Util.Math;

/**
 * Created by guillermo on 15/04/15.
 */
public class SustitucionAfin extends CifradoGenerico implements Cifrado
{
    int decimacion, desplazamiento;

    public SustitucionAfin(Object[] args) throws ParamsException
    {
        super((String)args[0], (String)args[1]);
        if ( args.length < 4 || (!(args[2] instanceof Integer) || !(args[3] instanceof Integer)))
            throw new ParamsException(Constants.TRANS_PARAM_ERR);

        //Test 'a' to be prime with mod
        if ( !Math.isPrime((Integer)args[2]) )
            throw new ParamsException("La constante de decimación (a) debe ser primo relativo con el modulo del alfabeto: " + Constants.ALFABETO.length);

        decimacion = (Integer)args[2];
        desplazamiento = (Integer)args[3];
    }

    @Override
    public void cifrar() throws ParamsException
    {
        //(Mi + b) * a mod27
        String ret = "";
        int code;
        for (int i = 0; i < input.length(); i++)
        {
            if (input.charAt(i) != ' ') {
                code = Constants.getIdx(input.charAt(i));
                int decode = Math.mod((code + desplazamiento) * decimacion);
                ret += Constants.ALFABETO[decode];
            }
        }
        output = Formatter.formatOutput(ret);
    }

    @Override
    public void descifrar() throws ParamsException
    {
        //(Ci – b) * inv(a, 27) mod27
        String ret = "";
        int code;
        for (int i = 0; i < input.length(); i++)
        {
            if (input.charAt(i) != ' ') {
                code = Constants.getIdx(input.charAt(i));
                int decode = Math.mod((code - desplazamiento) * Math.getInverse(decimacion, Constants.ALFABETO.length));
                ret += Constants.ALFABETO[decode];
            }
        }
        output = Formatter.formatOutput(ret);
    }

    @Override
    public void set(Object[] args)
    {
        args[0] = input;
        args[1] = output;
        args[2] = decimacion;
        args[3] = desplazamiento;
    }

    @Override
    public Object[] get()
    {
        return new Object[]{input, output, decimacion, desplazamiento};
    }

    public int getDecimacion()
    {
        return decimacion;
    }

    public void setDecimacion(int decimacion)
    {
        this.decimacion = decimacion;
    }

    public int getDesplazamiento()
    {
        return desplazamiento;
    }

    public void setDesplazamiento(int desplazamiento)
    {
        this.desplazamiento = desplazamiento;
    }

    public static void main(String[] args)
    {
        //Test Sustitucion afin, no simetrico??
        String t = "UV IW GZ VC DF ZN QV PD VN FZ CQ WD WP VB CS QO FC QW NI VN QW VP ZN EO DS QV PC KW FC QW GZ VP ON BO XM CQ VC BL VN PO WN CB LW EV MK WZ NC WM CP OW NG ZV VD ML VS WB LW BO MO CS WH EO CS BC FW CM OV LU WH CL WN MW NC DK CT CP QV PZ NW EO CS IV CN ZD WS IV CU LO HZ DW SI OK WN WL NW QO PO IZ DW BZ VP CZ NG ZV VD NW IH LV UV CP WI HL VG ZO VN WH LC CP OU OV NV ZN NW IH LV SV PV NW IH LV VP VD QV MK ZD WD CE VN FC NX CQ VQ WN IV NQ WB VQ LW IZ RW XP VM CT WL NC QC BL OI VL C";
        Object[] obj = {t, " ", 5, 2};
        try
        {
            SustitucionAfin a = new SustitucionAfin(obj);
            a.descifrar();
            String g = a.getOutput();
            System.out.println("Descifrado: " + g);
            Object[] obj2 = new Object[]{g," ", 5, 2};
            a = new SustitucionAfin(obj2);
            a.cifrar();
            System.out.println("Check: " + Formatter.replaceSpace(a.getOutput()).equals(t));
        } catch (ParamsException e) {
            System.out.println(e.getMessage());
        }
    }
}