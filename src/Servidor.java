
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor {
    static ObjectOutputStream enviar;
    static ObjectInputStream recibir;
    public static void main(String[] args) {
        try{
            ServerSocket ss = new ServerSocket(3000);
            System.out.println("Servidor iniciado");
            for(;;){
                boolean terminar = false;
                char opcion = 0;
                Socket cl = ss.accept();
                recibir = new ObjectInputStream(cl.getInputStream());
                enviar = new ObjectOutputStream(cl.getOutputStream());
                System.out.println("Cliente conectado...");
                while(terminar==false){
                    opcion = recibir.readChar();
                    switch(opcion){
                        case 'p':
                            enviarArticulos();
                            break;
                        case 's':
                            terminar = true;
                            recibir.close();
                            enviar.close();
                            cl.close();
                            System.out.println("Conexion cerrada.");
                            break;
                    }
                }
            }//for   
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    static void enviarArticulos(){
        try{
            int totalArticulos;
            ArrayList<Articulo> articulos = leerArticulos();
            totalArticulos = articulos.size();
            enviar.writeInt(totalArticulos);
            enviar.flush();
            for(int i=0;i<totalArticulos;i++){
                //Lo leido del archivo
                enviar.writeObject(articulos.get(i));
                enviar.flush();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    static ArrayList<Articulo> leerArticulos(){
        ArrayList<Articulo> articulos = new ArrayList<Articulo>();
        try{
            File f = new File("ArticulosEnAlmacen.txt");
            FileReader leer = new FileReader(f);
            BufferedReader almacenamiento = new BufferedReader(leer);
            boolean seguir = true;
            String cadena = almacenamiento.readLine();
            int[] fecha;
            while(seguir){
                Articulo art = new Articulo();
                art.setId(cadena);
                art.setNombre(almacenamiento.readLine());
                art.setPrecio(Float.valueOf(almacenamiento.readLine()));
                art.setCantidad_Existencias(Integer.valueOf(almacenamiento.readLine()));
                art.setPromocion(almacenamiento.readLine());
                if(!art.getPromocion().equals("0")){
                    fecha = getFecha(almacenamiento.readLine());
                    art.setInicio_promo(fecha[0],fecha[1],fecha[2]);
                    fecha = getFecha(almacenamiento.readLine());
                    art.setFin_promo(fecha[0],fecha[1],fecha[2]);
                }
                art.setImagen(leerCadenas(almacenamiento.readLine()));
                articulos.add(art);
                cadena = almacenamiento.readLine();
                if(cadena==null)
                    seguir = false;
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return articulos;
    }
    static int[] getFecha(String cad){
        int[] fecha = new int[3];
        fecha[0] = Integer.valueOf(cad.substring(0,4));
        fecha[1] = Integer.valueOf(cad.substring(5,7));
        fecha[2] = Integer.valueOf(cad.substring(8,10));
        return fecha;
    }
    static String[] leerCadenas(String cad){
        ArrayList<String> cadenas = new ArrayList<String>();
        int i,aux=0;
        for(i=0;i<cad.length();i++){
            if(cad.charAt(i)==','){
                cadenas.add(cad.substring(aux,i));
                aux=i+1;
            }
        }
        aux = cadenas.size();
        String[] cadenasret = new String[aux];
        for(i=0;i<aux;i++){
            cadenasret[i] = cadenas.get(i);
        }
        return cadenasret;
    }
}
