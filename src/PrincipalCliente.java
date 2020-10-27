
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JSeparator;

public class PrincipalCliente extends javax.swing.JFrame {
    private Cliente clt;
    private ArrayList<Articulo> articulos;
    private ArrayList<Articulo> carrito;
    public PrincipalCliente() {
        initComponents();
        articulos = new ArrayList<Articulo>();
        carrito = new ArrayList<Articulo>();
        clt = new Cliente();
        cargarProductos();
    }
    public void cargarProductos(){
        articulos = clt.pedirArticulos();
        //Mostrar productos
    }
    public void agregarCarrito(int cantidad, Articulo articulo){
        //Preguntar cuantas unidades va a agregar a su carrito, del articulo seleccionado
        //Eliminar las unidades que se agregaron
        //for para agregar cantidad productos al carrito
        carrito.add(articulo);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        visualizadorProductos = new javax.swing.JScrollPane();
        salir = new javax.swing.JButton();
        verCarrito = new javax.swing.JButton();
        finalizarCompra = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        salir.setText("Salir");
        salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salirActionPerformed(evt);
            }
        });

        verCarrito.setText("Ver Carrito");
        verCarrito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verCarritoActionPerformed(evt);
            }
        });

        finalizarCompra.setText("Finalizar compra");
        finalizarCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                finalizarCompraActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 517, Short.MAX_VALUE)
                        .addComponent(verCarrito)
                        .addGap(18, 18, 18)
                        .addComponent(finalizarCompra)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(salir))
                    .addComponent(visualizadorProductos))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(visualizadorProductos, javax.swing.GroupLayout.DEFAULT_SIZE, 558, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(salir)
                    .addComponent(finalizarCompra)
                    .addComponent(verCarrito))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void verCarritoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verCarritoActionPerformed
        
    }//GEN-LAST:event_verCarritoActionPerformed

    private void finalizarCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_finalizarCompraActionPerformed
        boolean b = clt.comprar(carrito);
        if(b)
            System.out.println("Compra Exitosa");
        else
            System.out.println("Ha habido un error al procesar su compra, por favor, intentelo mas tarde");
        //Actualizar el listado de productos pidiendo nuevamente los productos que se tienen en la tienda
    }//GEN-LAST:event_finalizarCompraActionPerformed

    private void salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salirActionPerformed
        clt.salir();
        System.exit(0);
    }//GEN-LAST:event_salirActionPerformed

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
    private javax.swing.JButton salir;
    private javax.swing.JButton verCarrito;
    private javax.swing.JScrollPane visualizadorProductos;
    // End of variables declaration//GEN-END:variables
}
