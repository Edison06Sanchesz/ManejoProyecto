/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import mds.conexion1;

/**
 *
 * @author HP
 */
public class Empleados extends javax.swing.JFrame {

    Integer fila;

    /**
     * Creates new form Empleados
     */
    public Empleados() {
        initComponents();
        cargarTabla();
        jtxtID.setEditable(false);
        jtblPaquetes.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (jtblPaquetes.getSelectedRow() != -1) {
                    fila = jtblPaquetes.getSelectedRow();
                    jtxtID.setText(jtblPaquetes.getValueAt(fila, 0).toString());
                    jtxtNombre.setText(jtblPaquetes.getValueAt(fila, 1).toString());
                    jtxtApellido.setText(jtblPaquetes.getValueAt(fila, 2).toString());
                    jtxtArticulo.setText(jtblPaquetes.getValueAt(fila, 3).toString());
                    jcbxTipo.setSelectedItem(jtblPaquetes.getValueAt(fila, 4).toString());
                    jcbxLocal.setSelectedItem(jtblPaquetes.getValueAt(fila, 5).toString());
                    jcbxDestino.setSelectedItem(jtblPaquetes.getValueAt(fila, 6).toString());
                }
            }
        });
    }

    public void agregar() {
        try {
            if (jtxtNombre.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese el Nombre ");
                jtxtNombre.requestFocus();
            } else if (jtxtApellido.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese el Apellido ");
                jtxtApellido.requestFocus();
            } else if (jtxtArticulo.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese un Articulo ");
                jtxtArticulo.requestFocus();
            } else if (jcbxLocal.getSelectedItem().equals("")) {
                JOptionPane.showMessageDialog(this, "Ingrese una Direccion ");
                jcbxLocal.requestFocus();
            } else if (jcbxDestino.getSelectedItem().equals("")) {
                JOptionPane.showMessageDialog(this, "Ingrese una Direccion ");
                jcbxDestino.requestFocus();
            } else if (jcbxTipo.getSelectedItem().toString().equals("tipo")) {
                JOptionPane.showMessageDialog(this, "Ingrese el Tipo");
                jcbxTipo.requestFocus();
            }

            conexion1 cc = new conexion1();
            Connection cn = cc.conectar();

            String sql = "insert into paquetes (nom_des_paq,ape_des_paq,art_paq,tipo_paq,dir_paq,dir_lleg_paq,est_paq,asig_paq) values (?,?,?,?,?,?,'No Entregado',0)";
            java.sql.PreparedStatement psd = cn.prepareStatement(sql);

            psd.setString(1, jtxtNombre.getText());
            psd.setString(2, jtxtApellido.getText());
            psd.setString(3, jtxtArticulo.getText());
            psd.setString(4, jcbxTipo.getSelectedItem().toString());
            psd.setString(5, jcbxLocal.getSelectedItem().toString());
            psd.setString(6, jcbxDestino.getSelectedItem().toString());
            int n = psd.executeUpdate();
            if (n > 0) {
                JOptionPane.showMessageDialog(this, "Se inserto Correctamente");
                cargarTabla();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "No se realizo la transaccion, error !!");;
        }
    }

    public void cargarTabla() {
        try {
            DefaultTableModel modelo = new DefaultTableModel();
            String titulos[] = {"CODIGO", "Nombre", "Apellido", "ARTICULO", "TIPO", "LOCAL", "DESTINO"};
            String[] registros = new String[7];
            modelo = new DefaultTableModel(null, titulos);
            jtblPaquetes.setModel(modelo);
            conexion1 cc = new conexion1();
            Connection cn = cc.conectar();
            String sql = "";
            sql = "select * from paquetes ";
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                registros[0] = rs.getString("id_paq");
                registros[1] = rs.getString("nom_des_paq");
                registros[2] = rs.getString("ape_des_paq");
                registros[3] = rs.getString("art_paq");
                registros[4] = rs.getString("tipo_paq");
                registros[5] = rs.getString("dir_paq");
                registros[6] = rs.getString("dir_lleg_paq");

                modelo.addRow(registros);
            }
            jtblPaquetes.setModel(modelo);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex);
        }
    }

    public void eliminarPaquete() {
        try {
            String id_paq;
            conexion1 cc = new conexion1();
            Connection cn = cc.conectar();
            id_paq = jtxtID.getText();
            if (JOptionPane.showConfirmDialog(rootPane, "Desea Eliminar ") == 0) {
                String sql = "DELETE FROM paquetes where id_paq='" + id_paq + "'";
                java.sql.PreparedStatement psd = cn.prepareStatement(sql);
                int n = psd.executeUpdate();
                if (n > 0) {
                    JOptionPane.showMessageDialog(this, "Eliminado satisfactoriamente");
                    cargarTabla();
                } else {
                    System.out.println("No se ha eliminado");
                }

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void actualizarDatos() {
        try {
            String id_paq, nom_des_paq, ape_des_paq, art_paq, tipo_paq, dir_paq;
            Integer sal_emp;
            conexion1 cn = new conexion1();
            Connection cc = cn.conectar();
            id_paq = jtxtID.getText();
            nom_des_paq = jtxtNombre.getText();
            ape_des_paq = jtxtApellido.getText();
            art_paq = jtxtArticulo.getText();
            tipo_paq = jcbxTipo.getSelectedItem().toString();
            dir_paq = jcbxLocal.getSelectedItem().toString();
            
            String sql = "";
            sql = "update paquetes set nom_des_paq='" + nom_des_paq + "',ape_des_paq='" + ape_des_paq + "',art_paq='" + art_paq
                    + "',tipo_paq='" + tipo_paq + "',dir_paq='" + dir_paq + "'Where id_paq='" + id_paq + "'";
            PreparedStatement psd = cc.prepareStatement(sql);
            psd.executeUpdate();
            int n = psd.executeUpdate();
            if (n > 0) {
                JOptionPane.showMessageDialog(null, "Se actualizo correctamente");
                cargarTabla();

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se puede realizar la transacion");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel5 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jtxtID = new javax.swing.JTextField();
        jtxtNombre = new javax.swing.JTextField();
        jtxtApellido = new javax.swing.JTextField();
        jtxtArticulo = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jcbxTipo = new javax.swing.JComboBox<>();
        jcbxLocal = new javax.swing.JComboBox<>();
        jcbxDestino = new javax.swing.JComboBox<>();
        jbtnGuardar = new javax.swing.JButton();
        jbtnModificar = new javax.swing.JButton();
        jbtnEliminar = new javax.swing.JButton();
        jbtnSalir = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtblPaquetes = new javax.swing.JTable();

        jLabel5.setText("jLabel5");

        jTextField5.setText("jTextField5");

        jScrollPane1.setViewportView(jEditorPane1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("ID paquete");

        jLabel2.setText("Nombre Destinatario");

        jLabel3.setText("Apellido Destinatario");

        jLabel4.setText("Art??culo");

        jtxtID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtIDActionPerformed(evt);
            }
        });

        jtxtApellido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtApellidoActionPerformed(evt);
            }
        });

        jLabel6.setText("Tipo de Paquete");

        jLabel7.setText("Direcci??n del Remitente");

        jLabel8.setText("Direcci??n del Destinatario");

        jcbxTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TIPO", "LIVIANO", "PESADO", "FRAGIL", "ELECTRONICO" }));

        jcbxLocal.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "LOCAL", "Ambato", "Riobamba", "Quito", "Cuenca" }));

        jcbxDestino.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DESTINO", "Salida", "Ambato", "Riobamba", "Quito", "Cuenca" }));

        jbtnGuardar.setText("Guardar");
        jbtnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnGuardarActionPerformed(evt);
            }
        });

        jbtnModificar.setText("Modificar");
        jbtnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnModificarActionPerformed(evt);
            }
        });

        jbtnEliminar.setText("Eliminar");
        jbtnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnEliminarActionPerformed(evt);
            }
        });

        jbtnSalir.setText("Salir");
        jbtnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSalirActionPerformed(evt);
            }
        });

        jScrollPane2.setViewportView(jtblPaquetes);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(36, 36, 36)
                        .addComponent(jcbxDestino, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                                .addComponent(jcbxTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(435, 435, 435))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(24, 24, 24)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jtxtApellido, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                                            .addComponent(jtxtNombre)
                                            .addComponent(jtxtID)
                                            .addComponent(jtxtArticulo, javax.swing.GroupLayout.Alignment.TRAILING))
                                        .addGap(106, 106, 106)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jbtnModificar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jbtnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(jbtnSalir)
                                                .addComponent(jbtnEliminar))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(47, 47, 47)
                                        .addComponent(jcbxLocal, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
            .addGroup(layout.createSequentialGroup()
                .addGap(124, 124, 124)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jtxtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jtxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jtxtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jtxtArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(41, 41, 41)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jcbxTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jcbxLocal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addComponent(jbtnGuardar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnModificar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnEliminar)
                        .addGap(18, 18, 18)
                        .addComponent(jbtnSalir)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jcbxDestino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(132, 132, 132))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jtxtIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtIDActionPerformed

    private void jtxtApellidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtApellidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtApellidoActionPerformed

    private void jbtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnGuardarActionPerformed
        // TODO add your handling code here:
        agregar();
    }//GEN-LAST:event_jbtnGuardarActionPerformed

    private void jbtnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnModificarActionPerformed
        // TODO add your handling code here:
        actualizarDatos();
    }//GEN-LAST:event_jbtnModificarActionPerformed

    private void jbtnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnEliminarActionPerformed
        // TODO add your handling code here:
        eliminarPaquete();
    }//GEN-LAST:event_jbtnEliminarActionPerformed

    private void jbtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSalirActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jbtnSalirActionPerformed

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
            java.util.logging.Logger.getLogger(Empleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Empleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Empleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Empleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Empleados().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JButton jbtnEliminar;
    private javax.swing.JButton jbtnGuardar;
    private javax.swing.JButton jbtnModificar;
    private javax.swing.JButton jbtnSalir;
    private javax.swing.JComboBox<String> jcbxDestino;
    private javax.swing.JComboBox<String> jcbxLocal;
    private javax.swing.JComboBox<String> jcbxTipo;
    private javax.swing.JTable jtblPaquetes;
    private javax.swing.JTextField jtxtApellido;
    private javax.swing.JTextField jtxtArticulo;
    private javax.swing.JTextField jtxtID;
    private javax.swing.JTextField jtxtNombre;
    // End of variables declaration//GEN-END:variables
}
