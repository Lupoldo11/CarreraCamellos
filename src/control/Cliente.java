package control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args){
        //Conexion TCP
        int puerto = 12345;
        String ipMulticast = "";
        try {
            Socket cliente = new Socket("localhost",12345);
            System.out.println("Conectandose...");

            //Aqui recibir el objeto con la IP Multicast
            BufferedReader entrada = (new BufferedReader(new InputStreamReader(cliente.getInputStream())));
            ipMulticast = entrada.readLine(); //Capta mensaje enviado por servidor (Objeto con MultiCast)

            cliente.close(); //Cierra la TCP

            //Inicio de conexión multicast
            MulticastSocket ms = new MulticastSocket(puerto);
            InetAddress grupo = InetAddress.getByName(); //nombreIPMulticast
            //A partir de aquí hay que administrar la carrera

        } catch (IOException e) {
            System.out.println("Server 503"); //El cliente no se puede conectar
        }

        //Conexión UDP
        try {
            MulticastSocket ms = new MulticastSocket(puerto);//Conexión UDP

            InetAddress grupo = InetAddress.getByName(ipMulticast);
        } catch (IOException e) {
            System.out.println("Error Multicast");
        }
    }
}
