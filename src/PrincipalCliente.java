import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

public class PrincipalCliente extends javax.swing.JFrame {
    
    private Cliente clt;
    private ArrayList<Articulo> articulos;
    private ArrayList<Articulo> carrito;
    private boolean inicio;
    
    public PrincipalCliente() {
        initComponents();
        jPanelContenedor.setPreferredSize(new Dimension(100, 5000));
        articulos = new ArrayList<Articulo>();
        carrito = new ArrayList<Articulo>();
        clt = new Cliente();
        inicio = true;
        File f = new File(clt.getRuta_archivos2());
        File[] f2 = f.listFiles();
        if(f2.length>0){
            for(int i=0;i<f2.length;i++){
                this.verTicket.addItem(f2[i].getName());
            }
        }
        cargarProductos();
    }
    public void cargarProductos(){
        articulos.clear();
        articulos = clt.pedirArticulos();
        clt.pedirImagenes();
        cargarPlantillaProductos();
        cargarPlantillaCarrito();
    }
    public void cargarProductos2(){
        articulos.clear();
        articulos = clt.pedirArticulos();
        cargarPlantillaProductos();
        cargarPlantillaCarrito();
    }
    
    //Recorre todo el arreglo de ARTICULOS para crear la plantilla de cada Articulo
    public void cargarPlantillaProductos(){
        jPanelContenedor.removeAll();
        for(int i=0; i<articulos.size();i++){
            if(articulos.get(i).getCantidad_Existencias()>0)
                crearPlantillaProducto(articulos.get(i), i);
        }
        jPanelContenedor.updateUI();
    }
    public String totalCarrito(){
        float total=(float) 0.00;
        String ret;
        for(int i=0;i<carrito.size();i++)
            total+=carrito.get(i).getPrecio()*carrito.get(i).getCantidad_Existencias();
        ret = String.valueOf(redondearDecimales(total,2));
        if(ret.charAt(ret.length()-2)=='.')
                    ret=ret+"0";
        if(ret.length()>6){
            ret = ret.substring(0,ret.length()-6)+","+ret.substring(ret.length()-6);
            if(ret.length()>10)
                ret = ret.substring(0,ret.length()-10)+","+ret.substring(ret.length()-10);
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
    //Recorre todo el arreglo del carrito para crear las Plantillas de cada Articulo
    public void cargarPlantillaCarrito(){
        jPanelCarrit.removeAll();
        for(int i=0; i<carrito.size();i++){
            crearPlantillaProductoCarrito(carrito.get(i), i);
        }
        jLabelTotal.setText(totalCarrito());
        jPanelCarrit.updateUI();
    }
    
    
    //Recibe un articulo y su indice en el arreglo de Articulos
    public void crearPlantillaProducto(Articulo A, int i){
        
        JPanel JArticuloContenedor = new JPanel();
        JArticuloContenedor.setLayout(new GridLayout(1,2));
        JArticuloContenedor.setBackground(Color.red);
        JArticuloContenedor.setPreferredSize(new Dimension(1300, 500));
  
 //CONTENERDOR IZQUIERDO
        JPanel ContenedorIzq = new JPanel();
        ContenedorIzq.setLayout(new BorderLayout());
        
        JPanel BotonesImg = new JPanel();
        BotonesImg.setLayout(new GridLayout(1,2));
        JButton btnS = new JButton("Siguiente");
        btnS.setFont(new Font("TimesRoman", Font.PLAIN, 30));;
        JButton btnA = new JButton("Anterior"); 
        btnA.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        JPanel Img = new JPanel();
        String imagen = "Imagenes\\"+articulos.get(i).getImagen(0);//WHOOOO!!
        
        Img.setLayout(new GridLayout(1,1));
        JButton imageicon = new JButton();
        imageicon.setPreferredSize(new Dimension(300,300));
        //imageicon.setBackground(Color.WHITE);
        imageicon.setContentAreaFilled(false); //QUITAR FONDO
        //imageicon.setBorder(null);
        imageicon.setIcon(new ImageIcon(imagen));
        Img.add(imageicon);
        ContenedorIzq.add(Img, BorderLayout.CENTER);
    //LISTENERS BOTONES SIGUIENTE Y ATRAS
        btnS.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    imageicon.setIcon(new ImageIcon("Imagenes\\"+articulos.get(i).getImagenSiguiente()));
                }                
        });
        btnA.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    imageicon.setIcon(new ImageIcon("Imagenes\\"+articulos.get(i).getImagenAnterior()));
                }                
        });
        BotonesImg.add(btnA);
        BotonesImg.add(btnS);
        
        ContenedorIzq.add(BotonesImg, BorderLayout.NORTH);
 //CONTENEDOR DERECHO
        //JPanel ContenedorDer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel ContenedorDer = new JPanel(new GridLayout(8,1));
        ContenedorDer.setBackground(Color.WHITE);
        
        JLabel nombreArt = new JLabel(".    "+A.getNombre().toUpperCase()+"");
        nombreArt.setFont(new Font("Agency FB", Font.BOLD, 45));
        
        JLabel precioArt = new JLabel("+   Precio: "+A.getPrecioComas());
        precioArt.setFont(new Font("Agency FB", Font.PLAIN, 40));
        
        JLabel promocionArt = new JLabel();
        JLabel promocion2Art = new JLabel();
        if(Integer.valueOf(A.getPromocion())>0){
            promocionArt = new JLabel("+   Obten un "+A.getPromocion()+"% de descuento en la compra de este producto,");
            promocionArt.setFont(new Font("Agency FB", Font.PLAIN, 30));
            promocion2Art = new JLabel("   promoción válida únicamente del "+A.getFechaInicio_promo2()+" al "+A.getFechaFin_promo2());
            promocion2Art.setFont(new Font("Agency FB", Font.PLAIN, 30));
        }
        
        JLabel descripArt = new JLabel("+   Descripcion:  "+A.getDescripcion());
        descripArt.setFont(new Font("Agency FB", Font.PLAIN, 30));
        
        JLabel disponiArt = new JLabel("+   Disponibles:  "+A.getCantidad_Existencias()+" Articulos.");
        disponiArt.setFont(new Font("Agency FB", Font.PLAIN, 40));
       
        JPanel jcantidadnumero = new JPanel(new GridLayout(1,2));
            JLabel lblcant = new JLabel("+   Cantidad: ");
            lblcant.setFont(new Font("Agency FB", Font.PLAIN, 45));

            JSpinner Jcantidad = new JSpinner();
                                      //SpinnerNumberModel(value, min, max, step);
            SpinnerNumberModel sm = new SpinnerNumberModel(1, 1, A.getCantidad_Existencias(), 1);
            Jcantidad.setModel(sm);
            Jcantidad.setFont(new Font("TimesRoman", Font.PLAIN, 40));
            Jcantidad.setValue(1);
           jcantidadnumero.add(lblcant);
           jcantidadnumero.add(Jcantidad);
           
        JButton btnAgregar = new JButton("Agregar al Carrito");
        btnAgregar.setPreferredSize(new Dimension(400,30));
        btnAgregar.setFont(new Font("TimesRoman", Font.PLAIN, 35));
        btnAgregar.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    int cantidad = (Integer) Jcantidad.getValue();
                    agregarCarrito(A,cantidad);
                    //VER SI FALTA ALGUNA ACCION AQUI
                }                
        });
        
        ContenedorDer.add(nombreArt);
        ContenedorDer.add(precioArt);
        if(Integer.valueOf(A.getPromocion())>0){
            ContenedorDer.add(promocionArt);
            ContenedorDer.add(promocion2Art);
        }
        ContenedorDer.add(descripArt);
        ContenedorDer.add(disponiArt);
        ContenedorDer.add(jcantidadnumero);
        ContenedorDer.add(btnAgregar);
        

        JArticuloContenedor.add(ContenedorIzq);
        JArticuloContenedor.add(ContenedorDer);
        
        jPanelContenedor.add(JArticuloContenedor);
    }
    
    
    //Recibe el articulo a agregar y su indice en el arreglo del carrito
    public void crearPlantillaProductoCarrito(Articulo A, int indice){
        
        JPanel JArticuloContenedor = new JPanel();
        JArticuloContenedor.setLayout(new GridLayout(1,2));
        JArticuloContenedor.setBackground(Color.red);
        JArticuloContenedor.setPreferredSize(new Dimension(1300, 500));
  
 //CONTENERDOR IZQUIERDO
        JPanel ContenedorIzq = new JPanel();
        ContenedorIzq.setLayout(new BorderLayout());
        
        JPanel BotonesImg = new JPanel();
        BotonesImg.setLayout(new GridLayout(1,2));
        JButton btnS = new JButton("Siguiente");
        btnS.setFont(new Font("TimesRoman", Font.PLAIN, 30));;
        JButton btnA = new JButton("Anterior"); 
        btnA.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        JPanel Img = new JPanel();
        String imagen = "Imagenes\\"+A.getImagen(0);//WHOOOO!!
        
        Img.setLayout(new GridLayout(1,1));
        JButton imageicon = new JButton();
        imageicon.setPreferredSize(new Dimension(300,300));
        imageicon.setContentAreaFilled(false); //QUITAR FONDO
        imageicon.setIcon(new ImageIcon(imagen));
        Img.add(imageicon);
        ContenedorIzq.add(Img, BorderLayout.CENTER);
    //LISTENERS BOTONES SIGUIENTE Y ATRAS
        btnS.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    imageicon.setIcon(new ImageIcon("Imagenes\\"+A.getImagenSiguiente()));
                }                
        });
        btnA.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    imageicon.setIcon(new ImageIcon("Imagenes\\"+A.getImagenAnterior()));
                }                
        });
        BotonesImg.add(btnA);
        BotonesImg.add(btnS);
        
        ContenedorIzq.add(BotonesImg, BorderLayout.NORTH);
 //CONTENEDOR DERECHO
        //JPanel ContenedorDer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel ContenedorDer = new JPanel(new GridLayout(7,1));
        ContenedorDer.setBackground(Color.WHITE);
        
        JLabel nombreArt = new JLabel(".    "+A.getNombre().toUpperCase()+"");
        nombreArt.setFont(new Font("Agency FB", Font.BOLD, 45));
        
        JLabel precioArt = new JLabel("+   Precio: "+A.getPrecioComas());
        precioArt.setFont(new Font("Agency FB", Font.PLAIN, 40));
        
        JLabel promocionArt = new JLabel();
        JLabel promocion2Art = new JLabel();
        if(Integer.valueOf(A.getPromocion())>0){
            promocionArt = new JLabel("+   Obten un "+A.getPromocion()+"% de descuento en la compra de este producto,");
            promocionArt.setFont(new Font("Agency FB", Font.PLAIN, 30));
            promocion2Art = new JLabel("   promoción válida únicamente del "+A.getFechaInicio_promo2()+" al "+A.getFechaFin_promo2());
            promocion2Art.setFont(new Font("Agency FB", Font.PLAIN, 30));
        }
        
        JLabel descripArt = new JLabel("+   Descripcion:  "+A.getDescripcion());
        descripArt.setFont(new Font("Agency FB", Font.PLAIN, 30));
        
        JLabel disponiArt = new JLabel("+   Seleccionados:  "+A.getCantidad_Existencias()+" Articulos.");
        disponiArt.setFont(new Font("Agency FB", Font.PLAIN, 40));
       
           
        JButton btnEliminar = new JButton("Eliminar del Carrito");
        btnEliminar.setPreferredSize(new Dimension(400,30));
        btnEliminar.setFont(new Font("TimesRoman", Font.PLAIN, 35));
        btnEliminar.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    int cantidad = Integer.valueOf(JOptionPane.showInputDialog("Ingresa el número de articulos que quieres eliminar de tu carrito."));
                    if(cantidad>A.getCantidad_Existencias())
                        cantidad = A.getCantidad_Existencias();
                    eliminardeCarrito(indice,cantidad);
                }
        });
        
        ContenedorDer.add(nombreArt);
        ContenedorDer.add(precioArt);
        if(Integer.valueOf(A.getPromocion())>0){
            ContenedorDer.add(promocionArt);
            ContenedorDer.add(promocion2Art);
        }
        ContenedorDer.add(descripArt);
        ContenedorDer.add(disponiArt);
        ContenedorDer.add(btnEliminar);
        
        

        JArticuloContenedor.add(ContenedorIzq);
        JArticuloContenedor.add(ContenedorDer);
        
        jPanelCarrit.add(JArticuloContenedor);//Agregamos al carrito
        jPanelCarrit.updateUI();
        
    }
    public void eliminardeCarrito(int indice, int cantidad){
        if(cantidad==carrito.get(indice).getCantidad_Existencias())
            carrito.remove(indice);
        else{
            Articulo art = carrito.get(indice);
            art.setCantidad_Existencias(carrito.get(indice).getCantidad_Existencias()-cantidad);
            carrito.set(indice,art);
        }
        cargarPlantillaCarrito();
    }
    
    public void agregarCarrito(Articulo A, int cantidad){
        //Eliminar las unidades que se agregaron
        Articulo Atemp = new Articulo();
        boolean auxiliar=false;
        Atemp.setId(A.getId());
        Atemp.setPrecio(A.getPrecio2());
        Atemp.setNombre(A.getNombre());
        Atemp.setCantidad_Existencias(cantidad);
        Atemp.setDescripcion(A.getDescripcion());
        Atemp.setPromocion(A.getPromocion());
        Atemp.setImagenes(A.getImagenes());
        Atemp.setInicio_promo(A.getInicio_promo());
        Atemp.setFin_promo(A.getFin_promo());
        for(int i=0;i<carrito.size();i++){
            if(carrito.get(i).getNombre().equals(Atemp.getNombre())){
                Atemp.setCantidad_Existencias(carrito.get(i).getCantidad_Existencias()+Atemp.getCantidad_Existencias());
                if(Atemp.getCantidad_Existencias()>A.getCantidad_Existencias())
                    JOptionPane.showMessageDialog(null,"La tienda no cuenta con suficientes productos, por favor, vuelve a ingresar un número coherente de productos","Error",JOptionPane.INFORMATION_MESSAGE);
                else
                    carrito.set(i,Atemp);
                auxiliar = true;
            }
        }
        if(!auxiliar)
            carrito.add(Atemp);
        cargarPlantillaCarrito();
    }
    @SuppressWarnings("unchecked")
    
    
    
    
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        salir = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        javax.swing.JPanel jPanelCatalogo = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jPanelCatalo = new javax.swing.JPanel();
        jPanelContenedor = new javax.swing.JPanel();
        jPanelCarrito = new javax.swing.JPanel();
        finalizarCompra = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanelCarrit = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabelTotal = new javax.swing.JLabel();
        verTicket = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        salir.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        salir.setText("Salir");
        salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salirActionPerformed(evt);
            }
        });

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setBorder(new javax.swing.border.MatteBorder(null));
        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N

        jPanelCatalogo.setBackground(new java.awt.Color(255, 255, 255));

        jPanelCatalo.setLayout(new javax.swing.BoxLayout(jPanelCatalo, javax.swing.BoxLayout.X_AXIS));

        jPanelContenedor.setAlignmentY(1.5F);
        jPanelContenedor.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanelContenedor.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 2, 10));
        jPanelCatalo.add(jPanelContenedor);

        jScrollPane3.setViewportView(jPanelCatalo);

        javax.swing.GroupLayout jPanelCatalogoLayout = new javax.swing.GroupLayout(jPanelCatalogo);
        jPanelCatalogo.setLayout(jPanelCatalogoLayout);
        jPanelCatalogoLayout.setHorizontalGroup(
            jPanelCatalogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCatalogoLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1662, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(44, Short.MAX_VALUE))
        );
        jPanelCatalogoLayout.setVerticalGroup(
            jPanelCatalogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCatalogoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 760, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("CATALOGO                                                                                         |", jPanelCatalogo);
        jPanelCatalogo.getAccessibleContext().setAccessibleName("");

        jPanelCarrito.setBackground(new java.awt.Color(255, 255, 255));

        finalizarCompra.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        finalizarCompra.setText("Finalizar compra");
        finalizarCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                finalizarCompraActionPerformed(evt);
            }
        });

        jScrollPane2.setViewportView(jPanelCarrit);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 28)); // NOI18N
        jLabel3.setText("Articulos agregados al carrito");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        jLabel4.setText("Total a pagar:  $");
        jLabel4.setToolTipText("");

        jLabelTotal.setFont(new java.awt.Font("Tahoma", 1, 32)); // NOI18N
        jLabelTotal.setText("1000.0");

        verTicket.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        verTicket.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        verTicket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verTicketActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelCarritoLayout = new javax.swing.GroupLayout(jPanelCarrito);
        jPanelCarrito.setLayout(jPanelCarritoLayout);
        jPanelCarritoLayout.setHorizontalGroup(
            jPanelCarritoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCarritoLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanelCarritoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelCarritoLayout.createSequentialGroup()
                        .addGroup(jPanelCarritoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelCarritoLayout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelTotal)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(verTicket, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(69, 69, 69)
                                .addComponent(finalizarCompra)))
                        .addGap(50, 50, 50))
                    .addGroup(jPanelCarritoLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addContainerGap(1336, Short.MAX_VALUE))))
        );
        jPanelCarritoLayout.setVerticalGroup(
            jPanelCarritoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCarritoLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 616, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addGroup(jPanelCarritoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(finalizarCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabelTotal)
                    .addComponent(verTicket, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(51, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("CARRITO DE COMPRAS", jPanelCarrito);

        jLabel1.setFont(new java.awt.Font("Jokerman", 0, 48)); // NOI18N
        jLabel1.setText("FOREVER ESCOM");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel2.setText("Los mejores equipos al mejor precio!.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1744, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addGap(471, 471, 471)
                        .addComponent(salir, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jLabel2))
                    .addComponent(salir, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 881, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("contArticulos");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_salirActionPerformed

    private void finalizarCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_finalizarCompraActionPerformed
        if(carrito.isEmpty()){
            JOptionPane.showMessageDialog(null,"No hay articulos en tu carrito de compras, lo sentimos.","Carrito vacio",JOptionPane.INFORMATION_MESSAGE);
        }else{
            String ticket = clt.comprar(carrito);
            if(!ticket.equals("")){
                System.out.println("Compra Exitosa"+ticket);
                carrito.clear();
            }else
                System.out.println("Ha habido un error al procesar su compra, por favor, intentelo mas tarde");
            cargarProductos2();
            Ticket tk = new Ticket(clt.getRuta_archivos2()+ticket);
            tk.setVisible(true);
            this.verTicket.addItem(ticket);
            //Actualizar el listado de productos pidiendo nuevamente los productos que se tienen en la tienda
        }
    }//GEN-LAST:event_finalizarCompraActionPerformed

    private void verTicketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verTicketActionPerformed
        if(!inicio){
            String cadena = (String)verTicket.getSelectedItem();
            Ticket tk = new Ticket(clt.getRuta_archivos2()+cadena);
            tk.setVisible(true);
        }else
            inicio = false;
    }//GEN-LAST:event_verTicketActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PrincipalCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PrincipalCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PrincipalCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PrincipalCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PrincipalCliente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton finalizarCompra;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabelTotal;
    private javax.swing.JPanel jPanelCarrit;
    private javax.swing.JPanel jPanelCarrito;
    private javax.swing.JPanel jPanelCatalo;
    private javax.swing.JPanel jPanelContenedor;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton salir;
    private javax.swing.JComboBox<String> verTicket;
    // End of variables declaration//GEN-END:variables
}
