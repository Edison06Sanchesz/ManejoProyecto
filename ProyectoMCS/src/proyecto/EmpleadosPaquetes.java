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
import mds.ReportePaquetes;
import mds.conexion1;

/**
 *
 * @author HP
 */
public class EmpleadosPaquetes extends javax.swing.JFrame {

    /**
     * Creates new form EmpleadosPaquetes
     */
    private String cedula = "";
    public EmpleadosPaquetes(String cedula) {
        initComponents();
        Integer fila1 = jtblPaquetes.getSelectedRow();
        jtblPaquetes.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (jtblPaquetes.getSelectedRow() != -1) {
                    int fila = jtblPaquetes.getSelectedRow();
                    jtxtIDPaquete.setText(jtblPaquetes.getValueAt(fila, 0).toString());
                }
            }
        });

        this.cedula = cedula;

        try {
            DefaultTableModel modelo = new DefaultTableModel();
            String titulos[] = {"CEDULA", "NOMBRE", "APELLIDO", "CARGO", "RUTA"};
            String registros[] = new String[5];
            modelo = new DefaultTableModel(null, titulos);
            jtblEmpleado.setModel(modelo);
            mds.Conexion cn = new mds.Conexion();
            Connection cc = cn.conectar();
            String sql = "select * from empleados where ced_emp ='" + this.cedula + "'";
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
            jtblEmpleado.setModel(modelo);
        } catch (SQLException ex) {
            Logger.getLogger(mds.Datos.class.getName()).log(Level.SEVERE, null, ex);
        }

        //////////////////////////////////
        try {
            mds.Conexion cn2 = new mds.Conexion();
            Connection cc2 = cn2.conectar();
            String sql1 = "select * from asignaciones where ced_emp ='" + this.cedula + "'";
            Statement psd1 = cc2.createStatement();
            ResultSet rs1 = psd1.executeQuery(sql1);
            if (rs1.next()) {
                int asignacion;
                String estado;
                DefaultTableModel modelo = new DefaultTableModel();
                String titulos[] = {"CODIGO", "Nombre", "Apellido", "ARTICULO", "TIPO", "LOCAL", "DESTINO", "ESTADO"};
                String[] registros = new String[8];
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
                    registros[7] = rs.getString("est_paq");
                    asignacion = Integer.valueOf(rs.getString("asig_paq"));
                    estado = rs.getString("est_paq");
                    if (asignacion == 1) {
                    } else if (estado.equals("No Entregado")) {
                        modelo.addRow(registros);
                    }

                }

                jtblPaquetes.setModel(modelo);
            } else {
                JOptionPane.showMessageDialog(this, "No tiene asignado paquetes");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex);
        }

    }

    
    public void entregarPaquetes(){
        try {
            Conexion cc = new Conexion();
            Connection cn = cc.conectar();
            String sql2 = "UPDATE PAQUETES SET EST_PAQ = 'ENTREGADO' WHERE ID_PAQ='"
                    +jtxtIDPaquete.getText()+"'";
            PreparedStatement psd2 = cn.prepareStatement(sql2);
            psd2.executeUpdate();
            int n2=psd2.executeUpdate();
            if (n2>0) {
                JOptionPane.showMessageDialog(null, "Paquete entregado");
            
            }else{
                JOptionPane.showMessageDialog(null, "Error");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(EmpleadosPaquetes.class.getName()).log(Level.SEVERE, null, ex);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jtblEmpleado = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jtxtIDPaquete = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtblPaquetes = new javax.swing.JTable();
        jbtnReportePaquetes = new javax.swing.JButton();
        jbtnEntregaPaquetes = new javax.swing.JButton();
        jbtnSalir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPane1.setViewportView(jtblEmpleado);

        jLabel1.setText("INFORMACIÓN DEL EMPLEADO");

        jLabel2.setText("ID del Paquete:");

        jLabel3.setText("LISTADO PAQUETES");

        jScrollPane2.setViewportView(jtblPaquetes);

        jbtnReportePaquetes.setText("Reporte de Paquetes");
        jbtnReportePaquetes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnReportePaquetesActionPerformed(evt);
            }
        });

        jbtnEntregaPaquetes.setText("Entregar Paquete");
        jbtnEntregaPaquetes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnEntregaPaquetesActionPerformed(evt);
            }
        });

        jbtnSalir.setText("Salir");
        jbtnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(272, 272, 272)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(213, 213, 213)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jtxtIDPaquete, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(135, 135, 135)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jbtnReportePaquetes)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jbtnEntregaPaquetes, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(315, 315, 315)
                        .addComponent(jbtnSalir)))
                .addContainerGap(173, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jtxtIDPaquete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnReportePaquetes)
                    .addComponent(jbtnEntregaPaquetes))
                .addGap(68, 68, 68)
                .addComponent(jbtnSalir)
                .addContainerGap(143, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnEntregaPaquetesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnEntregaPaquetesActionPerformed
        // TODO add your handling code here:
        entregarPaquetes();
    }//GEN-LAST:event_jbtnEntregaPaquetesActionPerformed

    private void jbtnReportePaquetesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnReportePaquetesActionPerformed
        // TODO add your handling code here:
        ReportePaquetes r = new ReportePaquetes("");
        r.setVisible(true);
    }//GEN-LAST:event_jbtnReportePaquetesActionPerformed

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
            java.util.logging.Logger.getLogger(EmpleadosPaquetes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EmpleadosPaquetes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EmpleadosPaquetes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EmpleadosPaquetes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EmpleadosPaquetes("").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jbtnEntregaPaquetes;
    private javax.swing.JButton jbtnReportePaquetes;
    private javax.swing.JButton jbtnSalir;
    private javax.swing.JTable jtblEmpleado;
    private javax.swing.JTable jtblPaquetes;
    private javax.swing.JTextField jtxtIDPaquete;
    // End of variables declaration//GEN-END:variables
}
