package datagramaconencabezado;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 *
 * @author David Madrigal Buendía
 * @author David Arturo Oaxaca Pérez
 */
public class CecoD {
    public static void main(String[] args) {
        ConexionUDP conexion= new ConexionUDP();
        try {
            conexion.iniciar();
            BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
            while(true) {
                System.out.println("Escribe un mensaje, <Entre> para enviar " + 
                        "\"salir\" para terminar");
                String mensaje= br.readLine();
                if(mensaje.compareToIgnoreCase("salir")==0){
                    System.out.println("termina programa");
                    br.close();
                    conexion.cerrar();
                    System.exit(0);
                }else{
                    conexion.enviarMensajeConNumeroPaquete(mensaje);
                }
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}