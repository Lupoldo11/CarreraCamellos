package control;

import mensajes.SendIPMulticast;
import mensajes.ServerReady;

import java.io.*;
import java.net.*;

public class Servidor implements Runnable{
    //Atributos
    public static final int numCliente = 3;
    public static int contador = 0;

    //Atributos del Objeto
    String hiloName;
    String grupoMulticast = "";
    Thread hiloSala;
    MulticastSocket ms;
    InetAddress grupo;
    DatagramPacket paqueteEnvio;
    DatagramPacket paqueteRecibo;
    int puerto;

    //Constructores
    public Servidor(int contador, String ipMulti, int puerto){
        this.puerto = puerto;
        this.hiloName = "Sala"+contador;
        this.grupoMulticast= ipMulti.trim();
        this.hiloSala = new Thread(this, "Sala" + contador);
        System.out.println("Hilo"+contador+" creado");
        try {
            ms = new MulticastSocket();
            grupo = InetAddress.getByName(grupoMulticast);
        } catch (IOException e) {
            System.out.println("Error al hacer el multicast");
        }
    }

    //Metodos
    public Thread getHilo() { return hiloSala; } //conseguir el hilo que usa la instancia

    public void envioPaquete(Object objeto){
        try {
            //Generar byte del objeto
            ByteArrayOutputStream bs= new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream (bs);
            out.writeObject(objeto); //escribir objeto Persona en el stream
            out.close(); //cerrar stream
            byte[] mensaje= bs.toByteArray();

            //Enviar objeto
            paqueteEnvio = new DatagramPacket(mensaje, mensaje.length, grupo, puerto);
            ms.send(paqueteEnvio);
        } catch (IOException e) {
            System.out.println("Error al enviar paquete");
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
                    out.flush();
                    out.writeObject(clientes); //envia el objeto al cliente ->
                    out.flush();
                    out.close(); //cerrar stream

                    cliente.close();
                    //puede aquí cerrarse los clientes ya que se ha mandado la IP
                }

                //Inicia Configuración de la carrera
                Servidor server = new Servidor(contador, clientes.getMSG(), puerto);
                server.getHilo().start();
                contador++;
            }

        } catch (IOException e) {
            System.out.println("Servidor 503"); //Servidor no operativo
        }
    }

    @Override
    public void run() {
        //Enviar que el servidor está listo
        ServerReady ready = new ServerReady();
        ready.setMSG("200");
        envioPaquete(ready);
        //Aquí se administra toda la carrera
    }
}
