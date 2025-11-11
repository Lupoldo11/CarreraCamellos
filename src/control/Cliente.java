package control;

import mensajes.SendIPMulticast;
import mensajes.ServerReady;

import java.io.*;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;

public class Cliente {
    //atributos
    String ipMulti;
    MulticastSocket ms;
    InetAddress grupo;

    //Constructores
    public Cliente(String ip, MulticastSocket ms, InetAddress grupo){
        this.ipMulti = ip.trim();
        this.ms=ms;
        this.grupo=grupo;
    }

    //metodos
    public void inicioCarrera(){
        //aqui hacer lo que sea con la carrera
        //Esperar el listo del servidor con el boton en "no pulsable"
        //El server envia un mensaje de cambio el boton a pulsable
        //Si le da al botón enviar a todos que se ha iniciado la carrera
        System.out.println("Paso hasta aqui");

        while (true){
            try {
                byte[] recibido = new byte[4096];
                DatagramPacket recibo = new DatagramPacket(recibido, recibido.length);
                ms.receive(recibo);

                ByteArrayInputStream in = new ByteArrayInputStream(recibido);
                ObjectInputStream ois = new ObjectInputStream(in);
                ServerReady sv = (ServerReady) ois.readObject();
                ois.close();
                in.close();

                System.out.println(sv.getMSG());

            } catch (IOException e) {
                System.out.println("Error recibir paquete");
            } catch (ClassNotFoundException e) {
                System.out.println("No castea el objeto");
            }
        }
    }

    //ejecutable
    public static void main(String[] args){
        //Conexion TCP
        int puerto = 12345;
        String ipMulticast = "";
        try {
            Socket cliente = new Socket("localhost",12345);
            System.out.println("Conectandose...");

            //Aqui recibir el objeto con la IP Multicast
            ObjectInputStream in = new ObjectInputStream(cliente.getInputStream());
            SendIPMulticast IPMulti = (SendIPMulticast) in.readObject();
            in.close(); //Capta mensaje enviado por servidor (Objeto con MultiCast) <-

            cliente.close(); //Cierra la TCP

            //Inicio de conexión multicast UDP
            MulticastSocket ms = new MulticastSocket(puerto);
            InetAddress grupo = InetAddress.getByName(IPMulti.getIPMulti()); //nombreIPMulticast

            //A partir de aquí hay que administrar la carrera
            Cliente camello = new Cliente(IPMulti.getIPMulti(), ms, grupo);
            camello.inicioCarrera();
        } catch (IOException e) {
            System.out.println("Server 503"); //El cliente no se puede conectar
        } catch (ClassNotFoundException e) {
            System.out.println("Error al leer o castear el objeto");
        }
    }
}
