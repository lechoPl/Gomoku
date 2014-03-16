package gui;

import javax.swing.JOptionPane;

public class OGrze{
    public OGrze(Ramka main){
        JOptionPane.showMessageDialog(main,
                "Gra Gomoku\n\n"
                + "Gracze zajmują pola na przemian,dążąc do objęcia pięciu pól\n"
                + "w jednej linii. Do zajęcia pola używa się pionów (jeden z graczy\n"
                + "używa pionów czarnych a drugi białych). Pole może być zajęte\n"
                + "tylko przez jednego gracza i nie zmienia swego właściciela do\n"
                + "końca gry. Grę rozpoczyna grający czarnymi pionami.\n"
                + "\n"
                + "*Plansza jest zawinięta(połączona przeciwległymi bokami).\n",
                "O Grze",
                JOptionPane.PLAIN_MESSAGE);
    }
}
