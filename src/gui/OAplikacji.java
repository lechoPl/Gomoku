package gui;

import javax.swing.JOptionPane;

public class OAplikacji{
    public OAplikacji(Ramka main){
        JOptionPane.showMessageDialog(main,
                "Autor:\n"
                + "     Michał Lech\n"
                + "Versja:\n"
                + "     1.0 beta\n"
                + "     2012-12-13\n",
                "O aplikacji",
                JOptionPane.PLAIN_MESSAGE);
    }
}
