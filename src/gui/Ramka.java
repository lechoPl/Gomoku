package gui;

import game.Computer;
import game.Gomoku;
import game.PLAYER;
import game.STAN_GRY;
import game.Wsp;
import java.awt.*;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.event.*;

/*
 *  Twoja gra powinna być maksymalnie odporna na niedoświadczonego użytkownika. Akcje związane z rozpoczęciem
    nowej gry i z zakończeniem działania całej aplikacji mają mieć przypisane skróty klawiaturowe
    (KeyStroke) postaci odpowiednio ctrl-N i ctrl-X. Zadbaj też o estetyczny wygląd aplikacji!
 */

public class Ramka extends JFrame{    
    private GMenuBar menuBar = new GMenuBar(this);
    private Plansza plansza;
    private JLabel napis;
    
    private Gomoku game;
    private Computer comp;
    
    private PLAYER player_start = PLAYER.P_HUMAN;
    
    public void setStartPlayer(PLAYER p) { player_start = p; }
    
    private MouseInputListener mausList = new MouseInputAdapter(){
        @Override
        public void mousePressed(MouseEvent e) {
            if(game.get_stan_gry() != STAN_GRY.S_MOVE) return;
            
            Wsp test = (( Plansza )( e.getSource() )).getField( e.getX(), e.getY() );
            
            if(test == null) return;
            if(game.get_current_Player() != PLAYER.P_HUMAN) return;
            if(! game.Move(test.get_y(), test.get_x()) ) return;
            
            //((Plansza)(e.getSource())).repaint();
            RPPlansza();
            
            if(game.SprawdzKoniecGry()) {
                setWinNapis();
                return;
            }
            
            game.ZmienGracza();
            setNextPlayerNapis();            
            
            //dodaj chile prerwy
            
            Wsp temp = comp.WykonajRuch();
            game.Move(temp.get_x(), temp.get_y());
            
            RPPlansza();
            
            if(game.SprawdzKoniecGry()) {
                setWinNapis();
                return;
            }
            
            game.ZmienGracza();
            setNextPlayerNapis();
                    
        }
    };
    
    private void setNextPlayerNapis() {
        napis.setText(
                    "Ruch wykonuje: " +
                    (game.get_current_Player() == PLAYER.P_COMP ?
                    "KOMPUTER" : "GRACZ") );        
    }
    
    private void setWinNapis() {
        String str_temp = "";
                
                switch(game.get_stan_gry()){
                    case S_DRAW:
                        str_temp = "REMIS !";
                        break;
                        
                    case S_WON:
                        str_temp = "WYGRYWA " + 
                                (game.get_current_Player() == PLAYER.P_COMP ?
                                "KOMPUTER" : "GRACZ") + " !";
                        break;                        
                }
                napis.setText(str_temp);
                
                JOptionPane.showMessageDialog(this, str_temp, "Konie gry", JOptionPane.PLAIN_MESSAGE);
    }
    
    public Ramka(){
        super("Gomoku Game");
        
        NowaGra();
                
        //game = new Gomoku(player_start);
        //comp = new Computer(game.get_Plansza(), game.getCompField(), game.getIleW());
        
        
        int szer = 600;
        int wys = 600;
        
        this.setSize(szer, wys);
        this.setMaximumSize(new Dimension(100,100));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((d.width - szer)/2, (d.height - wys)/2);
        
        this.setLayout(new BorderLayout());        
        
        //------- MENU ------
        this.add(menuBar, BorderLayout.NORTH);
        
        //----- PLANSZA -------
        
        //plansza = new Plansza(game.get_Plansza());
        //plansza.addMouseListener(mausList);
        
        this.add(plansza, BorderLayout.CENTER);
        
        //----- INFO ----------
        JPanel panel_2 = new JPanel();
        this.add(panel_2, BorderLayout.SOUTH);
        
        panel_2.setLayout(new FlowLayout());
        
        
        /*napis = new JLabel("Ruch wykonuje: " +
                    (game.get_current_Player() == PLAYER.P_COMP ?
                    "KOMPUTER" : "GRACZ") );
        */
        
        panel_2.add(napis);
        
        //------- END -------
        
        
        /*if(game.get_current_Player() == PLAYER.P_COMP){
            Wsp temp = comp.WykonajRuch();
            game.Move(temp.get_x(), temp.get_y());
            game.ZmienGracza();
            
            setNextPlayerNapis();
        }*/
        
        
        this.setVisible(true);
        //new UstawieniaKoloru(this, new Color(255,0,0));
    }
    
    public Gomoku getGame(){
        return  game;
    }
    
    public void NowaGra(){
        game = new Gomoku(player_start);
        comp = new Computer(game.get_Plansza(), game.getCompField(), game.getIleW());
        
        if(plansza != null) {
            plansza.setPlansza(game.get_Plansza());
            plansza.setGame(game);
        }
        else{
            plansza = new Plansza(game.get_Plansza(), game);
            plansza.addMouseListener(mausList);
        }
        
        if(napis != null) setNextPlayerNapis();
        else {
            napis = new JLabel("Ruch wykonuje: " +
                    (game.get_current_Player() == PLAYER.P_COMP ?
                    "KOMPUTER" : "GRACZ") );
        }
                
        if(game.get_current_Player() == PLAYER.P_COMP){
            /*try
            {
                Thread.sleep(100);
            }
            catch(InterruptedException e)
            {
            }*/
            
            Wsp temp = comp.WykonajRuch();
            game.Move(temp.get_x(), temp.get_y());
            game.ZmienGracza();
            
            setNextPlayerNapis();
        }
        
        
        this.RPPlansza();        
    }
    
    public void RPPlansza(){
        plansza.repaint();
    }
    
    public void setOznaczenia(boolean b) { plansza.setOznaczeniaON(b); }
    public Plansza getPlansza(){ return plansza; }
}
