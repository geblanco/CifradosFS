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

    public static final String TRANS_PARAM_ERR = "Para el cifrado por transposicion es neceserio un al menos un valor entero para el numero de columnas/filas";
    public static final String VIG_PARAM_ERR = "Para el cifrado de Vigenere son necesarios el texto y la clave";
    public static final String SIMAFIN_PARAM_ERR = "Para el cifrado simetrico afin es necesario el texto y las constantes de decimacion y de desplazamiento";
    public static final String OP_NO_CONOCIDA = "Tipo de operacion no conocida";
}
