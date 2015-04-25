package Interfaces;

import Cifrados.CifradoGenerico;

/**
 * Created by ivan on 24/04/15.
 */
class AplicacionCifradoClasico {
    public AplicacionCifradoClasico () {
        new VentanaCifrado (new CifradoGenerico(" ", " "));
    }

    public static void main (String[] args) {
        new AplicacionCifradoClasico();
    }
}