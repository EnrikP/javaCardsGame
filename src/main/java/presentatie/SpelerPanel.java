package presentatie;

import data.DataLayerJDBC;
import logica.Speler;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Deze klasse bevat de grafische voorstelling van een speler.
 * Deze voorstelling toont de naam van de speler en de handkaarten. Voor deze handkaarten wordt een aparte klasse geschreven.
 * @author
 */
public class SpelerPanel {
    private JPanel spelerPanel;
    private JPanel kaartenPanel;
    private JLabel naamLabel;


    private ArrayList<Speler> spelers;

    DataLayerJDBC dataLayer = new DataLayerJDBC("pandemie", true);



    public SpelerPanel(int nummer) throws SQLException {
        naamLabel.setText("Speler "+nummer);

    }

    public JPanel getKaartenPanel() {
        return kaartenPanel;
    }

    public JPanel getSpelerPanel() {
        return spelerPanel;
    }

    public void addKaart(String naamStad) throws SQLException {
        kaartenPanel.add(new Kaart().getStadPanel(naamStad)).revalidate();
    }

}


