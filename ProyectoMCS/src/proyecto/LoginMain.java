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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author HP
 */
public class LoginMain extends javax.swing.JFrame {

    /** Creates new form LoginMain */
    public LoginMain() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jtxtUsuario = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jbtnRegistrarse = new javax.swing.JButton();
        jbtnIngresar = new javax.swing.JButton();
        jtxtContrasena = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Usuario");

        jLabel2.setText("Contraseña");

        jLabel3.setText("BIENVENIDO A SERVIFASTH");

        jbtnRegistrarse.setText("Registrarse");
        jbtnRegistrarse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnRegistrarseActionPerformed(evt);
            }
        });

        jbtnIngresar.setText("Ingresar");
        jbtnIngresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnIngresarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(79, 79, 79))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(170, 170, 170)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addComponent(jbtnRegistrarse)
                        .addGap(81, 81, 81)
                        .addComponent(jbtnIngresar))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(155, 155, 155)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(140, 140, 140)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jtxtUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                            .addComponent(jtxtContrasena))))
                .addContainerGap(94, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addGap(25, 25, 25)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jtxtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jtxtContrasena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnRegistrarse)
                    .addComponent(jbtnIngresar))
                .addContainerGap(62, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnIngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnIngresarActionPerformed
        // TODO add your handling code here:
        String cedula = jtxtUsuario.getText().trim(), contrasena = jtxtContrasena.getText().trim();
        
        if (cedula.equals("") || contrasena.equals("")) {
            JOptionPane.showMessageDialog(this, "Debe ingresar los datos");
        }else{
            try {
                Conexion cn = new Conexion();
                Connection cc = cn.conectar();
                PreparedStatement ps1 = cc.prepareStatement("SELECT E.*, L.* FROM `login` as L,`empleados` as E WHERE  E.ced_emp='" + jtxtUsuario.getText() + "'" + "and E.rol_emp='Encargado' AND L.ced_usr='" + jtxtUsuario.getText() + "'" + " and L.con_usr='" + jtxtContrasena.getText() + "'");
                ResultSet rs1 = ps1.executeQuery();
                Conexion cn2 = new Conexion();
                Connection cc2 = cn2.conectar();
                PreparedStatement ps = cc.prepareStatement("SELECT E.*, L.* FROM `login` as L,`empleados` as E WHERE  E.ced_emp='" + jtxtUsuario.getText() + "'" + "and E.rol_emp='Repartidor' AND L.ced_usr='" + jtxtUsuario.getText() + "'" + " and L.con_usr='" + jtxtContrasena.getText() + "'");
                ResultSet rs = ps.executeQuery();
                
                if (rs1.next()) {
                    Empleados registro = new Empleados();
                    registro.setVisible(true);
                    this.dispose();
                } else if (rs.next()) {
                    EmpleadosPaquetes nuevo = new EmpleadosPaquetes(jtxtUsuario.getText());
                    nuevo.setVisible(true);
                    this.dispose();
                } else {
                     PreparedStatement ps2 = cc2.prepareStatement("select * from admins where ced_admin = '"
                            + jtxtUsuario.getText() + "' and con_admin = '" + jtxtContrasena.getText() + "'");
                     ResultSet rs2 = ps2.executeQuery();
                      if (rs2.next()) {
                          Datos empleados = new Datos();
                          empleados.setVisible(true);
                          this.dispose();
                      }else{
                          JOptionPane.showMessageDialog(this, "Cédula o contraseña incorrectos");
                      }
                }
                
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
            
        }
    }//GEN-LAST:event_jbtnIngresarActionPerformed

    private void jbtnRegistrarseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnRegistrarseActionPerformed
        // TODO add your handling code here:
        Registro r = new Registro();
        r.setVisible(true);
    }//GEN-LAST:event_jbtnRegistrarseActionPerformed

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
            java.util.logging.Logger.getLogger(LoginMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginMain().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JButton jbtnIngresar;
    private javax.swing.JButton jbtnRegistrarse;
    private javax.swing.JTextField jtxtContrasena;
    private javax.swing.JTextField jtxtUsuario;
    // End of variables declaration//GEN-END:variables

}
