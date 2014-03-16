package game;

import java.util.ArrayList;
import java.util.Random;

public class Computer {
    private FIELD[][] Plansza;
    private int size;
    
    private FIELD wlasnePola;
    
    private int m_ile = 0;
    private int m_ile2 = 0;
    private int W_POLA = 0;
    
    private int postawione_pionki;
    
    private Random rand;
    
    
    public Computer(FIELD[][] p, FIELD rodzajPola, int wP) {
        rand = new Random();
        
        Plansza = p;
        size = Plansza.length;
        
        wlasnePola = rodzajPola;
        W_POLA = wP;
        
        postawione_pionki = 0;
    }
    
    private Wsp genNextWsp(Wsp wsp, DIR kierunek){
        int next_x, next_y;
        
        switch(kierunek){
            case D_U:
                next_y = (wsp.get_y() == 0 ? next_y = size-1 : wsp.get_y() - 1);
                next_x = wsp.get_x();
                break;
                
            case D_UR:
                next_y = (wsp.get_y() == 0 ? size - 1 : wsp.get_y() - 1);
                next_x = (wsp.get_x() == size - 1 ? 0 : wsp.get_x() + 1 );
                break;
                
            case D_R:
                next_y = wsp.get_y();
                next_x = (wsp.get_x() == size - 1 ? 0 : wsp.get_x() + 1 );
                break;
                
            case D_DR: 
                next_y = (wsp.get_y() == size - 1 ? 0 : wsp.get_y() + 1);
                next_x = (wsp.get_x() == size - 1 ? 0 : wsp.get_x() + 1 );
                break;
                
            case D_D: 
                next_y = (wsp.get_y() == size - 1 ? 0 : wsp.get_y() + 1);
                next_x = wsp.get_x();
                break;
                
            case D_DL: 
                next_y = (wsp.get_y() == size - 1? 0 : wsp.get_y() + 1);
                next_x = (wsp.get_x() == 0 ? size - 1 : wsp.get_x() - 1 );
                break;
                
            case D_L: 
                next_y = wsp.get_y();
                next_x = (wsp.get_x() == 0 ? size - 1 : wsp.get_x() - 1 );
                break;
                
            case D_UL: 
                next_y = (wsp.get_y() == 0 ? size - 1 : wsp.get_y() - 1);
                next_x = (wsp.get_x() == 0 ? size - 1 : wsp.get_x() - 1 );
                break;
            default:
                return null;
        }
        
        return new Wsp(next_x,next_y);
    }
    
    private Wsp Sprawdz_temp(Wsp wsp, DIR kierunek, int ile, ArrayList<Wsp> odwiedzonePola)
    {
        if(odwiedzonePola.contains(wsp) == true) return null;
        else odwiedzonePola.add(wsp);
        
        /*if(ile == m_ile){
            return wsp;
        }*/
        //System.out.println(Plansza[wsp.get_x()][wsp.get_y()]);
        
        Wsp nextWsp = genNextWsp(wsp, kierunek);
        int next_x = nextWsp.get_x(), next_y = nextWsp.get_y();
        
        
        if(Plansza[wsp.get_x()][wsp.get_y()] != Plansza[next_x][next_y]){
            if(ile < W_POLA ){ // m_ile == 3
                if(Plansza[next_x][next_y] == FIELD.F_EMPTY){
                    Wsp nextWsp2 = genNextWsp(nextWsp, kierunek);
                    
                    if(Sprawdz_po_przerwie(nextWsp2, kierunek, ile + 1, 
                                odwiedzonePola, Plansza[wsp.get_x()][wsp.get_y()])){
                            return nextWsp;
                        }
                }
            }
            
            if(m_ile != ile){    
                return null;
            }
            
            //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            //m_ile == ile // trzy kolejne pola
            if(Plansza[next_x][next_y] == FIELD.F_EMPTY) return new Wsp(next_x, next_y);
            
            
            return null;
        }
        else{
            //sprawdzanie ciaglej lini do trzech pol
            return Sprawdz_temp(new Wsp(next_x, next_y), kierunek, ile + 1, odwiedzonePola);             
        }
    }
    
    private boolean Sprawdz_po_przerwie(Wsp wsp, DIR kierunek, int ile, ArrayList<Wsp> odwiedzonePola, FIELD typ){
        if(odwiedzonePola.contains(wsp) == true) return false;
        else odwiedzonePola.add(wsp);
        
        if(ile == W_POLA-1){
            return true;
        }
        
        //int next_x, next_y;
        Wsp nextWsp = genNextWsp(wsp, kierunek);
        int next_x = nextWsp.get_x(), next_y = nextWsp.get_y();
        
        
        if(Plansza[next_x][next_y] != typ){
            return false;
        }
        else{
            return Sprawdz_po_przerwie(new Wsp(next_x, next_y), kierunek, ile + 1, odwiedzonePola, typ);             
        }
        
        
    }
 
    private Wsp szukaj_kolejnych(FIELD rodzaj, int ile){
        
        ArrayList<Wsp> odwiedzonePola;
        ArrayList<Integer> wybraneKierunki;// = new ArrayList<Integer>();
        
        m_ile = ile;
        
        Wsp temp;
        int kierunek;
        
        for(int x = 0; x < size; ++x ) {
            for(int y = 0; y < size; ++y) {
                
                if(Plansza[x][y] != rodzaj) continue;
               
                wybraneKierunki = new ArrayList<Integer>();
                
                do{
                    kierunek = rand.nextInt(8);
                    
                    if(wybraneKierunki.contains(kierunek)) continue;
                    
                    wybraneKierunki.add(kierunek);
                    
                    odwiedzonePola = new ArrayList<Wsp>();
                    
                    temp = Sprawdz_temp(new Wsp(x,y),DIR.values()[kierunek], 1, odwiedzonePola);
                    
                    if( temp != null ){
                        return temp;
                    }
                }while( wybraneKierunki.size() != 8);
                
                
                /*
                for(int i=0; i <8; ++i){
                    odwiedzonePola = new ArrayList<Wsp>();
                    
                    temp = Sprawdz_temp(new Wsp(x,y),DIR.values()[i], 1, odwiedzonePola);
                    
                    if( temp != null ){
                        return temp;
                    }
                }*/
            }
        }
        return null;
    }
    
    public Wsp WykonajRuch(){       
        Wsp temp;
        
        //wysukanie trzech kolejnych pol albo 5 z przerwa
        temp = szukaj_kolejnych((wlasnePola == FIELD.F_BLACK ? 
                FIELD.F_WHITE : FIELD.F_BLACK),
                W_POLA-2);
        
        //sprawdz czy przeciwnik moze wygrac
        if(temp != null) {
            return temp;
        }
        
        for(int i = W_POLA - 1; i > 0; --i) {
            temp = szukaj_kolejnych(wlasnePola, i);
        
            if(temp != null) return temp;
        }
        
        Random rand = new Random();
        return new Wsp(rand.nextInt(size), rand.nextInt(size)); 
    }
}
