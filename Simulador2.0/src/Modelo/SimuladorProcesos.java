
package Modelo;

import java.util.ArrayList;


public class SimuladorProcesos {
    public ArrayList <Proceso> nuevo = new ArrayList();
    public ArrayList <Proceso> listo = new ArrayList();
    public ArrayList <Proceso> ejecucion = new ArrayList();
    public ArrayList <Proceso> bloqueado = new ArrayList();
    public ArrayList <Proceso> terminado = new ArrayList();
    public ArrayList <Proceso> Total = new ArrayList();
    

    public SimuladorProcesos() {
        
    }

    public ArrayList<Proceso> getNuevo() {
        return nuevo;
    }

    public void setNuevo(ArrayList<Proceso> nuevo) {
        this.nuevo = nuevo;
    }

    public ArrayList<Proceso> getListo() {
        return listo;
    }

    public void setListo(ArrayList<Proceso> listo) {
        this.listo = listo;
    }

    public ArrayList<Proceso> getEjecucion() {
        return ejecucion;
    }

    public void setEjecucion(ArrayList<Proceso> ejecucion) {
        this.ejecucion = ejecucion;
    }

    public ArrayList<Proceso> getBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(ArrayList<Proceso> bloqueado) {
        this.bloqueado = bloqueado;
    }

    public ArrayList<Proceso> getTerminado() {
        return terminado;
    }

    public void setTerminado(ArrayList<Proceso> terminado) {
        this.terminado = terminado;
    }

    public ArrayList<Proceso> getTotal() {
        return Total;
    }

    public void setTotal(ArrayList<Proceso> Total) {
        this.Total = Total;
    }
    
    
    
    
}
