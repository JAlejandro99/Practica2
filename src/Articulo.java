
import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Articulo implements Serializable{
    private String id;
    private String nombre;
    private float precio;
    private int cantidad_existencias;
    private String promocion;
    private Calendar inicio_promo;
    private Calendar fin_promo;
    private String[] imagenes;
    public Articulo(){
        this.inicio_promo = new GregorianCalendar();
        this.fin_promo = new GregorianCalendar();
    }
    public Articulo(String id, String nombre, float precio, int cantidad_existencias, String[] imagenes){
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad_existencias = cantidad_existencias;
        this.promocion = "0";
        this.imagenes = imagenes;
        this.inicio_promo = new GregorianCalendar();
        //inicio_promo.set(2020,10,10);
        this.fin_promo = new GregorianCalendar();
    }
    public String getId(){
        return id;
    }
    public String getNombre(){
        return nombre;
    }
    public float getPrecio(){
        return precio;
    }
    public int getCantidad_Existencias(){
        return cantidad_existencias;
    }
    public String getPromocion(){
        return promocion;
    }
    public String getFechaInicio_promo(){
        return String.valueOf(inicio_promo.get(Calendar.DATE))+"/"+String.valueOf(inicio_promo.get(Calendar.MONTH)+1)+"/"+String.valueOf(inicio_promo.get(Calendar.YEAR));
    }
    public String getFechaFin_promo(){
        return String.valueOf(fin_promo.get(Calendar.DATE))+"/"+String.valueOf(fin_promo.get(Calendar.MONTH)+1)+"/"+String.valueOf(fin_promo.get(Calendar.YEAR));
    }
    public String getImagen(int i){
        return imagenes[i];
    }
    public void setId(String id){
        this.id=id;
    }
    public void setNombre(String nombre){
        this.nombre=nombre;
    }
    public void setPrecio(float precio){
        this.precio=precio;
    }
    public void setCantidad_Existencias(int cantidad_existencias){
        this.cantidad_existencias=cantidad_existencias;
    }
    public void setPromocion(String promocion){
        this.promocion=promocion;
    }
    public void setInicio_promo(int Y, int M, int D){
        this.inicio_promo.set(Y,M,D);
    }
    public void setFin_promo(int Y, int M, int D){
        this.fin_promo.set(Y,M,D);
    }
    public void setImagen(String[] imagenes){
        this.imagenes = imagenes;
    }
}
