package control;

import mensajes.SendIPMulticast;

import java.io.*;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor implements Runnable{
    //Atributos
    public static final int numCliente = 3;
    public static int contador = 0;

    //Atributos del Objeto
    String hiloName;
    String grupoMulticast = "";

    //Constructores
    public Servidor(int contador, String grupo){
        this.hiloName = "Sala"+contador;
        this.grupoMulticast=grupo.trim();
        Thread hilo = new Thread(this, "Sala" + contador);
        System.out.println("Hilo"+contador+" creado");
    }

    //Metodos
    public void conexionMulticast(){
        //aquí se conecta por multicast los 3 clientes y el proceso del servidor
        try {
            MulticastSocket ms = new MulticastSocket();
            InetAddress grupo = InetAddress.getByName(grupoMulticast);

        } catch (IOException e) {
            System.out.println("Server 503");
        }

    }

    //Metodo que saca la IP de clase D que tiene que ir generando
    //*Puede mejorarse para que genere desde el menor rango hasta el mayor (limitar la generación de IPs)
    public static String getGrupo(){
        if(contador >=255 || contador==0 ){
            contador = 1;
        }
        return ("230.0.0."+contador).trim();
    }

    //Ejecutables
    public static void main(String[] args){
        //Conexión TCP
        int puerto = 12345;
        try {
            ServerSocket servidor = new ServerSocket(12345); //Crea el servidor de Clientes

            System.out.println("Esperando conexión...");
            while(true){ //Espera infinita a jugadores

                SendIPMulticast clientes = new SendIPMulticast(getGrupo());//determina la IP de este grupo
                for(int i = 0; i< numCliente; i++){
                    Socket cliente = servidor.accept();

                    //Envio de Objeto SendIPMulticast -> Client
                    ObjectOutputStream out = new ObjectOutputStream (cliente.getOutputStream());
                    out.writeObject(clientes); //envia el objeto al cliente ->
                    out.close(); //cerrar stream

                    cliente.close();
                    //puede aquí cerrarse los clientes ya que se ha mandado la IP
                }

                //Inicia Configuración de la carrera
                Servidor server = new Servidor(contador, clientes.getIPMulti());
                server.conexionMulticast();
                server.run();
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
