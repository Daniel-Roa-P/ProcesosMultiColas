
package procesosmulticolas;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class ProcesosMulticolas extends JFrame implements Runnable ,ActionListener{

    JScrollPane scrollPane = new JScrollPane();
    JScrollPane scrollPane1 = new JScrollPane();
    
    JScrollPane scrollPane2 = new JScrollPane();
    JScrollPane scrollPane3 = new JScrollPane();
    
    JScrollPane scrollPane4 = new JScrollPane();
    JScrollPane scrollPane5 = new JScrollPane();
    
    JScrollPane scrollPane6 = new JScrollPane();
    JScrollPane scrollPane7 = new JScrollPane();
    
    JScrollPane scrollPane8 = new JScrollPane();
    JScrollPane scrollPane9 = new JScrollPane();
    
    JComboBox prioridad = new JComboBox();
    
    JLabel semaforo = new JLabel();
    
    JLabel label = new JLabel("Nombre del proceso: ");
    JLabel label1 = new JLabel("Nombre del proceso: ");
    JLabel label2 = new JLabel("Nombre del proceso: ");
    JLabel label3 = new JLabel("Proceso en ejecucion: Ninguno");
    JLabel label4 = new JLabel("Tiempo: ");
    JLabel label5 = new JLabel("Tabla de procesos (Round Robin - Limite de ejecucion: 5):");
    JLabel label6 = new JLabel("Diagrama de Gant:");
    JLabel label7 = new JLabel("Tabla de Bloqueados:");
    JLabel label8 = new JLabel("Rafaga restante del proceso: 0");
    JLabel label9 = new JLabel("Tabla de procesos (Rafaga más corta):");
    JLabel label10 = new JLabel("Tabla de procesos (Prioridades):");
    JLabel label11 = new JLabel("Prioridad:");
    
    JButton botonIngresarRoundRobin = new JButton("Ingresar proceso (Round Robin)");
    JButton botonIngresarCorta = new JButton("Ingresar proceso (Rafaga corta)");
    JButton botonIngresarPrioridad = new JButton("Ingresar proceso (Prioridad)");
    JButton botonAleatorioRoundRobin = new JButton("Ingresar aleatorios (3)");
    JButton botonAleatorioCorta = new JButton("Ingresar aleatorios (3)");
    JButton botonAleatorioPrioridad = new JButton("Ingresar aleatorios (3)");
    JButton botonIniciar = new JButton("Iniciar ejecucion - tiempo de envejecimiento: 3 segundos");
    JButton botonBloquear = new JButton("Bloquear proceso actual");
    
    JTextField tfNombre = new JTextField("A1");
    JTextField tfNombre2 = new JTextField("B1");
    JTextField tfNombre3 = new JTextField("C1");
    
    JTextField[][] tabla = new JTextField[100][7];
    JTextField[][] tabla2 = new JTextField[100][2];
    JTextField[][] tabla3 = new JTextField[100][3];
    JTextField[][] tablaBloqueados = new JTextField[100][3];
    JLabel[][] diagrama = new JLabel[50][200];  
    
    ListaCircular colaRoundRobin = new ListaCircular();
    ListaCircular colaCorta = new ListaCircular();
    ListaCircular colaPrioridad= new ListaCircular();
    
    Nodo nodoEjecutado;
    
    int filasRoundRobin = 0, filasCorta = 0, filasPrioridad = 0, rafagaTemporal;
    int tiempoGlobal = 0;
    int coorX = 0;
    
    Thread procesos;
    
    public static void main(String[] args) {

        ProcesosMulticolas pm = new ProcesosMulticolas(); 
        pm.setBounds(0, 0, 1320, 730);
        pm.setTitle("Procesos con multiples colas");
        pm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pm.setVisible(true);
        
    }

    ProcesosMulticolas(){
        
        Container c = getContentPane();
        c.setLayout(null);
        this.getContentPane().setBackground(Color.GRAY);
        
        c.add(label);
        c.add(label1);
        c.add(label2);
        c.add(label3);
        c.add(label4);
        c.add(label5);
        c.add(label6);
        c.add(label7);
        c.add(label8);
        c.add(label9);
        c.add(label10);
        c.add(label11);
        c.add(semaforo);
        
        c.add(scrollPane1);
        c.add(scrollPane3);
        c.add(scrollPane5);
        c.add(scrollPane7);
        c.add(scrollPane9);
        
        c.add(botonIngresarRoundRobin);
        c.add(botonAleatorioRoundRobin);
        c.add(botonIngresarCorta);
        c.add(botonIniciar);
        c.add(botonBloquear);
        c.add(botonAleatorioCorta);
        c.add(botonIngresarPrioridad);
        c.add(botonAleatorioPrioridad);
        
        c.add(tfNombre);
        c.add(tfNombre2);
        c.add(tfNombre3);
        
        c.add(prioridad);
        
        prioridad.addItem("1");
        prioridad.addItem("2");
        prioridad.addItem("3");
        prioridad.addItem("4");
        prioridad.addItem("5");
        
        label.setBounds(720, 560, 300, 20);
        label1.setBounds(970, 560, 300, 20);
        label2.setBounds(250, 560, 300, 20);
        label3.setBounds(970, 285, 300, 20);
        label4.setBounds(970, 270, 300, 20);
        label5.setBounds(50, 330, 500, 20);
        label6.setBounds(50, 0, 300, 20);
        label7.setBounds(970, 0, 300, 20);
        label8.setBounds(970, 300, 300, 20);
        label9.setBounds(670, 330, 300, 20);
        label10.setBounds(970, 330, 300, 20);
        label11.setBounds(1150, 560, 200, 20);
        
        scrollPane.setBounds(50, 350, 2500, 2500);
        scrollPane.setPreferredSize(new Dimension(2500, 2500));  
        scrollPane.setBackground(Color.WHITE);
        
        scrollPane1.setBounds(50, 350, 600, 200);
        scrollPane1.setPreferredSize(new Dimension(1150, 400)); 
        scrollPane1.setBackground(Color.WHITE);
        
        scrollPane2.setBounds(50, 20, 2500, 2500);
        scrollPane2.setPreferredSize(new Dimension(2500, 2500));  
        scrollPane2.setBackground(Color.WHITE);
        
        scrollPane3.setBounds(50, 20, 600, 300);
        scrollPane3.setPreferredSize(new Dimension(1150, 400)); 
        scrollPane3.setBackground(Color.WHITE);
        
        scrollPane4.setBounds(970, 20, 280, 1000);
        scrollPane4.setPreferredSize(new Dimension(500, 1000));  
        scrollPane4.setBackground(Color.WHITE);
        
        scrollPane5.setBounds(970, 20, 280, 250);
        scrollPane5.setPreferredSize(new Dimension(350, 350)); 
        scrollPane5.setBackground(Color.WHITE);
        
        scrollPane6.setBounds(670, 350, 2500, 2500);
        scrollPane6.setPreferredSize(new Dimension(300, 2500));  
        scrollPane6.setBackground(Color.WHITE);
        
        scrollPane7.setBounds(670, 350, 280, 200);
        scrollPane7.setPreferredSize(new Dimension(280, 200)); 
        scrollPane7.setBackground(Color.WHITE);
        
        scrollPane8.setBounds(970, 350, 280, 1000);
        scrollPane8.setPreferredSize(new Dimension(300, 2500));  
        scrollPane8.setBackground(Color.WHITE);
        
        scrollPane9.setBounds(970, 350, 280, 200);
        scrollPane9.setPreferredSize(new Dimension(280, 200)); 
        scrollPane9.setBackground(Color.WHITE);
        
        tfNombre.setBounds(380, 560, 70, 20);
        tfNombre2.setBounds(850, 560, 50, 20);
        tfNombre3.setBounds(1090, 560, 40, 20);
        
        prioridad.setBounds(1210, 560, 40, 20);
        
        botonIngresarRoundRobin.addActionListener(this);
        botonIngresarRoundRobin.setBounds(50, 585, 600, 20);
        botonIngresarRoundRobin.setBackground(Color.CYAN);
        
        botonAleatorioRoundRobin.addActionListener(this);
        botonAleatorioRoundRobin.setBounds(50, 610, 600, 20);
        botonAleatorioRoundRobin.setBackground(Color.YELLOW);
        
        botonIngresarCorta.addActionListener(this);
        botonIngresarCorta.setBounds(670, 585, 280, 20);
        botonIngresarCorta.setBackground(Color.CYAN);
        
        botonAleatorioCorta.addActionListener(this);
        botonAleatorioCorta.setBounds(670, 610, 280, 20);
        botonAleatorioCorta.setBackground(Color.YELLOW);
        
        botonIngresarPrioridad.addActionListener(this);
        botonIngresarPrioridad.setBounds(970, 585, 280, 20);
        botonIngresarPrioridad.setBackground(Color.CYAN);
        
        botonAleatorioPrioridad.addActionListener(this);
        botonAleatorioPrioridad.setBounds(970, 610, 280, 20);
        botonAleatorioPrioridad.setBackground(Color.YELLOW);
        
        botonIniciar.addActionListener(this);
        botonIniciar.setBounds(50, 640, 600, 40);
        botonIniciar.setBackground(Color.GREEN);
        
        botonBloquear.addActionListener(this);
        botonBloquear.setBounds(670, 640, 580, 40);
        botonBloquear.setBackground(Color.RED);
        
        dibujarSemaforo("Verde.jpg");
        
    }
    
    public void dibujarSemaforo(String color){
        
        JLabel img = new JLabel();
        
        ImageIcon imgIcon = new ImageIcon(getClass().getResource(color));

        Image imgEscalada = imgIcon.getImage().getScaledInstance(280, 300, Image.SCALE_SMOOTH);
        Icon iconoEscalado = new ImageIcon(imgEscalada);
        semaforo.setBounds(670 , 20, 280, 300);
        semaforo.setIcon(iconoEscalado);
     
    }
    
    public void dibujarTablaRoundRobin(String nombre, int rafaga, int tiempo){
        
        scrollPane.removeAll();
        
        JLabel texto1 = new JLabel("Proceso");
        JLabel texto2 = new JLabel("T. llegada");
        JLabel texto3 = new JLabel("Rafaga");
        JLabel texto4 = new JLabel("T. comienzo");
        JLabel texto5 = new JLabel("T. final");
        JLabel texto6 = new JLabel("T. retorno");
        JLabel texto7 = new JLabel("T. espera");
        
        texto1.setBounds(20, 20, 150, 20);
        texto2.setBounds(100, 20, 150, 20);
        texto3.setBounds(180, 20, 150, 20);
        texto4.setBounds(260, 20, 150, 20);
        texto5.setBounds(340, 20, 150, 20);
        texto6.setBounds(420, 20, 150, 20);
        texto7.setBounds(500, 20, 150, 20);
        
        scrollPane.add(texto1);
        scrollPane.add(texto2);
        scrollPane.add(texto3);
        scrollPane.add(texto4);
        scrollPane.add(texto5);
        scrollPane.add(texto6);
        scrollPane.add(texto7);
        
        for(int i = 0; i<filasRoundRobin; i++){
            
            for(int j = 0; j<7; j++){
            
                if(tabla[i][j] != null){
                    
                    scrollPane.add(tabla[i][j]);
                    
                } else {
                
                    tabla[i][j] = new JTextField();
                    tabla[i][j].setBounds(20 + (j*80), 40 + (i*25), 70, 20);
                    
                    scrollPane.add(tabla[i][j]);
                    
                }

            }
        
        }
        
        tabla[filasRoundRobin-1][0].setText(nombre);
        tabla[filasRoundRobin-1][1].setText(Integer.toString(tiempo));
        tabla[filasRoundRobin-1][2].setText(Integer.toString(rafaga));

        scrollPane.repaint();
        scrollPane1.setViewportView(scrollPane);
        
    }
    
    public void dibujarTablaCorta(ListaCircular cola, JScrollPane scrollPar, JScrollPane scrollImpar){
        
        scrollPar.removeAll();
        
        JLabel texto1 = new JLabel("Proceso");
        JLabel texto2 = new JLabel("Rafaga");
        
        texto1.setBounds(20, 20, 150, 20);
        texto2.setBounds(100, 20, 150, 20);
        
        scrollPar.add(texto1);
        scrollPar.add(texto2);
        
        if(cola.getCabeza() != null){
        
        Nodo temp = cola.getCabeza().getSiguiente();
        
            for(int i = 0; i<cola.getTamaño(); i++){

                for(int j = 0; j<2 ; j++){

                        tabla2[i][j] = new JTextField("");
                        tabla2[i][j].setBounds(20 + (j*80), 40 + (i*25), 70, 20);

                        scrollPar.add(tabla2[i][j]);

                }

                tabla2[i][0].setText(temp.getLlave());
                tabla2[i][1].setText(Integer.toString(temp.getRafaga()));
                
                temp = temp.getSiguiente();

            }
        
        }
        
        scrollPar.repaint();
        scrollImpar.setViewportView(scrollPar);
        
    }
    
    public void dibujarTablaPrioridad(ListaCircular cola, JScrollPane scrollPar, JScrollPane scrollImpar){
        
        scrollPar.removeAll();
        
        JLabel texto1 = new JLabel("Proceso");
        JLabel texto2 = new JLabel("Rafaga");
        JLabel texto3 = new JLabel("Prioridad");
        
        texto1.setBounds(20, 20, 150, 20);
        texto2.setBounds(100, 20, 150, 20);
        texto3.setBounds(180, 20, 150, 20);
        
        scrollPar.add(texto1);
        scrollPar.add(texto2);
        scrollPar.add(texto3);
        
        if(cola.getCabeza() != null){
        
        Nodo temp = cola.getCabeza().getSiguiente();
        
            for(int i = 0; i<cola.getTamaño(); i++){

                for(int j = 0; j<3 ; j++){

                        tabla3[i][j] = new JTextField("");
                        tabla3[i][j].setBounds(20 + (j*80), 40 + (i*25), 70, 20);

                        scrollPar.add(tabla3[i][j]);

                }

                tabla3[i][0].setText(temp.getLlave());
                tabla3[i][1].setText(Integer.toString(temp.getRafaga()));
                tabla3[i][2].setText(Integer.toString(temp.getPrioridad()));
                
                temp = temp.getSiguiente();

            }
        
        }
        
        scrollPar.repaint();
        scrollImpar.setViewportView(scrollPar);
        
    }
    
    public void llenarBloqueados( ListaCircular cola, JScrollPane scrollPar, JScrollPane scrollImpar){
        
        scrollPane4.removeAll();
        
        JLabel texto1 = new JLabel("Proceso");
        JLabel texto2 = new JLabel("T. llegada");
        JLabel texto3 = new JLabel("Rafaga");
        
        texto1.setBounds(20, 20, 150, 20);
        texto2.setBounds(100, 20, 150, 20);
        texto3.setBounds(180, 20, 150, 20);
        
        scrollPane4.add(texto1);
        scrollPane4.add(texto2);
        scrollPane4.add(texto3);
        
        if(cola.getCabeza() != null){
        
        Nodo temp = cola.getCabeza().getSiguiente();
        
            for(int i = 0; i<cola.getTamaño()-1; i++){

                for(int j = 0; j<3 ; j++){

                        tablaBloqueados[i][j] = new JTextField("");
                        tablaBloqueados[i][j].setBounds(20 + (j*80), 40 + (i*25), 70, 20);

                        scrollPar.add(tablaBloqueados[i][j]);

                }

                tablaBloqueados[i][0].setText(temp.getLlave());
                tablaBloqueados[i][1].setText(tablaBloqueados[i][1].getText() + "," + Integer.toString(temp.getLlegada()));
                tablaBloqueados[i][2].setText(Integer.toString(temp.getRafaga()));
                
                temp = temp.getSiguiente();

            }
        
        }
        
        scrollPar.repaint();
        scrollImpar.setViewportView(scrollPar);
        
    }
    
    public void llenarRestante(){
        
        tabla[nodoEjecutado.getIndice()-1][3].setText(tabla[nodoEjecutado.getIndice()-1][3].getText() + "," + Integer.toString(nodoEjecutado.getComienzo()));
        tabla[nodoEjecutado.getIndice()-1][4].setText(tabla[nodoEjecutado.getIndice()-1][4].getText() + "," +Integer.toString(nodoEjecutado.getFinalizacion()));
        tabla[nodoEjecutado.getIndice()-1][5].setText(Integer.toString(nodoEjecutado.getFinalizacion() - nodoEjecutado.getLlegada()));
        
        String [] comienzos = tabla[nodoEjecutado.getIndice()-1][3].getText().split(","); 
        String [] finales = tabla[nodoEjecutado.getIndice()-1][4].getText().split(","); 
        finales[0] = "0";
        String cadena = "";
        
        for(int i = 1; i<comienzos.length; i++){
            
            cadena = cadena + (Integer.parseInt(comienzos[i]) - Integer.parseInt(finales[i-1])) + ",";
            
        }
          
        tabla[nodoEjecutado.getIndice()-1][6].setText(cadena);
        
        
    }
    
    public void dibujarDiagrama(String nombre, int coorX, int coorY){
        
        scrollPane2.removeAll();
        
        for(int i = 0; i<200; i++){
            
            diagrama[0][i] = new JLabel(Integer.toString(i));
            diagrama[0][i].setBounds(40 + (i*20), 20, 20, 20);

            scrollPane2.add(diagrama[0][i]);
            
        }
        
        diagrama[nodoEjecutado.getIndice()][0] = new JLabel("  " + nombre);
        diagrama[nodoEjecutado.getIndice()][0].setBounds(0, 20 + (nodoEjecutado.getIndice()*20), 50, 20);
        
        scrollPane2.add(diagrama[nodoEjecutado.getIndice()][0]);
        
        JLabel img = new JLabel();
        
        ImageIcon imgIcon = new ImageIcon(getClass().getResource("barra.png"));

        Image imgEscalada = imgIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        Icon iconoEscalado = new ImageIcon(imgEscalada);
        
        for(int i = 1; i < filasRoundRobin + 1; i++){
            
            for(int j = 0; j < coorX+1; j++){
                
                if(diagrama[i][j] != null){
                
                    scrollPane2.add(diagrama[i][j]);
                    
                }
                
            }
            
        }
        
        diagrama[nodoEjecutado.getIndice()][coorX+1] = new JLabel();
        diagrama[nodoEjecutado.getIndice()][coorX+1].setBounds(40 + (coorX*20), 20 + (nodoEjecutado.getIndice()*20), 20, 20);
        diagrama[nodoEjecutado.getIndice()][coorX+1].setIcon(iconoEscalado);
        
        scrollPane2.add(diagrama[nodoEjecutado.getIndice()][coorX+1]);
        
        scrollPane2.repaint();
        scrollPane3.setViewportView(scrollPane2);
            
    }
    
    public void ingresar(ListaCircular cola, String nombre, int prioridad , int rafaga, int tiempo, int filas){
        
        cola.insertar(nombre, prioridad, rafaga, tiempo, filas);
        
    }
    
    public void ordenarRafagas(){
        
        int movimientos = 0;
        int contador = 0;
        
        Nodo temp = colaCorta.getCabeza().getSiguiente();
        
        int menorRaf = colaCorta.getCabeza().getRafaga();
        
        while(!(temp.equals(colaCorta.getCabeza()))){
    
            contador++;
            
            if(temp.getRafaga() < menorRaf){
            
                menorRaf = temp.getRafaga();
                movimientos = contador;
                
            }
            
            temp = temp.getSiguiente();
            
        }
        
        for(int i = 0; i < movimientos; i++){
            
            colaCorta.intercambiar(colaCorta.getCabeza());
            
        }
        
    }
    
    public int calcularRafaga(){
        
        return 1 + ((int) (Math.random()*12));
        
    }
    
    public void cortaLlenaRound(){
                
        if( (tiempoGlobal%3 == 0) ){

            for(int i = 0; i<3; i++){

                if(colaCorta.getCabeza() != null){

                    Nodo temp = colaCorta.getCabeza();
                    filasRoundRobin++;
                    ingresar(colaRoundRobin, temp.getLlave(), 0 , temp.getRafaga(), tiempoGlobal, filasRoundRobin);
                    dibujarTablaRoundRobin(temp.getLlave(), temp.getRafaga(), tiempoGlobal);

                    colaCorta.eliminar(colaCorta.getCabeza());

                }

            }

        }
        
        dibujarTablaCorta(colaCorta, scrollPane6, scrollPane7);
        
    }
    
    public void prioLlenaCorta(){
                
        
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
    
        if(e.getSource() == botonIngresarRoundRobin){
            
            filasRoundRobin++;
            
            String nombre = tfNombre.getText();
            rafagaTemporal = calcularRafaga();
            
            ingresar(colaRoundRobin, nombre, 0,rafagaTemporal, tiempoGlobal, filasRoundRobin);
            dibujarTablaRoundRobin(nombre, rafagaTemporal, tiempoGlobal);
            
            tfNombre.setText("A" + (filasRoundRobin + 1));
            
        } else if(e.getSource() == botonIngresarCorta){
        
            filasCorta++;
            
            String nombre = tfNombre2.getText();
            rafagaTemporal = calcularRafaga();
            
            ingresar(colaCorta, nombre, 0, rafagaTemporal, tiempoGlobal, filasCorta);
            dibujarTablaCorta(colaCorta, scrollPane6, scrollPane7);
            
            tfNombre2.setText("B" + (filasCorta + 1));
            
            ordenarRafagas();
            
        } else if(e.getSource() == botonIngresarPrioridad){
        
            filasPrioridad++;
            
            String nombre = tfNombre3.getText();
            String p = String.valueOf(prioridad.getSelectedItem());
            int prio = Integer.parseInt(p);
            rafagaTemporal = calcularRafaga();
            
            ingresar(colaPrioridad, nombre, prio, rafagaTemporal, tiempoGlobal, filasPrioridad);
            dibujarTablaPrioridad(colaPrioridad, scrollPane8, scrollPane9);
            
            tfNombre3.setText("C" + (filasPrioridad + 1));
            
        } else if(e.getSource() == botonAleatorioRoundRobin){
        
            for(int i = 0; i<3; i++){
                
                filasRoundRobin++;
            
                String nombre = tfNombre.getText();
                rafagaTemporal = calcularRafaga();

                ingresar(colaRoundRobin, nombre, 0, rafagaTemporal, tiempoGlobal, filasRoundRobin);
                dibujarTablaRoundRobin(nombre, rafagaTemporal, tiempoGlobal);

                tfNombre.setText("A" + (filasRoundRobin + 1));
            
            }
            
        } else if(e.getSource() == botonAleatorioCorta){
        
            for(int i = 0; i<3; i++){
                
                filasCorta++;
            
                String nombre = tfNombre2.getText();
                rafagaTemporal = calcularRafaga();

                ingresar(colaCorta, nombre, 0, rafagaTemporal, tiempoGlobal, filasCorta);
                
                dibujarTablaCorta(colaCorta, scrollPane6, scrollPane7);

                tfNombre2.setText("B" + (filasCorta + 1));
            
            }
            
            ordenarRafagas();
            
        } else if(e.getSource() == botonAleatorioPrioridad){
        
            for(int i = 0; i<3; i++){
                
                filasPrioridad++;
            
                String nombre = tfNombre3.getText();
                String p = String.valueOf(prioridad.getSelectedItem());
                int prio = (int) (Math.random() * 5) + 1;
                rafagaTemporal = calcularRafaga();

                ingresar(colaPrioridad, nombre, prio, rafagaTemporal, tiempoGlobal, filasPrioridad);
                dibujarTablaPrioridad(colaPrioridad, scrollPane8, scrollPane9);

                tfNombre3.setText("C" + (filasPrioridad + 1));
            
            }
            
        } else if(e.getSource() == botonIniciar){
        
            procesos = new Thread( this );
            procesos.start();  
            
        } else if(e.getSource() == botonBloquear){
        
            if(nodoEjecutado.getRafaga() != 0){
            
                filasRoundRobin++;
                ingresar(colaRoundRobin, nodoEjecutado.getLlave() + "*", 0,nodoEjecutado.getRafaga(), tiempoGlobal, filasRoundRobin);
                dibujarTablaRoundRobin(nodoEjecutado.getLlave() + "*", nodoEjecutado.getRafaga(), tiempoGlobal);
                nodoEjecutado.setFinalizacion(tiempoGlobal);
                llenarRestante();
                colaRoundRobin.eliminar(colaRoundRobin.getCabeza());
                llenarBloqueados(colaRoundRobin, scrollPane4, scrollPane5);
                nodoEjecutado = colaRoundRobin.getCabeza();
                nodoEjecutado.setComienzo(tiempoGlobal);

            }
        }
        
    }
    
    @Override
    public void run() {
    
            try{

            while(colaRoundRobin.getTamaño() != 0){
                
                dibujarSemaforo("Rojo.jpg");
                
                nodoEjecutado = colaRoundRobin.getCabeza();
                nodoEjecutado.setComienzo(tiempoGlobal);
                
                int tiempoEjecutado = 0;
                
                while(nodoEjecutado.getRafaga() > 0 && tiempoEjecutado < 5){
                    
                    nodoEjecutado.setRafaga(nodoEjecutado.getRafaga()-1);
                    
                    label3.setText("Proceso en ejecucion: " + nodoEjecutado.getLlave());
                    label4.setText("Tiempo: " + String.valueOf(tiempoGlobal) + " Segundos.");
                    label8.setText("Rafaga restante del proceso: " + nodoEjecutado.getRafaga());
                    
                    dibujarDiagrama(nodoEjecutado.getLlave(), coorX, nodoEjecutado.getIndice());
                    llenarBloqueados(colaRoundRobin, scrollPane4, scrollPane5);
                    
                    tiempoGlobal++;
                    coorX++;
                    tiempoEjecutado++;
                    
                    cortaLlenaRound();
                    
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ProcesosMulticolas.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
                
                nodoEjecutado.setFinalizacion(tiempoGlobal);
                llenarRestante();
                
                if(nodoEjecutado.getRafaga() == 0){
                
                    colaRoundRobin.eliminar(colaRoundRobin.getCabeza());
                    
                } else if (tiempoEjecutado == 5){
                
                    colaRoundRobin.getCabeza().setLlave(colaRoundRobin.getCabeza().getLlave());
                    colaRoundRobin.intercambiar(colaRoundRobin.getCabeza());
                    
                }
                
            }

            dibujarSemaforo("Verde.jpg");
            label3.setText("Proceso en ejecucion: Ninguno");
            
        }catch(Exception e){
        
            System.out.print("No se que poner aca :D");
            
        }  
    
    }
    
}
