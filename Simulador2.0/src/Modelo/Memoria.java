package Modelo;

public class Memoria {
    static int tamMax = 1000;
    static int marco = 20;
    private int aux =0;
    public float paginas(int n){        
        aux =(int) Math.floor(n);
        return  Math.round(n/ marco);
    }
    public int pocisionF(int filas){
        return (int) (Math.random() * filas) ;
    }
    public int pocisionC(int n){
        return (int) (Math.random() * n) ;
    }

    public Memoria() {
    }
    
}
    

