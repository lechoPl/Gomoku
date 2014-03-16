package gui;

import game.FIELD;
import game.Gomoku;
import game.Wsp;
import java.awt.*;
import javax.swing.*;

public class Plansza extends JPanel {
    private FIELD[][] Plansza;
    private Gomoku Game;
    
    private int sizeSquare_x;
    private int sizeSquare_y;
    
    private int positionX = 0;
    private int positionY = 0;
    
    private int liczbaKratek = 0;
    
    private Color CLini = Color.black;
    private Color COznaczen = Color.black;
    private Color CPlanszy = new Color(185,138,91);
    private Color CPBlack = Color.black;
    private Color CPWhite = Color.white;
    private Color CSkreslenie = Color.red;
    
    private boolean OznaczeniaON = true;
    private boolean SkresleniaON = true;
    
    public Plansza(FIELD[][] Plansza, Gomoku game) {
        this.Plansza = Plansza;
        this.Game = game;
        
        this.setFocusable(true);
    }
    
    public Wsp getField(int x, int y){
        if(x < positionX + sizeSquare_x ||
           y < positionY + sizeSquare_y)
            return null;
        
        x = (x - positionX) / sizeSquare_x;
        y = (y - positionY) / sizeSquare_y;
        
        if(x >= liczbaKratek || y >= liczbaKratek)
            return null;
        
        return new Wsp(x - 1, y - 1);
    }
    
    
 
    @Override
    public void paint(Graphics graphics){
        liczbaKratek = Plansza.length+1;
        
        sizeSquare_x = (this.getSize().width < this.getSize().height ?
                this.getSize().width : this.getSize().height)
                / liczbaKratek;
        
        
        sizeSquare_y = (this.getSize().width < this.getSize().height ?
                this.getSize().width : this.getSize().height)
                / liczbaKratek;
        
        
        positionX  = (this.getSize().width - liczbaKratek * sizeSquare_x)/2;
        positionY  = (this.getSize().height - liczbaKratek * sizeSquare_x)/2;
        
        super.paint(graphics);
        
        drawBoard(graphics);
        drawPionki(graphics);
        drawSkreslenie(graphics);
    }
    
    void drawPionki(Graphics graphics){
        for(int x = 1; x < Plansza.length + 1; ++x){
            for(int y = 1; y < Plansza.length + 1; ++y){
                if(Plansza[x-1][y-1] == FIELD.F_EMPTY){
                    continue;
                }
                else if(Plansza[x-1][y-1] == FIELD.F_BLACK) {
                    graphics.setColor(CPBlack);
                }
                else if(Plansza[x-1][y-1] == FIELD.F_WHITE) {
                    graphics.setColor(CPWhite);
                }
                       
             
                int temp = (int)(sizeSquare_x * 0.2);
                
                //dopisz rozne figury
                graphics.fillOval((sizeSquare_y * y) + positionX + temp/2,
                        (sizeSquare_x * x + temp/2) + positionY, sizeSquare_y - temp,
                        sizeSquare_x - temp);
                       
                graphics.setColor(Color.black);
                       
                graphics.drawOval((sizeSquare_y * y) + positionX + temp/2,
                        (sizeSquare_x * x + temp/2) + positionY, sizeSquare_y - temp,
                        sizeSquare_x - temp);
                       /*graphics.fillRect((sizeSquare_y * y) + positionX,
                            (sizeSquare_x * x) + positionY, sizeSquare_y,
                            sizeSquare_x);
                       */    
            }    
        }        
    }
    
