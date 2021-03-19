package datagramaconencabezado;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

/**
 *
 * @author David Madrigal Buendía
 * @author David Arturo Oaxaca Pérez
 */
public class ConexionUDP {
    private int puerto;
    private String direccion; 
    private InetAddress destino;
    private int tam;
    private DatagramSocket cliente;
    private int tam_no_paquete;
    
    public ConexionUDP() {
        puerto= 4000;
        direccion= "localhost";
        tam_no_paquete= 4;
    }
    
    public void getDestino() throws Exception {
        destino= InetAddress.getByName(direccion);
    }
    
    public void iniciar() throws Exception {
        cliente = new DatagramSocket();
    }
    
    public void cerrar() throws Exception {
        cliente.close();
    }
    
    public void enviarMensajeConNumeroPaquete(String mensaje) throws Exception {
        tam= 10;
        int i= 0;
        byte[] b= mensaje.getBytes();
        String paquete;
        int paquetes= (int) (b.length/tam); // Truena con cero?
        getDestino();
        byte[] bytes_mensaje_paquete;
        if(b.length > tam) {
            byte[] b_eco= new byte[b.length];
            for(i= 0; i < paquetes; i++) {
                bytes_mensaje_paquete= Arrays.copyOfRange(b, i*tam, ((i*tam)+(tam)));
                bytes_mensaje_paquete= construirPaqueteConMetadata(i, bytes_mensaje_paquete);
                DatagramPacket datagrama= new DatagramPacket(bytes_mensaje_paquete, bytes_mensaje_paquete.length, destino, puerto);
                cliente.send(datagrama);
                System.out.println("Enviando fragmento: " + (i+1) + " de " + (paquetes+1) + 
                        "\n desde " + (i * tam) + " hasta " + ((i * tam) + tam));
            }
        }
        if(b.length % tam > 0) {
            int sobrantes= b.length % tam;
            bytes_mensaje_paquete= Arrays.copyOfRange(b, i*tam, ((i*tam)+(sobrantes)));
            bytes_mensaje_paquete= construirPaqueteConMetadata(i, bytes_mensaje_paquete);
            DatagramPacket datagrama= new DatagramPacket(bytes_mensaje_paquete, bytes_mensaje_paquete.length, destino, puerto);
            cliente.send(datagrama);
            System.out.println("Enviando fragmento: " + (i+1) + " de " + (paquetes+1) + 
                    "\n desde " + (i * tam) + " hasta " + ((i * tam) + tam));
        }else {
            bytes_mensaje_paquete= mensaje.getBytes();
            bytes_mensaje_paquete= construirPaqueteConMetadata(i, bytes_mensaje_paquete);
            DatagramPacket datagrama= new DatagramPacket(bytes_mensaje_paquete, bytes_mensaje_paquete.length, destino, puerto);
            cliente.send(datagrama);
            System.out.println("Enviando fragmento: " + (i+1) + " de " + (paquetes+1) + 
                    "\n desde " + (i * tam) + " hasta " + ((i * tam) + tam));
        }
    }
    
    private byte[] construirPaqueteConMetadata(int no_paquete, byte[] bytes_mensaje_paquete) {
        String numero_paquete= rellenar(no_paquete);
        byte[] metadata= numero_paquete.getBytes();
        byte[] datagrama= new byte[metadata.length + bytes_mensaje_paquete.length];
        System.arraycopy(metadata, 0, datagrama, 0, metadata.length);
        System.arraycopy(bytes_mensaje_paquete, 0, datagrama, metadata.length, bytes_mensaje_paquete.length);    
        return datagrama;
    }
    
    private String rellenar(int numero_paquete) {
        numero_paquete++;
        String paquete;
        if(numero_paquete / 1000 >= 1) {
            paquete= "" + numero_paquete;
        }else if(numero_paquete / 100 >= 1) {
            paquete= "0" + numero_paquete;
        }else if(numero_paquete / 10 >= 1) {
            paquete= "00" + numero_paquete;
        }else {
            paquete= "000" + numero_paquete;
        }
        return paquete;
    }
}