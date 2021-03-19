package datagramaconencabezado;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 *
 * @author David Madrigal Buendía
 * @author David Arturo Oaxaca Pérez
 */
public class Servidor {
    private int puerto;
    private DatagramSocket servidor;
    
    public Servidor() {
        puerto= 4000;
    }
    
    public void iniciar() throws Exception {
        servidor = new DatagramSocket(puerto);
        servidor.setReuseAddress(true);
    }
    
    public void cerrar() throws Exception {
        servidor.close();
    }
    
    public String recibirMensaje() throws Exception {
        byte[] b= new byte[65535];
        DatagramPacket datagrama= new DatagramPacket(b, b.length);
        servidor.receive(datagrama);
        return new String(datagrama.getData(), 0, datagrama.getLength());
    }
}