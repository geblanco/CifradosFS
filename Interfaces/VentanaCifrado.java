package Interfaces;


import Cifrados.CifradoGenerico;

import javax.swing.*;

/**
 * Created by ivan on 24/04/15.
 */
public class VentanaCifrado extends JFrame {

    public VentanaCifrado (CifradoGenerico cifrado) {
        this.setContentPane(new PanelCifrado(cifrado));
        this.setTitle("Cifrados Clasicos");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocation(200,200);
        this.setSize(400,400);
        this.setResizable(false);
        this.setVisible(true);
    }
}
