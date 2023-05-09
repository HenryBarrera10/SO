package Modelo;

public class Micro_procesos {
    private int id,tamano;
    private String nombre;
    private boolean r1,r2,r3,r4,r5;
    private boolean re1,re2,re3,re4,re5;

    public Micro_procesos() {
        this.id = 0;
        this.tamano = 0;
        this.nombre = "";
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
    }
    

    public Micro_procesos(int id, int tamano, String nombre, boolean r1, boolean r2, boolean r3, boolean r4, boolean r5, boolean re1, boolean re2, boolean re3, boolean re4, boolean re5) {
        this.id = id;
        this.tamano = tamano;
        this.nombre = nombre;
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
    
}
