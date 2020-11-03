
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
    private int posicionImagen;
    
    public Articulo(){
        this.inicio_promo = new GregorianCalendar();
        this.fin_promo = new GregorianCalendar();
        posicionImagen = 0;
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
        posicionImagen = 0;
    }
    public String getImagenAnterior(){
        String ret;
        if(posicionImagen>0)
            posicionImagen-=1;
        ret = imagenes[posicionImagen];
        return ret;
    }
    public String getImagenSiguiente(){
        String ret;
        if(posicionImagen<(imagenes.length-1))
            posicionImagen+=1;
        ret = imagenes[posicionImagen];
        return ret;
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
    public Calendar getInicio_promo(){
        return inicio_promo;
    }
    public Calendar getFin_promo(){
        return fin_promo;
    }
    public String getFechaInicio_promo(){
        String date,month;
        date = String.valueOf(inicio_promo.get(Calendar.DATE));
        month = String.valueOf(inicio_promo.get(Calendar.MONTH)+1);
        if(date.length()==1)
            date = "0"+date;
        if(month.length()==1)
            month = "0"+month;
        return String.valueOf(inicio_promo.get(Calendar.YEAR))+"/"+month+"/"+date;
    }
    public String getFechaFin_promo(){
        String date,month;
        date = String.valueOf(fin_promo.get(Calendar.DATE));
        month = String.valueOf(fin_promo.get(Calendar.MONTH)+1);
        if(date.length()==1)
            date = "0"+date;
        if(month.length()==1)
            month = "0"+month;
        return String.valueOf(fin_promo.get(Calendar.YEAR))+"/"+month+"/"+date;
    }
    public String getImagen(int i){
        return imagenes[i];
    }
    public String[] getImagenes(){
        return imagenes;
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
    public void setFechaInicio_promo(int Y, int M, int D){
        this.inicio_promo.set(Y,M,D);
    }
    public void setInicio_promo(Calendar inicio_promo){
        inicio_promo = inicio_promo;
    }
    public void setFechaFin_promo(int Y, int M, int D){
        this.fin_promo.set(Y,M,D);
    }
    public void setFin_promo(Calendar fin_promo){
        this.fin_promo = fin_promo;
    }
    public void setImagenes(String[] imagenes){
        this.imagenes = imagenes;
    }
}
