package control;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor implements Runnable{
    //Atributos
    public static final int numCliente = 3;

    //Atributos del Objeto
    Socket[] listCliente;
    String hiloName;

    //Constructores
    public Servidor(Socket[] listCliente, int contador){
        this.hiloName = "Sala"+contador;
        this.listCliente = listCliente;
        Thread hilo = new Thread(this, "Sala" + contador);
        System.out.println("Hilo"+contador+" creado");
    }

    //Metodos
    public void conexionMulticast(){
        //aquí se conecta por multicast los 3 clientes y el proceso del servidor

    }

    //Ejecutables
    public static void main(String[] args){
        //Conexión TCP
        int puerto = 12345;
        try {
            ServerSocket servidor = new ServerSocket(12345); //Crea el servidor de Clientes

            System.out.println("Esperando conexión...");
            int contador = 0;
            while(true){ //Espera infinita a jugadores
                Socket[] list_Cliente = new Socket[numCliente];

                for(int i = 0; i<list_Cliente.length; i++){
                    list_Cliente[i] = servidor.accept();
                }

                Servidor server = new Servidor(list_Cliente, contador);
                server.conexionMulticast();
                contador++;
            }

        } catch (IOException e) {
            System.out.println("Servidor 503"); //Servidor no operativo
        }
    }

    @Override
    public void run() {
        //Aquí se administra toda la carrera
    }
}