    void drawBoard(Graphics graphics){
        //super.paint(graphics);
        graphics.setColor(CPlanszy);
        graphics.fillRect(positionX,positionY, 
                sizeSquare_x*liczbaKratek,  sizeSquare_y*liczbaKratek);
        
        graphics.setColor(Color.black);
        graphics.drawRect(positionX,positionY, 
                sizeSquare_x*liczbaKratek,  sizeSquare_y*liczbaKratek);
        
        //Podpisywanie osi x i y
        if(OznaczeniaON){
            for(int i = 1; i < liczbaKratek; ++i){
                graphics.setColor(COznaczen);
                int rozmiar = (int)(sizeSquare_x*0.8);
                int przesuniecie = sizeSquare_x - rozmiar;
                graphics.setFont(new Font("Dialog", Font.BOLD, rozmiar));
            
                char litera = (char) ((int)('A')+(i-1));
                graphics.drawString( String.valueOf(litera),
                        positionX + sizeSquare_x * i + przesuniecie,
                        positionY + sizeSquare_y);
            
                int przesuniecie_x = 0;
                if(i < 10) przesuniecie_x = (int)(sizeSquare_x * 0.5);
                else if(i < 100) przesuniecie_x = sizeSquare_x - rozmiar;
            
                graphics.drawString(String.valueOf(i), positionX + przesuniecie_x,
                        positionY + sizeSquare_y * (i+1)- przesuniecie);
            }
        }
        
        for(int i = 1; i < liczbaKratek; ++i){
            graphics.setColor(CLini);
            
            //rysowanie lini x
            int x1 = positionX + i * sizeSquare_x + sizeSquare_x/2;
            int x2 = x1;
            int y1 = positionY + (int)(sizeSquare_y * 1.5);
            int y2 = positionY + (int)(sizeSquare_y * (liczbaKratek-0.5));
            graphics.drawLine(x1, y1, x2, y2);
            
            //rysowanie lini y
            x1 = positionX + (int)(sizeSquare_x * 1.5);
            x2 = positionX + (int)(sizeSquare_x * (liczbaKratek - 0.5));
            y1 = positionY + i * sizeSquare_y + sizeSquare_y/2;
            y2 = y1;
            graphics.drawLine(x1, y1, x2, y2);
        }
    }
    
    void drawSkreslenie(Graphics graphics){
        if(!SkresleniaON) return;
        
        Wsp[] punkty = Game.getZwyciezkaLinia(); 
        
        if(punkty == null) return;
        
        graphics.setColor(CSkreslenie);
        
        Wsp p1 = punkty[0];
        Wsp p2 = punkty[1];
        
        int x1; int y1;
        int x2; int y2;
        
        for(int i = 0; i < Game.getIleW()-1; ++i){
            if(Math.abs(punkty[i].get_y() - punkty[i+1].get_y()) > Game.getIleW()) continue;
            if(Math.abs(punkty[i].get_x() - punkty[i+1].get_x()) > Game.getIleW()) continue;
            
            x1 = positionX + (punkty[i].get_y()+1) * sizeSquare_x + sizeSquare_x/2;
            y1 = positionY + (punkty[i].get_x()+1) * sizeSquare_y + sizeSquare_y/2;
            
            x2 = positionX + (punkty[i+1].get_y()+1) * sizeSquare_x + sizeSquare_x/2;
            y2 = positionY + (punkty[i+1].get_x()+1) * sizeSquare_y + sizeSquare_y/2;
            
            graphics.drawLine(x1, y1, x2, y2);
        }
    }
    
    public void setCLini(Color c) { CLini = c;}
    public void setCOznaczen(Color c) { COznaczen = c;}
    public void setCPlanszy(Color c) { CPlanszy = c;}
    public void setCPBlack(Color c) { CPBlack = c;}
    public void setCPWhite(Color c) { CPWhite = c;}
    public void setCSkreslenie(Color c) { CSkreslenie = c;}
        
    public Color getCLini() { return CLini;}
    public Color getCOznaczen() { return COznaczen;}
    public Color getCPlanszy() { return CPlanszy;}
    public Color getCPBlack() { return CPBlack;}
    public Color getCPWhite() { return CPWhite;}
    public Color getCSkreslenie() { return CSkreslenie;}
    
    public void setOznaczeniaON(boolean b) {OznaczeniaON = b; }
    public boolean getOznaczeniaON() { return OznaczeniaON; }
    
    public void setSkresleniaON(boolean b) {SkresleniaON = b; }
    public boolean getSkresleniaON() { return SkresleniaON; }
    
    public void setPlansza(FIELD[][] Plansza) {
        this.Plansza = Plansza;
    }
    
    public void setGame(Gomoku Game) {
        this.Game = Game;
    }
}
