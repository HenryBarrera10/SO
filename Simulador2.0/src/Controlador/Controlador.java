package Controlador;

import Modelo.*;
import Vista.Ventana;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class Controlador implements ActionListener, Runnable {

    Ventana ventana;//Interfaz
    Proceso proceso;//Proceso
    SimuladorProcesos Sproceso;//ArraysList estados de procesos
    Thread hilo;//Hilo
    int memoria;//Espacio de memoria
    boolean activo;//Verifica inicio de hilo
    boolean r1, r2, r3, r4, r5;//Recursos locales para determiner que recurso esta disponible
    float pag;//numero de paginas
    Memoria objmemoria;//memoria para poner en la tabla
    int hi;//asignar paginas
    boolean pausar;
    boolean reanudar;

    public Controlador() {
        ventana = new Ventana();
        Sproceso = new SimuladorProcesos();
        hilo = new Thread(this);
        memoria = 1000;
        activo = true;
        r1 = true;
        r2 = true;
        r3 = true;
        r4 = true;
        r5 = true;
        pag = 0;
        objmemoria = new Memoria();
        hi = 1;

    }

    /*Lsta de tareas
    1)Memoria Virtual 
    2)pausar y reanudar*/
    public void inicio() {
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
        ventana.getBtnLimpiar().addActionListener(this);
        ventana.getBtnReiniciar().addActionListener(this);
        ventana.getBtnAgregar().addActionListener(this);
        ventana.getBtnDetener().addActionListener(this);
        ventana.getBtnReanudar().addActionListener(this);
        ventana.getBtnReanudar().setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(ventana.getBtnLimpiar())) {
            Limpiar();//limpia campos
        }
        if (e.getSource().equals(ventana.getBtnReiniciar())) {//Reiniciar sin necesidad de apagar el programa
            Sproceso.getNuevo().clear();
            Sproceso.getListo().clear();
            Sproceso.getTerminado().clear();
            Sproceso.getBloqueado().clear();
            Sproceso.getTotal().clear();
            Sproceso.getEjecucion().clear();
            limpiar_memoria();
            Limpiar();
            memoria = 1000;
        }
        if (e.getSource().equals(ventana.getBtnAgregar())) {//Agrega proceso
            proceso = new Proceso(); //nuevo proceso
            if (ventana.getTxtId().getText().equals("") || ventana.getTxttamaño().getText().equals("") || ventana.getTxtnombre().getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Por favor verificar los campos a llenar ");
            } else {
                if (memoria < 0) {
                    inhabilitar();
                } else {
                    proceso.setId(Integer.parseInt(ventana.getTxtId().getText()));//recolectar datos
                    proceso.setTamano(Integer.parseInt(ventana.getTxttamaño().getText()));
                    proceso.setTamaptem(Integer.parseInt(ventana.getTxttamaño().getText()));
                    proceso.setNombre(ventana.getTxtnombre().getText());
                    memoria = memoria - proceso.getTamano(); //resta de memoria 
                    if (ventana.getCheckrecurso1().isSelected()) {//Verifica si fue seleccionado un proceso 
                        proceso.setR1(true); //recursos
                    }
                    if (ventana.getCheckRecurso2().isSelected()) {
                        proceso.setR2(true);
                    }
                    if (ventana.getCheckRecurso3().isSelected()) {
                        proceso.setR3(true);
                    }
                    if (ventana.getCheckRecurso4().isSelected()) {
                        proceso.setR4(true);
                    }
                    if (ventana.getCheckRecurso5().isSelected()) {
                        proceso.setR5(true);
                    }
                    proceso.setNumPaginas(objmemoria.paginas(Integer.parseInt(ventana.getTxttamaño().getText()))); //numero de paginas
                    proceso.setEstado("Nuevo");
                    proceso.AsignarHilos();//Asigna tamaño de hilos de acuerdo al tamaño del proceso.
                    if (proceso.getNumPaginas() <= 3 && proceso.getNumPaginas() > 0) {
                        if (Sproceso.getTotal().size() != 0) {
                            boolean posibilidad = true;
                            for (int k = 0; k < Sproceso.getTotal().size(); k++) {
                                if (proceso.getId() == Sproceso.getTotal().get(k).getId() || ventana.getTxtnombre().getText().equalsIgnoreCase(Sproceso.getTotal().get(k).getNombre())) {
                                    JOptionPane.showMessageDialog(null, "Error el nombre o el id ingresado no se encuentran disponibles", "Error", JOptionPane.ERROR_MESSAGE);
                                    Limpiar();
                                    posibilidad = false;
                                    break;
                                }
                            }
                            if (posibilidad == true) {
                                Sproceso.getNuevo().add(proceso);
                                Sproceso.getTotal().add(proceso);
                                Limpiar();
                                paginacion(proceso);

                            }

                        } else {
                            Sproceso.getNuevo().add(proceso);
                            Sproceso.getTotal().add(proceso);
                            paginacion(proceso);
                            Limpiar();
                        }

                        if (activo == true) {
                            hilo.start();//hilo general
                            activo = false;
                        }
                    } else {
                        inhabilitar();
                    }
                }
            }

        }
        if (e.getSource().equals(ventana.getBtnDetener())) {
            ventana.getBtnReanudar().setEnabled(true);
            pausar();

        }
        if (e.getSource().equals(ventana.getBtnReanudar())) {
            ventana.getBtnReanudar().setEnabled(false);
            reanudar();

        }
    }

    public synchronized void pausar() {
        pausar = true;
    }

    public synchronized void reanudar() {
        pausar = false;
        notify();
    }

    public void paginacion(Proceso p) {
        DefaultTableModel objMemoria = (DefaultTableModel) ventana.getTablaMemoria().getModel();//tabla memoria
        pag = p.getNumPaginas();
        int fila, columnas = 0;

        while (hi <= 3) {//hilo    predefinido
            if (pag == 0) {
                break;
            } else {
                fila = objmemoria.pocisionF(objMemoria.getRowCount());
                columnas = objmemoria.pocisionC(2);
                while (objMemoria.getValueAt(fila, columnas) != null) {
                    fila = objmemoria.pocisionF(objMemoria.getRowCount());
                    columnas = objmemoria.pocisionC(2);
                }
                objMemoria.setValueAt(p.getNombre() + "." + hi, fila, columnas);
                hi++;
                pag--;
            }

        }
        hi = 1;
    }

    public void inhabilitar() {
        ventana.getTxtId().setEnabled(false);
        ventana.getTxtnombre().setEnabled(false);
        ventana.getTxttamaño().setEnabled(false);
        ventana.getCheckrecurso1().setEnabled(false);
        ventana.getCheckRecurso2().setEnabled(false);
        ventana.getCheckRecurso3().setEnabled(false);
        ventana.getCheckRecurso4().setEnabled(false);
        ventana.getCheckRecurso5().setEnabled(false);
        JOptionPane.showMessageDialog(null, "Memoria llena o no hay espacio de paginacion!!, no se aceptan mas procesos");
    }

    public void limpiar_memoria() {
        DefaultTableModel objMemoria = (DefaultTableModel) ventana.getTablaMemoria().getModel();//tabla memoria
        for (int i = 0; i < ventana.getTablaMemoria().getRowCount(); i++) {
            for (int j = 0; j < ventana.getTablaMemoria().getColumnCount(); j++) {
                if (objMemoria.getValueAt(i, j) != null) {
                    objMemoria.setValueAt(null, i, j);
                }
            }
        }
    }

    @Override
    public void run() {
        while (true) {

            TablaG();
            // actializa a la lista total
            tablanuevo();//nuevo de acuerdo a la lista nuevo
            NuevoAListo();//tabla nuevo a listo
            synchronized (this) {
                if (pausar == true) {
                    while (pausar) {
                        try {
                            wait();
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
            tablaListo();
            try {
                hilo.sleep(1000);
            } catch (InterruptedException ex) {
                return;

            }

            Listo_Ejecutado();
            synchronized (this) {
                if (pausar == true) {
                    while (pausar) {
                        try {
                            wait();
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
            TablaEjecutar();
            try {
                hilo.sleep(1000);
            } catch (InterruptedException ex) {
                return;

            }
            Ejecucion();
            synchronized (this) {
                if (pausar == true) {
                    while (pausar) {
                        try {
                            wait();
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
            Bloqueado_Listo();

            synchronized (this) {
                if (pausar == true) {
                    while (pausar) {
                        try {
                            wait();
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }

            try {
                hilo.sleep(1000);
            } catch (InterruptedException ex) {
                return;

            }

        }

    }

    public void Listo_Ejecutado() {

        if (Sproceso.getListo().size() > 0) {
            Sproceso.getListo().get(0).setEstado("Ejecutando");
            Sproceso.getEjecucion().add(Sproceso.getListo().get(0));
            Sproceso.getListo().remove(0);
        }
        try {
            hilo.sleep(1000);
        } catch (InterruptedException ex) {
            return;

        }
        tablaListo();
        TablaG();

    }
   
   
    
public void Ejecucion() {
    if (Sproceso.getEjecucion().size() > 0) {
        if (Verificacion(Sproceso.getEjecucion().get(0))) {
            Sproceso.getEjecucion().get(0).asignarrecursos();
            Sproceso.getEjecucion().get(0).setTamano(Sproceso.getEjecucion().get(0).getTamano() - 3);//cuanto reducir?
            Sproceso.getEjecucion().get(0).getH1().setTamano(Sproceso.getEjecucion().get(0).getH1().getTamano() - 1);
            Sproceso.getEjecucion().get(0).getH2().setTamano(Sproceso.getEjecucion().get(0).getH2().getTamano() - 1);
            Sproceso.getEjecucion().get(0).getH3().setTamano(Sproceso.getEjecucion().get(0).getH3().getTamano() - 1);
            if (Sproceso.getEjecucion().get(0).getTamano() <= 0) { // Cambiado a <= 0
                try {
                    hilo.sleep(1000);
                } catch (InterruptedException ex) {
                    return;
                }
                Proceso proceso = Sproceso.getEjecucion().get(0);
                Sproceso.getEjecucion().remove(proceso); // Se remueve el proceso de la lista de ejecución
                //Liberar(Sproceso.getEjecucion().get(0));
                //proceso.liberarrecursos(); // Se liberan los recursos
                Sproceso.getTerminado().add(proceso); // Se agrega el proceso a la lista de terminados
                //Ejecutado_Terminado();
                TablaTerminado();
                TablaG();
            } else {
                try {
                    hilo.sleep(1000);
                } catch (InterruptedException ex) {
                    return;

                }
                Liberar(Sproceso.getEjecucion().get(0));
                Ejecutado_Listo();
                tablaListo();
                TablaG();
            }
        } else {
            try {
                hilo.sleep(1000);
            } catch (InterruptedException ex) {
                return;
            }
            Ejecucion_Bloqueado();
            TablaG();
        }
    }
}

 
    public void Ejecutado_Listo() {
        if (Sproceso.getEjecucion().size() > 0) {
            Sproceso.getEjecucion().get(0).setEstado("Listo");
            Sproceso.getListo().add(Sproceso.getEjecucion().get(0));
            Sproceso.getEjecucion().remove(0);
        }
        try {
            hilo.sleep(1000);
        } catch (InterruptedException ex) {
            return;

        }
        TablaEjecutar();

    }

    public void Ejecucion_Bloqueado() {
        if (Sproceso.getEjecucion().size() > 0) {
            Sproceso.getEjecucion().get(0).setEstado("Bloqueado");
            Sproceso.getBloqueado().add(Sproceso.getEjecucion().get(0));
            Sproceso.getEjecucion().remove(0);
        }

    }

    public void TablaBloqueado() {

        Object[][] data = new Object[0][0];
        DefaultTableModel plantill = (DefaultTableModel) ventana.getTablaBloqueado().getModel();
        String[] titulos = {"ID", "Tamaño"};
        plantill.setDataVector(data, titulos);
        if (Sproceso.getBloqueado().size() > 0) {
            for (int i = 0; i < Sproceso.getBloqueado().size(); i++) {
                plantill.addRow(new Object[]{Sproceso.getBloqueado().get(i).getId(), Sproceso.getBloqueado().get(i).getTamano()});
            }
        }

    }

    public void Liberar(Proceso p) {
        double ram;
        if (p.isRe1() == true) {
            ram = (Math.random() * 1);
            if (Math.round(ram) == 1) {
                //no se libera
                p.setRe1(false);
                r1 = true;
                p.setR1(false);

            }
        }
        if (p.isRe2() == true) {
            ram = (Math.random() * 1);
            if (Math.round(ram) == 1) {
                //no se libera
                p.setRe2(false);
                r2 = true;
                p.setR2(false);

            }
        }
        if (p.isRe3() == true) {
            ram = (Math.random() * 1);
            if (Math.round(ram) == 1) {
                //no se libera
                p.setRe3(false);
                r3 = true;
                p.setR3(false);

            }
        }
        if (p.isRe4() == true) {
            ram = (Math.random() * 1);
            if (Math.round(ram) == 1) {
                //no se libera
                p.setRe4(false);
                r4 = true;
                p.setR4(false);

            }
        }
        if (p.isRe5() == true) {
            ram = (Math.random() * 1);
            if (Math.round(ram) == 1) {
                //no se libera
                p.setRe5(false);
                r5 = true;
                p.setR5(false);

            }
        }
    }

    public void Bloqueado_Listo() {
        if (Sproceso.getBloqueado().size() > 0) {
            for (int i = 0; i < Sproceso.getBloqueado().size(); i++) {
                if (Verificacion(Sproceso.getBloqueado().get(i))) {
                    Sproceso.getBloqueado().get(i).setEstado("Bloqueado Listo");
                    Sproceso.getListo().add(Sproceso.getBloqueado().get(i));
                    Sproceso.getBloqueado().remove(i);
                }
            }
            try {
                hilo.sleep(1000);
            } catch (InterruptedException ex) {
                return;

            }
            tablaListo();
            TablaBloqueado();

        } else {
            return;
        }

    }

    public boolean Verificacion(Proceso p) {
        boolean r = false;
        if (p.isR1() == false && p.isR2() == false && p.isR3() == false && p.isR4() == false && p.isR5() == false) {
            //En caso de que algun proceso no tenga recurso
            //r = true;
            return true;
        }
        if (p.isR1() == true) {
            if (r1 == true && p.isRe1() == false) {
                r1 = false;//lo ocupa
                p.setRe1(true);
                return true;
            } else {
                if (p.isRe1() == true) {
                    return true;
                } else {
                    return false;
                }

            }

        }
        if (p.isR2() == true) {
            if (r2 == true && p.isRe2() == false) {
                r2 = false;//lo ocupa
                p.setRe2(true);
                return true;
            } else {
                if (p.isRe2() == true) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        if (p.isR3() == true) {
            if (r3 == true && p.isRe3() == false) {
                r3 = false;//lo ocupa
                p.setRe3(true);
                return true;
            } else {
                if (p.isRe3() == true) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        if (p.isR4() == true) {
            if (r4 == true && p.isRe4() == false) {
                r4 = false;//lo ocupa
                p.setRe4(true);
                return true;
            } else {
                if (p.isRe4() == true) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        if (p.isR5() == true) {
            if (r5 == true && p.isRe5() == false) {
                r5 = false;//lo ocupa
                p.setRe5(true);
                return true;
            } else {
                if (p.isRe5() == true) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;

    }

    public void TablaEjecutar() {
        //micro procesadores
        Object[][] data = new Object[0][0];
        DefaultTableModel plantill = (DefaultTableModel) ventana.getTablaEjecucion().getModel();
        String[] titulos = {"ID", "Nombre", "Tamaño"};
        plantill.setDataVector(data, titulos);

        if (Sproceso.getEjecucion().size() > 0) {
            plantill.addRow(new Object[]{Sproceso.getEjecucion().get(0).getH1().getId(), Sproceso.getEjecucion().get(0).getH1().getNombre(), Sproceso.getEjecucion().get(0).getH1().getTamano()});

        }

        Object[][] data1 = new Object[0][0];
        DefaultTableModel plantill1 = (DefaultTableModel) ventana.getTablamicroejecucion1().getModel();
        plantill1.setDataVector(data1, titulos);

        if (Sproceso.getEjecucion().size() > 0) {
            plantill1.addRow(new Object[]{Sproceso.getEjecucion().get(0).getH2().getId(), Sproceso.getEjecucion().get(0).getH2().getNombre(), Sproceso.getEjecucion().get(0).getH2().getTamano()});

        }
        Object[][] data2 = new Object[0][0];
        DefaultTableModel plantill2 = (DefaultTableModel) ventana.getTablamicroejecucion2().getModel();
        plantill2.setDataVector(data2, titulos);

        if (Sproceso.getEjecucion().size() > 0) {
            plantill2.addRow(new Object[]{Sproceso.getEjecucion().get(0).getH3().getId(), Sproceso.getEjecucion().get(0).getH3().getNombre(), Sproceso.getEjecucion().get(0).getH3().getTamano()});

        }
    }

    public void Ejecutado_Terminado() {
        Sproceso.getEjecucion().get(0).setEstado("Terminado");
        Sproceso.getTerminado().add(Sproceso.getEjecucion().get(0));
        Sproceso.getEjecucion().remove(0);
        r1 = true;
        r2 = true;
        r3 = true;
        r4 = true;
        r5 = true;
        memoria = memoria + Sproceso.getEjecucion().get(0).getTamaptem();  

    }

    public void TablaTerminado() {
        Object[][] data = new Object[0][0];
        DefaultTableModel plantill = (DefaultTableModel) ventana.getTablaTeriminado().getModel();
        String[] titulos = {"ID", "Tamaño"};
        plantill.setDataVector(data, titulos);
        for (int i = 0; i < Sproceso.getTerminado().size(); i++) {
            plantill.addRow(new Object[]{Sproceso.getTerminado().get(i).getId(), Sproceso.getTerminado().get(i).getTamano()});
        }
    }

    public void NuevoAListo() {
        for (int i = 0; i < Sproceso.getNuevo().size(); i++) {
            Sproceso.getNuevo().get(i).setEstado("Listo");
            Sproceso.getListo().add(Sproceso.getNuevo().get(i));
            Sproceso.getNuevo().remove(Sproceso.getNuevo().get(i));

        }
        try {
            hilo.sleep(1000);
        } catch (InterruptedException ex) {
            return;

        }
        tablanuevo();
        TablaG();

    }

    public void tablaListo() {

        Object[][] data = new Object[0][0];
        DefaultTableModel plantill = (DefaultTableModel) ventana.getTablaListo().getModel();
        String[] titulos = {"ID", "Tamaño"};
        plantill.setDataVector(data, titulos);
        for (int i = 0; i < Sproceso.getListo().size(); i++) {
            plantill.addRow(new Object[]{Sproceso.getListo().get(i).getId(), Sproceso.getListo().get(i).getTamano()});
        }
    }

    public void tablanuevo() {
        Object[][] data = new Object[0][0];
        DefaultTableModel plantill = (DefaultTableModel) ventana.getTablaNuevo().getModel();
        String[] titulos = {"ID", "Tamaño"};
        plantill.setDataVector(data, titulos);
        if (Sproceso.getNuevo().size() > 0) {
            for (int i = 0; i < Sproceso.getNuevo().size(); i++) {
                plantill.addRow(new Object[]{Sproceso.getNuevo().get(i).getId(), Sproceso.getNuevo().get(i).getTamano()});
            }
        }
    }

    public void TablaG() {
        Object[][] data = new Object[0][0];
        DefaultTableModel plantill = (DefaultTableModel) ventana.getTabla_estadistica_todo().getModel();
        String[] titulos = {"ID", "NOMBRE", "TAMAÑO", "RECURSOS", "ESTADO"};
        plantill.setDataVector(data, titulos);
        if (Sproceso.getTotal().size() > 0) {
            for (int i = 0; i < Sproceso.getTotal().size(); i++) {
                String temp = "";
                if (Sproceso.getTotal().get(i).isR1() == true) {
                    temp += "R1,";
                }
                if (Sproceso.getTotal().get(i).isR2() == true) {
                    temp += "R2,";
                }
                if (Sproceso.getTotal().get(i).isR3() == true) {
                    temp += "R3,";
                }
                if (Sproceso.getTotal().get(i).isR4() == true) {
                    temp += "R4,";
                }
                if (Sproceso.getTotal().get(i).isR5() == true) {
                    temp += "R5,";
                }
                plantill.addRow(new Object[]{Sproceso.getTotal().get(i).getId(), Sproceso.getTotal().get(i).getNombre(), Sproceso.getTotal().get(i).getTamano(), temp, Sproceso.getTotal().get(i).getEstado()});

            }
        }

    }

    public void Limpiar() {
        ventana.getTxtId().setText("");
        ventana.getTxtnombre().setText("");
        ventana.getTxttamaño().setText("");
        ventana.getCheckrecurso1().setSelected(false);
        ventana.getCheckRecurso2().setSelected(false);
        ventana.getCheckRecurso3().setSelected(false);
        ventana.getCheckRecurso4().setSelected(false);
        ventana.getCheckRecurso5().setSelected(false);
    }

}
