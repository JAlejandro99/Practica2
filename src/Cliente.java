import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente {
    private ObjectInputStream recibir;
    private ObjectOutputStream enviar;
    Socket cl;
    String ruta_archivos;
    
    public Cliente(){
        try {
            cl = new Socket("localhost", 3000);
            System.out.println("Conexion con servidor exitosa...");
            enviar = new ObjectOutputStream(cl.getOutputStream());
            recibir = new ObjectInputStream(cl.getInputStream());
            File f = new File("");
            String ruta = f.getAbsolutePath();
            String carpeta="Imagenes";
            ruta_archivos = ruta+"\\"+carpeta+"\\";
            System.out.println("ruta:"+ruta_archivos);
            File f2 = new File(ruta_archivos);
            f2.mkdirs();//Haces la dirección, mkdir te hace un nivel de directorio, el mkdirs te hace todas las estructuras de directorio, aquí se pudo haber ocupado solo mkdir
            f2.setWritable(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void salir(){
        try{
            enviar.writeChar('s');
            enviar.flush();
            cl.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public ArrayList<Articulo> pedirArticulos(){
        ArrayList<Articulo> articulos = new ArrayList<Articulo>();
        try{
            enviar.writeChar('p');
            enviar.flush();
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
        return articulos;
    }
    public void pedirImagenes(){
        try{
            int l,porcentaje,aux;
            long recibidos,tam;
            String nombre;
            enviar.writeChar('i');
            enviar.flush();
            aux = recibir.readInt();
            for(int i=0;i<aux;i++){
                nombre = recibir.readUTF();
                tam = recibir.readLong();
                System.out.println("\nComienza descarga del archivo "+nombre+" de "+tam+" bytes");
                DataOutputStream dos = new DataOutputStream(new FileOutputStream(ruta_archivos+"\\"+nombre));
                recibidos=0;
                l=0;
                porcentaje=0;//l es para saber cuandos bytes se leyeron en el Socket
                while(recibidos<tam){
                    byte[] b = new byte[1500];
                    l = recibir.read(b);
                    //System.out.print("\nLeidos: "+l);
                    dos.write(b,0,l);
                    dos.flush();
                    recibidos = recibidos + l;
                    porcentaje = (int)((recibidos*100)/tam);
                    System.out.print(", recibido el "+ porcentaje +" % del archivo");
                }//while
                System.out.println("Archivo recibido..");
                dos.close();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public String comprar(ArrayList<Articulo> articulos){
        String ret = "";
        try{
            int totalArticulos;
            totalArticulos = articulos.size();
            enviar.writeChar('c');
            enviar.flush();
            enviar.writeInt(totalArticulos);
            enviar.flush();
            for(int i=0;i<totalArticulos;i++){
                //Lo leido del archivo
                enviar.writeObject(articulos.get(i));
                enviar.flush();
            }
            int l,porcentaje;
            long recibidos,tam;
            String nombre;
            nombre = recibir.readUTF();
            ret = nombre;
            tam = recibir.readLong();
            System.out.println("\nComienza descarga del archivo "+nombre+" de "+tam+" bytes");
            DataOutputStream dos = new DataOutputStream(new FileOutputStream(ruta_archivos+"\\"+nombre));
            recibidos=0;
            l=0;
            porcentaje=0;//l es para saber cuandos bytes se leyeron en el Socket
            while(recibidos<tam){
                byte[] b = new byte[1500];
                l = recibir.read(b);
                //System.out.print("\nLeidos: "+l);
                dos.write(b,0,l);
                dos.flush();
                recibidos = recibidos + l;
                porcentaje = (int)((recibidos*100)/tam);
                System.out.print(", recibido el "+ porcentaje +" % del archivo");
            }//while
            System.out.println("Archivo recibido..");
            dos.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        return ret;
    }
    public String getRuta_archivos(){
        return this.ruta_archivos;
    }
}
