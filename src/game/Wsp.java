package game;

public class Wsp{
    private final int x;
    private final int y;
    
    public Wsp(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public int get_x() { return x; }
    public int get_y() { return y; }
    
    @Override
    public String toString() {
        return x + " " + y;
    }
    
    public boolean equals(Object obj){
        Wsp temp;
        
        try{
            temp = (Wsp)(obj);
        }catch(Exception ex){
            temp = null;
        }
        
        if(temp != null) {
            if(x != temp.x) return false;
            if(y != temp.y) return false;
        
            return true;
        }else{
            return super.equals(obj);
        }
    }
}
