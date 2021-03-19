package datagramaconencabezado;

import java.util.ArrayList;

/**
 *
 * @author David Madrigal Buendía
 * @author David Arturo Oaxaca Pérez
 */
public class SecoD {
    public static void main(String[] args) {
        Servidor servidor= new Servidor();
        String mensaje, numero_paquete;
        int numero_paquete_tam= 4;
        try {
            servidor.iniciar();
            System.out.println("Servidor iniciado... espedando datagramas..");
            String[] partes_mensaje= new String[10000];
            String mensaje_completo= "";
            for(;;) {
                mensaje_completo= "";
                //Recibimos un mensaje con 4 números al principio ?
                mensaje= servidor.recibirMensaje();
                numero_paquete= mensaje.substring(0, numero_paquete_tam);
                mensaje= mensaje.substring(numero_paquete_tam);
                int numero_paq= Integer.parseInt(numero_paquete);
                /*if(numero_paq == 1) {
                    //Limpiar el arreglo?
                    partes_mensaje= new String[10000];
                }*/
                System.out.println("Se ha recibido el paquete número: " + 
                        numero_paq + ", contenido: " + mensaje);
                partes_mensaje[numero_paq-1]= mensaje;
                //Imprimiendo mensaje (debería ser después de recibir todos los paquetes)
                //Idea: Guardar el número de paquete más grande y usarlo en un for, junto con el string estático con el número máximo de número de paquetes
                for (int i= 0; i < numero_paq; i++) {
                    mensaje_completo+= partes_mensaje[i];
                }
                System.out.println("Mensaje recibido: " + mensaje_completo);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
