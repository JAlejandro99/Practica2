import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.Document;

public class Servidor {
    static ObjectOutputStream enviar;
    static ObjectInputStream recibir;
    static String ruta_archivos,ruta_archivos2;
    public static void main(String[] args) {
        try{
            ServerSocket ss = new ServerSocket(3000);
            System.out.println("Servidor iniciado");
            File f = new File("");
            String ruta = f.getAbsolutePath();
            String carpeta="ImagenesAdministrador";
            ruta_archivos = ruta+"\\"+carpeta+"\\";
            ruta_archivos2 = ruta+"\\TicketsAdministrador\\";
            System.out.println("ruta Imagenes:"+ruta_archivos);
            File f2 = new File(ruta_archivos);
            f2.mkdirs();//Haces la dirección, mkdir te hace un nivel de directorio, el mkdirs te hace todas las estructuras de directorio, aquí se pudo haber ocupado solo mkdir
            f2.setWritable(true);
            System.out.println("ruta Tickets:"+ruta_archivos2);
            File f3 = new File(ruta_archivos2);
            f3.mkdirs();//Haces la dirección, mkdir te hace un nivel de directorio, el mkdirs te hace todas las estructuras de directorio, aquí se pudo haber ocupado solo mkdir
            f3.setWritable(true);
            for(;;){
                char opcion = 0;
                Socket cl = ss.accept();
                recibir = new ObjectInputStream(cl.getInputStream());
                enviar = new ObjectOutputStream(cl.getOutputStream());
                System.out.println("Cliente conectado...");
                opcion = recibir.readChar();
                switch(opcion){
                    case 'p':
                        enviarArticulos();
                        break;
                    case 'i':
                        enviarImagenes();
                        break;
                    case 'c':
                        procesarCompra();
                        break;
                    }
                recibir.close();
                enviar.close();
                cl.close();
                System.out.println("Conexion cerrada.");
            }//for   
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    static void enviarImagenes(){
        try{
            File f2 = new File(ruta_archivos);
            File[] f = f2.listFiles();
            int aux = f.length,l,porcentaje;
            String nombre;
            String path;
            long tam, enviados;
            enviar.writeInt(aux);
            enviar.flush();
            for(int i=0;i<aux;i++){
                //System.out.println("Archivo "+nombre[i]+"; path: "+path[i]+"; tamano: "+tam[i]+";");
                nombre = f[i].getName();
                path = f[i].getAbsolutePath();
                tam = f[i].length();
                System.out.println("\nPreparandose pare enviar archivo "+path+" de "+tam+" bytes");
                DataInputStream dis = new DataInputStream(new FileInputStream(path));
                enviar.writeUTF(nombre);
                enviar.flush();
                enviar.writeLong(tam);
                enviar.flush();
                enviados = 0;
                l=0;
                porcentaje=0;
                while(enviados<tam){
                    byte[] b = new byte[1500];
                    l=dis.read(b);
                    //System.out.print("\nEnviados: "+l);
                    enviar.write(b,0,l);
                    enviar.flush();
                    enviados = enviados + l;
                    porcentaje = (int)((enviados*100)/tam);
                    //System.out.print(", enviado el "+porcentaje+" % del archivo "+nombre[i]);
                }//while
                System.out.println("Archivo "+nombre+" enviado...");
                dis.close();
            }
        }catch(IOException e){
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
                art.setDescripcion(almacenamiento.readLine());
                art.setPromocion(almacenamiento.readLine());
                if(!art.getPromocion2().equals("0")){
                    fecha = getFecha(almacenamiento.readLine());
                    art.setFechaInicio_promo(fecha[0],fecha[1],fecha[2]);
                    fecha = getFecha(almacenamiento.readLine());
                    art.setFechaFin_promo(fecha[0],fecha[1],fecha[2]);
                }
                art.setImagenes(leerCadenas(almacenamiento.readLine()));
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
    static void procesarCompra(){
        ArrayList<Articulo> articulos = new ArrayList<Articulo>();
        try{
            int totalArticulos = recibir.readInt();
            for(int i=0;i<totalArticulos;i++){
                Articulo art = (Articulo) recibir.readObject();
                articulos.add(art);
            }
        }catch(IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        eliminarArticulosInventario(articulos);
        String nombreTicket = generarTicket(articulos);
        enviarTicket(nombreTicket);
    }
    static void enviarTicket(String nombreTicket){
        try{
            File f = new File(ruta_archivos2+nombreTicket);
            int l,porcentaje;
            String nombre;
            String path;
            long tam, enviados;
            //System.out.println("Archivo "+nombre[i]+"; path: "+path[i]+"; tamano: "+tam[i]+";");
            nombre = f.getName();
            path = f.getAbsolutePath();
            tam = f.length();
            System.out.println("\nPreparandose pare enviar archivo "+path+" de "+tam+" bytes");
            DataInputStream dis = new DataInputStream(new FileInputStream(path));
            enviar.writeUTF(nombre);
            enviar.flush();
            enviar.writeLong(tam);
            enviar.flush();
            enviados = 0;
            l=0;
            porcentaje=0;
            while(enviados<tam){
                byte[] b = new byte[1500];
                l=dis.read(b);
                //System.out.print("\nEnviados: "+l);
                enviar.write(b,0,l);
                enviar.flush();
                enviados = enviados + l;
                porcentaje = (int)((enviados*100)/tam);
                //System.out.print(", enviado el "+porcentaje+" % del archivo "+nombre[i]);
            }//while
            System.out.println("Archivo "+nombre+" enviado...");
            dis.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    static void eliminarArticulosInventario(ArrayList<Articulo> articulos){
        ArrayList<Articulo> articulosViejo = leerArticulos();
        for(int i=0;i<articulosViejo.size();i++){
            for(int j=0;j<articulos.size();j++){
                if(articulosViejo.get(i).getId().equals(articulos.get(j).getId()))
                    articulosViejo.get(i).setCantidad_Existencias(articulosViejo.get(i).getCantidad_Existencias()-articulos.get(j).getCantidad_Existencias());
            }
        }
        guardarArticulosInventario(articulosViejo);
    }
    static void guardarArticulosInventario(ArrayList<Articulo> articulos){
        try{
            File f = new File("ArticulosEnAlmacen.txt");
            PrintWriter pw = new PrintWriter(f);
            f.createNewFile();
            int[] fecha;
            for(int i=0;i<articulos.size();i++){
                Articulo art = articulos.get(i);
                pw.println(art.getId());
                pw.println(art.getNombre());
                pw.println(String.valueOf(art.getPrecio2()));
                pw.println(String.valueOf(art.getCantidad_Existencias()));
                pw.println(art.getDescripcion());
                pw.println(art.getPromocion2());
                if(!art.getPromocion2().equals("0")){
                    pw.println(art.getFechaInicio_promo());
                    pw.println(art.getFechaFin_promo());
                }
                for(int j=0;j<art.getImagenes().length;j++){
                    pw.print(art.getImagen(j)+",");
                }
                pw.println();
            }
            pw.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    static String generarTicket(ArrayList<Articulo> articulos){
        String ticket;
        File fAux = new File(ruta_archivos2);
        File[] fAux2 = fAux.listFiles();
        ticket="ticket"+String.valueOf(fAux2.length+1)+".txt";
        try{
            File f = new File(ruta_archivos2+ticket);
            PrintWriter pw = new PrintWriter(f);
            float total = 0;
            String p;
            f.createNewFile();
            Calendar fechaCompra = Calendar.getInstance();
            pw.println("Número de ticket: "+String.valueOf(fAux2.length+1));
            pw.println("Fecha de compra: "+String.valueOf(fechaCompra.get(Calendar.DATE))+"/"+String.valueOf(fechaCompra.get(Calendar.MONTH)+1)+"/"+String.valueOf(fechaCompra.get(Calendar.YEAR)));
            pw.println();
            pw.println("Id      /Producto    /Cantidad    /Precio");
            for(int i=0;i<articulos.size();i++){
                Articulo art = articulos.get(i);
                pw.println(art.getId()+"      "+art.getNombre()+"      "+String.valueOf(art.getCantidad_Existencias())+"      "+art.getPrecioComas2());
                if(Integer.valueOf(art.getPromocion())>0)
                    pw.println("              Descuento por cada "+art.getNombre()+"      "+art.getCantidadPromocion());
                total = total+art.getCantidad_Existencias()*art.getPrecio();
            }
            pw.println();
            p = String.valueOf(redondearDecimales(total,2));
            //if(p.charAt(p.length()-2)=='.')
            //        p=p+"0";
            if(p.length()>6){
                p = p.substring(0,p.length()-6)+","+p.substring(p.length()-6);
                if(p.length()>10)
                    p = p.substring(0,p.length()-10)+","+p.substring(p.length()-10);
            }
            pw.println("Total:      $"+p);
            pw.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        return ticket;
    }
    public static float redondearDecimales(float valorInicial, int numeroDecimales) {
        float parteEntera, resultado;
        resultado = valorInicial;
        parteEntera = (float) Math.floor(resultado);
        resultado=(float) ((resultado-parteEntera)*Math.pow(10, numeroDecimales));
        resultado=Math.round(resultado);
        resultado=(float) ((resultado/Math.pow(10, numeroDecimales))+parteEntera);
        return resultado;
    }
}
