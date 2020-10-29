
import java.io.DataInputStream;
import java.io.DataOutputStream;
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
    
    public Cliente(){
        try {
            cl = new Socket("localhost", 3000);
            System.out.println("Conexion con servidor exitosa...");
            enviar = new ObjectOutputStream(cl.getOutputStream());
            recibir = new ObjectInputStream(cl.getInputStream());
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
    public boolean comprar(ArrayList<Articulo> articulos){
        boolean b = false;
        return b;
    }
}
