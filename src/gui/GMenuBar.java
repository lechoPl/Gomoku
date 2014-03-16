package gui;

import game.PLAYER;
import java.awt.Color;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GMenuBar extends JMenuBar {
    Ramka main;
    
    private class NewGameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            main.NowaGra();
        }
    }
    
    private class KoniecListListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            main.dispose();
        }
    }
    
    private class UserListener implements ChangeListener{
        @Override
        public void stateChanged(ChangeEvent e) {
            JRadioButton temp = (JRadioButton)e.getSource();
            if( temp.isSelected() == true){
                if(temp == zProgram) main.setStartPlayer(PLAYER.P_COMP);
                
                if(temp == zUzytkownik) main.setStartPlayer(PLAYER.P_HUMAN);
            }
        }  
    }
    
    private class CheckListener implements ChangeListener{
        public void stateChanged(ChangeEvent e) {
            JCheckBox temp = (JCheckBox)e.getSource();
            
            if( temp.isSelected() == true){
                if(temp == OzP){
                    main.setOznaczenia(true);
                    main.RPPlansza();
                }
                
                if(temp == SkP){
                    main.getPlansza().setSkresleniaON(true);
                    main.RPPlansza();                    
                }
            }else{
                if(temp == OzP){
                    main.setOznaczenia(false);
                    main.RPPlansza();
                }
                
                if(temp == SkP){
                    main.getPlansza().setSkresleniaON(false);
                    main.RPPlansza();                    
                }
            }
        }
    }
    
    private class MenuActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JMenuItem source = (JMenuItem)e.getSource();
            Color temp;
            
            if(source == CPBlack){
                temp = JColorChooser.showDialog(main, 
                            "Wybieranie Koloru",
                            main.getPlansza().getCPBlack());
                main.getPlansza().setCPBlack(temp);
            }
            
            if(source == CPWhite){
                temp = JColorChooser.showDialog(main, 
                            "Wybieranie Koloru",
                            main.getPlansza().getCPWhite());
                main.getPlansza().setCPWhite(temp);
            }
            
            if(source == CLinie){
                temp = JColorChooser.showDialog(main, 
                            "Wybieranie Koloru",
                            main.getPlansza().getCLini());
                main.getPlansza().setCLini(temp);
            }
            
            if(source == COznaczenia){
                temp = JColorChooser.showDialog(main, 
                            "Wybieranie Koloru",
                            main.getPlansza().getCOznaczen());
                main.getPlansza().setCOznaczen(temp);
            }
            
            if(source == CPlanszy){
                temp = JColorChooser.showDialog(main, 
                            "Wybieranie Koloru",
                            main.getPlansza().getCPlanszy());
                main.getPlansza().setCPlanszy(temp);
            }
            
            if(source == CSkresleniePol){
                temp = JColorChooser.showDialog(main, 
                            "Wybieranie Koloru",
                            main.getPlansza().getCSkreslenie());
                main.getPlansza().setCSkreslenie(temp);
            }
                    
            main.RPPlansza();
        }
    } 
    
    private class OGrzeActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new OGrze(main);
        }
    }
    
    private class OAplikacjiActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new OAplikacji(main);
        }
    }
    
    NewGameListener NGList = new NewGameListener();
    KoniecListListener KoniecList = new KoniecListListener();
    UserListener setStartList = new UserListener();
    CheckListener CheckL = new CheckListener();
    MenuActionListener SCL = new MenuActionListener();
    OGrzeActionListener OGL = new OGrzeActionListener();
    OAplikacjiActionListener OAL = new OAplikacjiActionListener();
    /*
        Na końcu ostatnie menu Pomoc (dosunięte do prawego brzegu okna) z dwiema opcjami: O grze (opis
        zasad gry) i O aplikacji (informacje o autorze programu, jego wersji, dacie powstania, itp)—wybór jednej
        z nich powinien powodować pojawienie się dialogowego okienka modalnego z odpowiednimi informacjami.
     */
    private JMenu m_gra;
    private JMenuItem NowaGra;
    private JMenuItem Koniec;
    
    private JMenu m_ustawienia;
    private JCheckBox OzP;
    private JCheckBox SkP;
    private JMenu m_ust_zaczyna;
    private ButtonGroup group;
    private JRadioButton zProgram;
    private JRadioButton zUzytkownik;
    
    private JMenu m_ust_kolor;
    private JMenuItem CPBlack;
    private JMenuItem CPWhite;
    private JMenuItem CLinie;
    private JMenuItem COznaczenia;
    private JMenuItem CPlanszy;
    private JMenuItem CSkresleniePol;
    
    
    private JDialog dialog;
    private JColorChooser colorChooser;
    
    
    private JMenu m_pomoc;
    private JMenuItem OGrze;
    private JMenuItem OAplikacji;
    
    
    public GMenuBar(Ramka contener) {
        main = contener;
        
        //---- GRA ----
        m_gra = new JMenu("Gra");
        
        NowaGra = new JMenuItem("NowaGra");
        NowaGra.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.CTRL_MASK));
        NowaGra.addActionListener(NGList);
        
        Koniec = new JMenuItem("Koniec");
        Koniec.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, Event.CTRL_MASK));
        Koniec.addActionListener(KoniecList);
        
        
        
        m_gra.add(NowaGra);
        m_gra.addSeparator();
        m_gra.add(Koniec);
        
        //----- USTAWIENIA -----
        m_ustawienia = new JMenu("Ustawienia");
        
        //JMenuItem Ust1 = new JMenuItem("ust1");
        OzP = new JCheckBox("Oznaczenie pol");
        OzP.setSelected(true);
        OzP.addChangeListener(CheckL);
        m_ustawienia.add(OzP);
        
        SkP = new JCheckBox("Skreślenie pol");
        SkP.setSelected(true);
        SkP.addChangeListener(CheckL);
        m_ustawienia.add(SkP);
        
        //-------------USTAWIENIA Zaczyna ----------
        m_ust_zaczyna = new JMenu("Zaczyna");
        m_ustawienia.add(m_ust_zaczyna);
        
        group = new ButtonGroup();
        
        zProgram = new JRadioButton("Program");
        zProgram.addChangeListener(setStartList);
        group.add(zProgram);
        m_ust_zaczyna.add(zProgram);
        
        zUzytkownik = new JRadioButton("Użytkownik");
        zUzytkownik.addChangeListener(setStartList);
        
        group.add(zUzytkownik);
        m_ust_zaczyna.add(zUzytkownik);
        
        group.setSelected(zUzytkownik.getModel(), true);
        
        //--- ust koloru
        m_ust_kolor = new JMenu("Kolor");
        m_ustawienia.add(m_ust_kolor);
        
        CPBlack = new JMenuItem("Piony czarne");
        CPBlack.addActionListener(NGList);
        CPBlack.addActionListener(SCL);
        m_ust_kolor.add(CPBlack);
        
        CPWhite = new JMenuItem("Piony białe");
        CPWhite.addActionListener(SCL);
        m_ust_kolor.add(CPWhite);
        
        CLinie = new JMenuItem("Linie planszy");
        CLinie.addActionListener(SCL);
        m_ust_kolor.add(CLinie);
        
        COznaczenia = new JMenuItem("Oznaczenia Planszy");
        COznaczenia.addActionListener(SCL);
        m_ust_kolor.add(COznaczenia);
        
        CPlanszy = new JMenuItem("Plansza");
        CPlanszy.addActionListener(SCL);
        m_ust_kolor.add(CPlanszy);
        
        CSkresleniePol = new JMenuItem("SkresleniePol");
        CSkresleniePol.addActionListener(SCL);
        m_ust_kolor.add(CSkresleniePol);
        
        //----- POMOC ----
        m_pomoc = new JMenu("Pomoc");
        
        OGrze = new JMenuItem("O grze");
        OGrze.addActionListener(OGL);
        
        OAplikacji = new JMenuItem("O aplikacji");
        OAplikacji.addActionListener(OAL);
        
        m_pomoc.add(OGrze);
        m_pomoc.add(OAplikacji);
        
        this.add(m_gra);
        this.add(m_ustawienia);        
        this.add(Box.createHorizontalGlue());
        this.add(m_pomoc);
        
        this.setVisible(true);
    }

}
