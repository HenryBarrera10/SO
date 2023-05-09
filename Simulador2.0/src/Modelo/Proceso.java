package Modelo;


public class Proceso {
    private int id,tamano;
    private String nombre,estado; 
    private boolean r1,r2,r3,r4,r5;
    private boolean re1,re2,re3,re4,re5;
    private float numPaginas;
    private Micro_procesos h1, h2, h3;
    private int tamaptem;

    public Proceso() {
        this.id = 0;
        this.tamano = 0;
        this.nombre = "";
        this.estado = "";
        this.r1 = false;
        this.r2 = false;
        this.r3 = false;
        this.r4 = false;
        this.r5 = false;
        this.re1 = false;
        this.re2 = false;
        this.re3 = false;
        this.re4 = false;
        this.re5 = false;
        this.numPaginas=0;
        this.h1=new Micro_procesos();
        this.h2=new Micro_procesos();
        this.h3=new Micro_procesos();
        this.tamaptem=0;
        
    
    }
    

    public Proceso(int id, int tamano, String nombre, String estado, boolean re1, boolean re2, boolean re3, boolean re4, boolean re5, float numPaginas, int tamaptem) {
        this.id = id;
        this.tamano = tamano;
        this.nombre = nombre;
        this.estado = estado;
        this.r1 = r1;
        this.r2 = r2;
        this.r3 = r3;
        this.r4 = r4;
        this.r5 = r5;
        this.re1 = re1;
        this.re2 = re2;
        this.re3 = re3;
        this.re4 = re4;
        this.re5 = re5;
         this.numPaginas=numPaginas;
        this.tamaptem=tamaptem;
    }

    public int getTamaptem() {
        return tamaptem;
    }

    public void setTamaptem(int tamaptem) {
        this.tamaptem = tamaptem;
    }
    
    

    public boolean isRe1() {
        return re1;
    }

    public void setRe1(boolean re1) {
        this.re1 = re1;
    }

    public boolean isRe2() {
        return re2;
    }

    public void setRe2(boolean re2) {
        this.re2 = re2;
    }

    public boolean isRe3() {
        return re3;
    }

    public void setRe3(boolean re3) {
        this.re3 = re3;
    }

    public boolean isRe4() {
        return re4;
    }

    public void setRe4(boolean re4) {
        this.re4 = re4;
    }

    public boolean isRe5() {
        return re5;
    }

    public void setRe5(boolean re5) {
        this.re5 = re5;
    }

    public boolean isR1() {
        return r1;
    }

    public void setR1(boolean r1) {
        this.r1 = r1;
    }

    public boolean isR2() {
        return r2;
    }

    public void setR2(boolean r2) {
        this.r2 = r2;
    }

    public boolean isR3() {
        return r3;
    }

    public void setR3(boolean r3) {
        this.r3 = r3;
    }

    public boolean isR4() {
        return r4;
    }

    public void setR4(boolean r4) {
        this.r4 = r4;
    }

    public boolean isR5() {
        return r5;
    }

    public void setR5(boolean r5) {
        this.r5 = r5;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTamano() {
        return tamano;
    }

    public void setTamano(int tamano) {
        this.tamano = tamano;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public float getNumPaginas() {
        return numPaginas;
    }

    public void setNumPaginas(float numPaginas) {
        this.numPaginas = numPaginas;
    }
    


    public Micro_procesos getH1() {
        return h1;
    }

    public void setH1(Micro_procesos h1) {
        this.h1 = h1;
    }

    public Micro_procesos getH2() {
        return h2;
    }

    public void setH2(Micro_procesos h2) {
        this.h2 = h2;
    }

    public Micro_procesos getH3() {
        return h3;
    }

    public void setH3(Micro_procesos h3) {
        this.h3 = h3;
    }
    public void AsignarHilos(){
        int valorhilos=getTamano()/3; // tamaño/hilo (3) predefinido
        h2.setId(id);
        h1.setId(id);
        h3.setId(id);
        h2.setNombre(nombre+".2");
        h1.setNombre(nombre+".1");
        h3.setNombre(nombre+".3");
        h2.setTamano(valorhilos);
        h1.setTamano(valorhilos);
        h3.setTamano(valorhilos);
       
    }
    public void asignarrecursos(){
        if(isR1()==true && isRe1()==true){
            h1.setR1(true);
            h1.setRe1(true);
        }
        if(isR2()==true && isRe2()==true){
            h2.setR2(true);
            h2.setRe2(true);
        }
        if(isR3()==true && isRe3()==true){
            h1.setR1(true);
            h1.setRe1(true);
        }
        if(isR4()==true && isRe4()==true){
            h2.setR2(true);
            h2.setRe2(true);
        }
        if(isR5()==true && isRe5()==true){
            h3.setR3(true);
           h3.setRe3(true);
        }
        
    }

    
}
