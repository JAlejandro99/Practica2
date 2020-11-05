
import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Articulo implements Serializable{
    private String id;
    private String nombre;
    private float precio;
    private int cantidad_existencias;
    private String descripcion;
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
        this.precio = (float)((int)(precio*100))/100;
        this.cantidad_existencias = cantidad_existencias;
        this.promocion = "0";
        this.imagenes = imagenes;
        this.inicio_promo = new GregorianCalendar();
        //inicio_promo.set(2020,10,10);
        this.fin_promo = new GregorianCalendar();
        posicionImagen = 0;
    }
    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }
    public String getDescripcion(){
        return this.descripcion;
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
        Float precioFinal = precio;
        if(Integer.valueOf(promocion)>0){
            precioFinal = precio*(1-Float.valueOf(promocion)/100);
        }
        return precioFinal;
    }
    public float getPrecio2(){
        return precio;
    }
    public String getPrecioComas(){
        float precioFinal = precio;
        if(Integer.valueOf(promocion)>0){
            precioFinal = precio*(1-Float.valueOf(promocion)/100);
        }
        String ret = String.valueOf(redondearDecimales(precioFinal,2));
        if(ret.length()>6){
            ret = ret.substring(0,ret.length()-6)+","+ret.substring(ret.length()-6);
            if(ret.length()>10)
                ret = ret.substring(0,ret.length()-10)+","+ret.substring(ret.length()-10);
        }
        if(Integer.valueOf(promocion)>0){
            String ret2 = String.valueOf(redondearDecimales(precio,2));
            if(ret2.charAt(ret2.length()-2)=='.')
                ret2+="0";
            if(ret2.length()>6){
                ret2 = ret2.substring(0,ret2.length()-6)+","+ret2.substring(ret2.length()-6);
                if(ret2.length()>10)
                    ret2 = ret2.substring(0,ret2.length()-10)+","+ret2.substring(ret2.length()-10);
            }
            ret = "De $"+ret2+" a solo $"+ret;
        }else{
            ret = "$"+ret;
        }
        return ret;
    }
    public float redondearDecimales(float valorInicial, int numeroDecimales) {
        float parteEntera, resultado;
        resultado = valorInicial;
        parteEntera = (float) Math.floor(resultado);
        resultado=(float) ((resultado-parteEntera)*Math.pow(10, numeroDecimales));
        resultado=Math.round(resultado);
        resultado=(float) ((resultado/Math.pow(10, numeroDecimales))+parteEntera);
        return resultado;
    }
    public String getPrecioComas2(){
        float p = precio;
        String ret = String.valueOf(redondearDecimales(p,2));
        //if(ret.charAt(ret.length()-2)=='.')
            //ret+="0";
        if(ret.length()>6){
            ret = ret.substring(0,ret.length()-6)+","+ret.substring(ret.length()-6);
            if(ret.length()>10)
                ret = ret.substring(0,ret.length()-10)+","+ret.substring(ret.length()-10);
        }
        ret = "$"+ret;
        return ret;
    }
    public String getCantidadPromocion(){
        String ret = String.valueOf(precio*Float.valueOf(promocion)/100);
        if(ret.charAt(ret.length()-2)=='.')
            ret+="0";
        if(ret.length()>6){
            ret = ret.substring(0,ret.length()-6)+","+ret.substring(ret.length()-6);
            if(ret.length()>10)
                ret = ret.substring(0,ret.length()-10)+","+ret.substring(ret.length()-10);
        }
        ret = "-$"+ret;
        return ret;
    }
    public int getCantidad_Existencias(){
        return cantidad_existencias;
    }
    public String getPromocion2(){
        return promocion;
    }
    public String getPromocion(){
        Calendar fechaActual = Calendar.getInstance();
        String ret = "0";
        if(Integer.valueOf(promocion)>0){
            if(fechaActual.after(inicio_promo)){
                if(fin_promo.after(fechaActual)){
                    ret = promocion;
                }
            }
        }
        return ret;
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
        month = String.valueOf(inicio_promo.get(Calendar.MONTH));
        if(date.length()==1)
            date = "0"+date;
        if(month.length()==1)
            month = "0"+month;
        return String.valueOf(inicio_promo.get(Calendar.YEAR))+"/"+month+"/"+date;
    }
    public String getFechaInicio_promo2(){
        String date,month;
        date = String.valueOf(inicio_promo.get(Calendar.DATE));
        month = String.valueOf(inicio_promo.get(Calendar.MONTH)+1);
        if(date.length()==1)
            date = "0"+date;
        if(month.length()==1)
            month = "0"+month;
        return date+"/"+month+"/"+String.valueOf(inicio_promo.get(Calendar.YEAR));
    }
    public String getFechaFin_promo(){
        String date,month;
        date = String.valueOf(fin_promo.get(Calendar.DATE));
        month = String.valueOf(fin_promo.get(Calendar.MONTH));
        if(date.length()==1)
            date = "0"+date;
        if(month.length()==1)
            month = "0"+month;
        return String.valueOf(fin_promo.get(Calendar.YEAR))+"/"+month+"/"+date;
    }
    public String getFechaFin_promo2(){
        String date,month;
        date = String.valueOf(fin_promo.get(Calendar.DATE));
        month = String.valueOf(fin_promo.get(Calendar.MONTH)+1);
        if(date.length()==1)
            date = "0"+date;
        if(month.length()==1)
            month = "0"+month;
        return date+"/"+month+"/"+String.valueOf(fin_promo.get(Calendar.YEAR));
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
