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
public class AsignarPaquetes extends javax.swing.JFrame {

    /**
     * Creates new form AsignarPaquetes
     */
    public AsignarPaquetes() {
        initComponents();
        cargarTablaRepartidores();
        cargarTablaPaquetes();
        Integer fila = jtblRepartidores.getSelectedRow();
        jtblRepartidores.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (jtblRepartidores.getSelectedRow() != -1) {
                    int fila = jtblRepartidores.getSelectedRow();
                    jtxtCedula.setText(jtblRepartidores.getValueAt(fila, 0).toString());
                }
            }
        });
        
        Integer fila1 = jtblPaquetes.getSelectedRow();
        jtblPaquetes.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (jtblPaquetes.getSelectedRow() != -1) {
                    int fila = jtblPaquetes.getSelectedRow();
                    jtxtPaquete.setText(jtblPaquetes.getValueAt(fila, 0).toString());
                }
            }
        });
        
    }

    public void guardarAsignacionesRutas(){
        try {
            String ced_emp, id_paq, sal_cbx, lle_cbx;
            mds.Conexion cn = new mds.Conexion();
            Connection cc = cn.conectar();
            ced_emp = jtxtCedula.getText();
            sal_cbx = jcbxSalida.getSelectedItem().toString();
            lle_cbx = jcbxLlegada.getSelectedItem().toString();
            String sql = "insert into asignaciones (ced_emp,sal_paq,lle_paq) values (?,?,?)";
            PreparedStatement psd = cc.prepareStatement(sql);
            psd.setString(1, ced_emp);
            psd.setString(2, sal_cbx);
            psd.setString(3, lle_cbx);
            
            int n4 = psd.executeUpdate();
            
            if (n4>0) {
                JOptionPane.showMessageDialog(null, "Se inserto correctamente");
                //
                //
                String sql1 = "update empleados set ruta_emp= '" + jcbxSalida.getSelectedItem().toString() + "-" + jcbxLlegada.getSelectedItem().toString()
                        + "' where ced_emp= '" + jtxtCedula.getText() + "' and rol_emp = 'Repartidor'";
                PreparedStatement psd1 = cc.prepareStatement(sql1);
                psd1.executeUpdate();
                int n2 = psd1.executeUpdate();
                if (n2 > 0) {
                    //
                    //
                } else {
                    JOptionPane.showMessageDialog(null, "Error");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Error");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error Guardar empleados");
        }
        
    }
    
    public void guardarAsignacionesPaquetes() {
        if (jtxtCedula.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese cedula");
            jtxtCedula.requestFocus();
        } else if (jtxtPaquete.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese nombre");
            jtxtPaquete.requestFocus();
        } else if (jcbxSalida.getSelectedItem().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese apellido");
            jcbxSalida.requestFocus();
        } else if (jcbxLlegada.getSelectedItem().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese salario");
            jcbxLlegada.requestFocus();

        } else {
            try {
                String ced_emp, id_paq, sal_cbx, lle_cbx;//usu_emp, con_emp;

                mds.Conexion cn = new mds.Conexion();
                Connection cc = cn.conectar();
                ced_emp = jtxtCedula.getText();
                id_paq = jtxtPaquete.getText();
                sal_cbx = jcbxSalida.getSelectedItem().toString();
                lle_cbx = jcbxLlegada.getSelectedItem().toString();

                String sql = "insert into asignaciones (ced_emp,id_paq) values (?,?)";
                PreparedStatement psd2 = cc.prepareStatement(sql);
                psd2.setString(1, ced_emp);
                psd2.setString(2, id_paq);

                int n1 = psd2.executeUpdate();
                if (n1 > 0) {
                    JOptionPane.showMessageDialog(null, "Se inserto correctamente");
                    this.cargarTablaPaquetes();
                    this.cargarTablaRepartidores();
                } else {
                    JOptionPane.showMessageDialog(null, "Error");
                }
                
                String sql2 = "update paquetes set asig_paq= 1 where id_paq='" + jtxtPaquete.getText()
                        + "' ";
                PreparedStatement psd = cc.prepareStatement(sql2);
                psd.executeUpdate();
                int n3 = psd.executeUpdate();
                if (n3 > 0) {
                    this.cargarTablaPaquetes();

                } else {
                    JOptionPane.showMessageDialog(null, "Error");
                }
                
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al Asignar Paquetes");
            }
        }
    }

    
    public void cargarTablaRepartidores() {
        try {
            DefaultTableModel modelo = new DefaultTableModel();
            String titulos[] = {"CEDULA", "NOMBRE", "APELLIDO", "CARGO", "RUTA"};
            String registros[] = new String[5];
            modelo = new DefaultTableModel(null, titulos);
            jtblRepartidores.setModel(modelo);
            mds.Conexion cn = new mds.Conexion();
            Connection cc = cn.conectar();
            String sql = "select * from empleados where rol_emp = 'Repartidor'";
            Statement psd = cc.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                registros[0] = rs.getString("ced_emp");
                registros[1] = rs.getString("nom_emp");
                registros[2] = rs.getString("ape_emp");
                registros[3] = rs.getString("rol_emp");
                registros[4] = rs.getString("ruta_emp");
                modelo.addRow(registros);
            }
            jtblRepartidores.setModel(modelo);
        } catch (SQLException ex) {
            Logger.getLogger(mds.Datos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void cargarTablaPaquetes() {
        try {
            int asignacion;
            DefaultTableModel modelo = new DefaultTableModel();
            String titulos[] = {"CODIGO", "Nombre", "Apellido", "ARTICULO", "TIPO", "LOCAL", "DESTINO"};
            String[] registros = new String[8];
            modelo = new DefaultTableModel(null, titulos);
            jtblPaquetes.setModel(modelo);
            Conexion cc = new Conexion();
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
                registros[7] = rs.getString("est_paq");
                asignacion = Integer.valueOf(rs.getString("asig_paq"));
                if (asignacion == 0) {
                    modelo.addRow(registros);
                }

            }
            jtblPaquetes.setModel(modelo);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex);
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jtxtCedula = new javax.swing.JTextField();
        jtxtPaquete = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jbtnGuardarPaquetes = new javax.swing.JButton();
        jcbxSalida = new javax.swing.JComboBox<>();
        jcbxLlegada = new javax.swing.JComboBox<>();
        jbtnGuardarRutas = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtblPaquetes = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtblRepartidores = new javax.swing.JTable();
        jbtnSalir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Asignaci??n de paquetes");

        jLabel3.setText("Repartidor: ");

        jLabel4.setText("Paquete: ");

        jtxtCedula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtCedulaActionPerformed(evt);
            }
        });

        jLabel2.setText("Asignaci??n de rutas");

        jbtnGuardarPaquetes.setText("Guardar");
        jbtnGuardarPaquetes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnGuardarPaquetesActionPerformed(evt);
            }
        });

        jcbxSalida.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Salida", "Ambato", "Riobamba", "Quito", "Cuenca" }));

        jcbxLlegada.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Llegada", "Ambato", "Riobamba", "Quito", "Cuenca" }));

        jbtnGuardarRutas.setText("Guardar");
        jbtnGuardarRutas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnGuardarRutasActionPerformed(evt);
            }
        });

        jLabel5.setText("Paquetes");

        jtblPaquetes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jtblPaquetes);

        jLabel6.setText("Repartidores");

        jtblRepartidores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jtblRepartidores);

        jbtnSalir.setText("Salir");
        jbtnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(351, 351, 351)
                        .addComponent(jbtnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(352, 352, 352)
                        .addComponent(jLabel6))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(361, 361, 361)
                        .addComponent(jLabel5))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(163, 163, 163)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel3)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jtxtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                            .addGap(12, 12, 12)
                                            .addComponent(jLabel4)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jbtnGuardarPaquetes)
                                                .addComponent(jtxtPaquete, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(49, 49, 49)
                                        .addComponent(jLabel1)))
                                .addGap(98, 98, 98)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(14, 14, 14)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addComponent(jbtnGuardarRutas))
                                            .addComponent(jcbxSalida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jcbxLlegada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jLabel2)))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(166, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(jcbxSalida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(41, 41, 41))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jcbxLlegada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addComponent(jbtnGuardarRutas))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jtxtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jtxtPaquete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnGuardarPaquetes)))
                .addGap(37, 37, 37)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbtnSalir)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jtxtCedulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtCedulaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtCedulaActionPerformed

    private void jbtnGuardarPaquetesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnGuardarPaquetesActionPerformed
        // TODO add your handling code here:
        guardarAsignacionesPaquetes();
    }//GEN-LAST:event_jbtnGuardarPaquetesActionPerformed

    private void jbtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSalirActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jbtnSalirActionPerformed

    private void jbtnGuardarRutasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnGuardarRutasActionPerformed
        // TODO add your handling code here:
        guardarAsignacionesRutas();
    }//GEN-LAST:event_jbtnGuardarRutasActionPerformed

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
            java.util.logging.Logger.getLogger(AsignarPaquetes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AsignarPaquetes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AsignarPaquetes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AsignarPaquetes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AsignarPaquetes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jbtnGuardarPaquetes;
    private javax.swing.JButton jbtnGuardarRutas;
    private javax.swing.JButton jbtnSalir;
    private javax.swing.JComboBox<String> jcbxLlegada;
    private javax.swing.JComboBox<String> jcbxSalida;
    private javax.swing.JTable jtblPaquetes;
    private javax.swing.JTable jtblRepartidores;
    private javax.swing.JTextField jtxtCedula;
    private javax.swing.JTextField jtxtPaquete;
    // End of variables declaration//GEN-END:variables
}
