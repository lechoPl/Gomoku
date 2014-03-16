package game;

import java.util.ArrayList;
import java.util.Random;

public class Gomoku {
    
    /**
     * pierwsza wspolrzedna to x
     * druga wspolrzedna to y
     * 
     * lewy gory rog to [0][0]
     * prawy gory rot do [size-1][0]
     * 
     * prawy dolny rog to [size-1][size-1]
     * lewy dolny rog to [0][size-1]
     */
    
    private FIELD[][] Plansza; // [x][y]
    private PLAYER aktualny_Gracz;
    private STAN_GRY stan_Gry;
    
    private FIELD pola_Gracza;
    
    private int size = 19;
    private int W_POLA = 5; // liczba pol ktore musza byc w jednej lini aby wygrac
    private int puste_pola;
    
    private Wsp[] ZwyciezkaLinia;
    //private DIR ZwyciezkiKierunek;
        
    public Gomoku(PLAYER gracz) {        
        Plansza = new FIELD[size][size];
        for(int x=0; x < size; x++){
            for(int y=0; y < size; y++)
                Plansza[x][y] = FIELD.F_EMPTY;            
        }
        
        puste_pola = size*size;
        
        Random rand = new Random();
        
        //losowanie gracza ktory startuje
        aktualny_Gracz = gracz;//PLAYER.values()[rand.nextInt(2)];
        
        if(aktualny_Gracz == PLAYER.P_HUMAN) pola_Gracza = FIELD.F_BLACK;
        else pola_Gracza = FIELD.F_WHITE;
        
        ZwyciezkaLinia = new Wsp[W_POLA];
        //ZwyciezkiKierunek = null;
        
        stan_Gry = STAN_GRY.S_MOVE;
    }
    
    public boolean Move(int x, int y) throws IllegalArgumentException {
        if(stan_Gry != STAN_GRY.S_MOVE) return false;
        
        if( x < 0 || y < 0) throw new IllegalArgumentException("bledne wspolrzedne pole");
        if(x >= size || y >= size) throw new IllegalArgumentException("bledne wspolrzedne pola");
        
        if(Plansza[x][y] != FIELD.F_EMPTY) return false;
        else{
            switch(aktualny_Gracz){
                case P_HUMAN: Plansza[x][y] = pola_Gracza; break;
                case P_COMP: Plansza[x][y] = (pola_Gracza == FIELD.F_BLACK ? FIELD.F_WHITE : FIELD.F_BLACK );
            }
            
            --puste_pola;
            return true;
        }            
    }
    
    public void ZmienGracza() {
        aktualny_Gracz = aktualny_Gracz == PLAYER.P_COMP ? PLAYER.P_HUMAN : PLAYER.P_COMP;
    }
    
    
    //sprawdzanie wygranej
    private boolean Sprawdz(Wsp wsp, DIR kierunek)
    {
        ArrayList<Wsp> odwiedzonePola = new ArrayList<Wsp>();
        
        ZwyciezkaLinia[0] = wsp;
        
        return Sprawdz_temp(wsp, kierunek, 1,odwiedzonePola);
    }
    
    private boolean Sprawdz_temp(Wsp wsp, DIR kierunek, int ile, ArrayList<Wsp> odwiedzonePola)
    {
        if(odwiedzonePola.contains(wsp) == true) return false;
        else odwiedzonePola.add(wsp);
        
        ZwyciezkaLinia[ile-1] = wsp;
        
        if(ile == W_POLA) {
            //ZwyciezkiKierunek = kierunek;
            return true;
        }
        
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
                return false;
        }
        
        if(Plansza[wsp.get_x()][wsp.get_y()] != Plansza[next_x][next_y]) return false;
        else{
            return Sprawdz_temp(new Wsp(next_x, next_y), kierunek, ile + 1, odwiedzonePola);             
        }
    }
    
    public boolean SprawdzKoniecGry() {
        if(stan_Gry != STAN_GRY.S_MOVE) return true;
        
        for(int x = 0; x < size; x++)
            for(int y=0; y < size; y++)
            {
                if(Plansza[x][y] == FIELD.F_EMPTY) continue;
                
                for(int i=0; i < 8; i++)
                    if( Sprawdz(new Wsp(x,y),DIR.values()[i]) ){
                        stan_Gry = STAN_GRY.S_WON;
                        return true;
                    }
            }
        
        if(puste_pola == 0)
        {
            stan_Gry = STAN_GRY.S_DRAW;
            return true;
        }
        
        return false;
    }
    
    public FIELD get_Field(int x, int y) throws IllegalArgumentException {
        if( x < 0 || y < 0) throw new IllegalArgumentException("bledne wspolrzedne pole");
        if(x >= size || y >= size) throw new IllegalArgumentException("bledne wspolrzedne pola");
        
        return Plansza[x][y];
    }
    
    public PLAYER get_current_Player(){
        return aktualny_Gracz;
    }
    
    public STAN_GRY get_stan_gry(){
        return stan_Gry;
    }
    
    public FIELD[][] get_Plansza(){
        return Plansza;
    }
    
    public FIELD getCompField(){
        return pola_Gracza == FIELD.F_BLACK ? FIELD.F_WHITE : FIELD.F_BLACK;
    }
    
    public int getIleW(){
        return W_POLA;
    }
    
    public Wsp[] getZwyciezkaLinia(){
        if( stan_Gry != STAN_GRY.S_WON ) return null;
        
        return ZwyciezkaLinia;
    }
}
