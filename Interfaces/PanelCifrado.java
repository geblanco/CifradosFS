package Interfaces;

import Cifrados.CifradoGenerico;
import Util.Constants;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.GroupLayout;

/**
 * Created by ivan on 24/04/15.
 */
class PanelCifrado extends JPanel {

    private CifradoGenerico cifrado;

    private JLabel desplazamientoPuro;
    private JLabel decimacionPura;
    private JLabel adicionMultiplicacion;
    private JLabel vigenere;
    private JLabel beaufort;
    private JLabel claveContinua;
    private JLabel vernam;
    private JLabel playfair;
    private JLabel hillDigramico;
    private JLabel transposicion;

    public PanelCifrado (CifradoGenerico cifrado) {
        this.cifrado = cifrado;

        desplazamientoPuro      = new JLabel ( Constants.CIF_DESPLAZAMIENTO_PURO );
        decimacionPura          = new JLabel ( Constants.CIF_DECIMACION_PURA );
        adicionMultiplicacion   = new JLabel ( Constants.CIF_ADICION_MULTIPLICACION );
        vigenere                = new JLabel ( Constants.CIF_VIGENERE );
        beaufort                = new JLabel ( Constants.CIF_BEAUFORT );
        claveContinua           = new JLabel ( Constants.CIF_CLAVE_CONTINUA );
        vernam                  = new JLabel ( Constants.CIF_VERNAM );
        playfair                = new JLabel ( Constants.CIF_PLAYFAIR );
        hillDigramico           = new JLabel ( Constants.CIF_HILL_DIGRAMICO );
        transposicion           = new JLabel ( Constants.CIF_TRANSPOSICION );

        this.add(desplazamientoPuro);
        this.add(decimacionPura);
        this.add(adicionMultiplicacion);
        this.add(vigenere);
        this.add(beaufort);
        this.add(claveContinua);
        this.add(vernam);
        this.add(playfair);
        this.add(hillDigramico);
        this.add(transposicion);

        GroupLayout distribuyeCifrados = new GroupLayout(this);
        this.setLayout(distribuyeCifrados);
        distribuyeCifrados.setAutoCreateGaps(true);
        distribuyeCifrados.setAutoCreateContainerGaps(true);

        distribuyeCifrados.setHorizontalGroup(
                distribuyeCifrados.createParallelGroup()
                .addComponent(desplazamientoPuro)
                .addComponent(decimacionPura)
                .addComponent(adicionMultiplicacion)
                .addComponent(vigenere)
                .addComponent(beaufort)
                .addComponent(claveContinua)
                .addComponent(vernam)
                .addComponent(playfair)
                .addComponent(hillDigramico)
                .addComponent(transposicion)
        );

        distribuyeCifrados.setVerticalGroup(
                distribuyeCifrados.createSequentialGroup()
                .addComponent(desplazamientoPuro)
                .addComponent(decimacionPura)
                .addComponent(adicionMultiplicacion)
                .addComponent(vigenere)
                .addComponent(beaufort)
                .addComponent(claveContinua)
                .addComponent(vernam)
                .addComponent(playfair)
                .addComponent(hillDigramico)
                .addComponent(transposicion)
        );
    }
}