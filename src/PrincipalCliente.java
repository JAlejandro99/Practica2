
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;

public class PrincipalCliente extends javax.swing.JFrame {
    
    private Cliente clt;
    private ArrayList<Articulo> articulos;
    private ArrayList<Articulo> carrito;
    
    public PrincipalCliente() {
        initComponents();

        jPanelContenedor.setPreferredSize(new Dimension(100, 5000));
                
        articulos = new ArrayList<Articulo>();
        carrito = new ArrayList<Articulo>();
        clt = new Cliente();
        cargarProductos();
        
        cargarPlantillaProductos();
        
    }

    
    public void cargarProductos(){
        
        
        articulos = clt.pedirArticulos();
        //Mostrar productos
    }
    
    public void cargarPlantillaProductos(){
        jPanelContenedor.removeAll();
        for(int i=0; i<articulos.size();i++){
            crearPlantillaProducto(articulos.get(i), 'c');// c: Catalogo
        }        
    }
    
    public void crearPlantillaProducto(Articulo A, char c){
        
        JPanel JArticuloContenedor = new JPanel();
        JArticuloContenedor.setLayout(new GridLayout(1,2));
        JArticuloContenedor.setBackground(Color.red);
        JArticuloContenedor.setPreferredSize(new Dimension(1000, 500));
  
 //CONTENERDOR IZQUIERDO
        JPanel ContenedorIzq = new JPanel();
        ContenedorIzq.setLayout(new BorderLayout());
        
        JPanel BotonesImg = new JPanel();
        BotonesImg.setLayout(new GridLayout(1,2));
        JButton btnS = new JButton("Siguiente");
        JButton btnA = new JButton("Anterior");  
    //LISTENERS BOTONES SIGUIENTE Y ATRAS
        btnS.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    //CARGAR SIGUIENTE IMAGEN
                }                
        });
        btnA.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    //CARGAR IMAGEN ANTERIOR
                }                
        });
        BotonesImg.add(btnA);
        BotonesImg.add(btnS);
        
        ContenedorIzq.add(BotonesImg, BorderLayout.NORTH);
        
        JPanel Img = new JPanel();
        Img.setLayout(new GridLayout(1,1));
        JButton imageicon = new JButton();
        imageicon.setPreferredSize(new Dimension(300,300));
            //imageicon.setBackground(Color.WHITE);
        imageicon.setContentAreaFilled(false); //QUITAR FONDO
        //imageicon.setBorder(null);
        imageicon.setIcon(new ImageIcon("Imagenes/art.png"));
        Img.add(imageicon);
        ContenedorIzq.add(Img, BorderLayout.CENTER);
        
        
 //CONTENEDOR DERECHO
        //JPanel ContenedorDer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel ContenedorDer = new JPanel(new GridLayout(5,1));
        
        JLabel nombreArt = new JLabel("<html><b>"+A.getNombre().toUpperCase()+"</b></html>");
        nombreArt.setFont(new Font("TimesRoman", Font.PLAIN, 45));
        
        JLabel precioArt = new JLabel("Precio: $ "+A.getPrecio());
        precioArt.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        
        JLabel descripArt = new JLabel("Descripcion:  "+A.getPromocion());
        descripArt.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        
        JLabel disponiArt = new JLabel("Disponibles:  "+A.getCantidad_Existencias()+" Articulos.");
        disponiArt.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        
        JButton btnAgregar = new JButton("Agregar al Carrito");
        btnAgregar.setPreferredSize(new Dimension(400,30));
        btnAgregar.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    //AGREGAR AL CARRITO
                }                
        });
        
        ContenedorDer.add(nombreArt);
        ContenedorDer.add(precioArt);
        ContenedorDer.add(descripArt);
        ContenedorDer.add(disponiArt);
        ContenedorDer.add(btnAgregar);
        
        

        JArticuloContenedor.add(ContenedorIzq);
        JArticuloContenedor.add(ContenedorDer);
        
        if(c=='c')
            jPanelContenedor.add(JArticuloContenedor);
        if(c=='q')
            jPanelCarrit.add(JArticuloContenedor);
        
    }
    
    public void agregarCarrito(int cantidad, Articulo articulo){
        //Preguntar cuantas unidades va a agregar a su carrito, del articulo seleccionado
        //Eliminar las unidades que se agregaron
        //for para agregar cantidad productos al carrito
        carrito.add(articulo);
        
        
        crearPlantillaProducto(articulo, 'q');//q: Carrito
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
                    .addComponent(jLabelTotal))
                .addContainerGap(40, Short.MAX_VALUE))
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
        clt.salir();
        System.exit(0);
    }//GEN-LAST:event_salirActionPerformed

    private void finalizarCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_finalizarCompraActionPerformed
        boolean b = clt.comprar(carrito);
        if(b)
        System.out.println("Compra Exitosa");
        else
        System.out.println("Ha habido un error al procesar su compra, por favor, intentelo mas tarde");
        //Actualizar el listado de productos pidiendo nuevamente los productos que se tienen en la tienda
    }//GEN-LAST:event_finalizarCompraActionPerformed

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
    // End of variables declaration//GEN-END:variables
}
