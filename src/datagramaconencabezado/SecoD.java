package datagramaconencabezado;

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
            int paquete_max;
            int i;
            for(;;) {
                paquete_max= 0;
                for(;;) {
                    mensaje_completo= "";
                    //Recibimos un mensaje con 4 números de paquete al principio
                    mensaje= servidor.recibirMensaje();
                    if(!mensaje.equals("salir")) {
                        numero_paquete= mensaje.substring(0, numero_paquete_tam);
                        mensaje= mensaje.substring(numero_paquete_tam);
                        int numero_paq= Integer.parseInt(numero_paquete);
                        if(paquete_max < numero_paq) {
                            paquete_max= numero_paq;
                        }
                        /*if(numero_paq == 1) {
                            //Limpiar el arreglo?
                            partes_mensaje= new String[10000];
                        }*/
                        System.out.println("Se ha recibido el paquete número: " + 
                                numero_paq + ", contenido: " + mensaje);
                        partes_mensaje[numero_paq-1]= mensaje;
                    }else {
                        break;
                    }
                }
                for (i= 0; i < paquete_max; i++) {
                    mensaje_completo+= partes_mensaje[i];
                }
                //TODO: Hilo donde espere el número máximo de paquetes y cuando lo reciba vaya comparando con cuantos paquetes ha recibido
                System.out.println("Mensaje recibido: " + mensaje_completo);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
