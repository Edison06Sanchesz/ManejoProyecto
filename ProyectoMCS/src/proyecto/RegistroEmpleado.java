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

/**
 *
 * @author edison
 */
public class RegistroEmpleado extends javax.swing.JFrame {

    /**
     * Creates new form RegistroEmpleado
     */
    Integer fila;
    public RegistroEmpleado() {
        initComponents();
        this.bloquearTextos();
        this.bloquearBotones();
        this.cargarTabla();
        fila = jtblEmpleados.getSelectedRow();
        jtblEmpleados.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (jtblEmpleados.getSelectedRow() != -1) {
                    int fila = jtblEmpleados.getSelectedRow();
                    jtxtCedula.setText(jtblEmpleados.getValueAt(fila, 0).toString());
                    jtxtNombre.setText(jtblEmpleados.getValueAt(fila, 1).toString());
                    jtxtApellido.setText(jtblEmpleados.getValueAt(fila, 2).toString());
                    jtxtSalario.setText(jtblEmpleados.getValueAt(fila, 3).toString());
                    jcbxRol.setSelectedItem(jtblEmpleados.getValueAt(fila, 4).toString());
                    desbloquearBotonesModificar();
                    desbloquearTextos();
                }
            }
        });
    }
    public void Cancelar(){
        this.limpiarTexto();
        this.bloquearTextos();
        this.bloquearBotones();
    }
    public void limpiarTexto(){
        jtxtCedula.setText("");
        jtxtNombre.setText("");
        jtxtApellido.setText("");
        jtxtSalario.setText("");
        jcbxRol.setSelectedItem("Encargado");
    }
    
    public void bloquearTextos(){
        jtxtCedula.setEditable(false);
        jtxtNombre.setEditable(false);
        jtxtApellido.setEditable(false);
        jtxtSalario.setEditable(false);
        jcbxRol.setEnabled(false);
    }
    
    public void bloquearBotones(){
        jbtnNuevo.setEnabled(true);
        jbtnGuardar.setEnabled(false);
        jbtnEliminar.setEnabled(false);
        jbtnModificar.setEnabled(false);
        jbtnCancelar.setEnabled(false);
    }
    
    public void desbloquearTextos(){
        jtxtCedula.setEditable(true);
        jtxtNombre.setEditable(true);
        jtxtApellido.setEditable(true);
        jtxtSalario.setEditable(true);
        jcbxRol.setEnabled(true);
    }
    
    public void desbloquearBotones(){
        jbtnNuevo.setEnabled(false);
        jbtnGuardar.setEnabled(true);
        jbtnModificar.setEnabled(false);
        jbtnEliminar.setEnabled(false);
        jbtnCancelar.setEnabled(true);
    }
    
    public void desbloquearBotonesModificar(){
        jbtnNuevo.setEnabled(false);
        jbtnGuardar.setEnabled(false);
        jbtnModificar.setEnabled(true);
        jbtnEliminar.setEnabled(true);
        jbtnCancelar.setEnabled(true);
    }
    
    public void Nuevo(){
        this.desbloquearTextos();
        this.desbloquearBotones();
        this.limpiarTexto();
    }
    public void cargarTabla(){
        try {
            DefaultTableModel modelo = new DefaultTableModel();
            String titulos[] = {"CEDULA","NOMBRE","APELLIDO","SALARIO","ROL"};
            String registros[] = new String[5];
            modelo = new DefaultTableModel(null,titulos);
            jtblEmpleados.setModel(modelo);
            Conexion cn = new Conexion();
            Connection cc = cn.conectar();
            String sql= "select * from empleados";
            Statement psd = cc.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while(rs.next()){
                registros[0] = rs.getString("ced_emp");
                registros[1] = rs.getString("nom_emp");
                registros[2] = rs.getString("ape_emp");
                registros[3] = rs.getString("sal_emp");
                registros[4] = rs.getString("rol_emp");
                modelo.addRow(registros);
            }
            jtblEmpleados.setModel(modelo);
        } catch (SQLException ex) {
            Logger.getLogger(RegistroEmpleado.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void Guardar(){
        if(jtxtCedula.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Ingrese el N. de cedula");
            jtxtCedula.requestFocus();
        }else if (jtxtNombre.getText().isEmpty()){
          JOptionPane.showMessageDialog(null, "Ingrese un Nombre"); 
          jtxtNombre.requestFocus();
        }else if (jtxtApellido.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Ingrese un Apellido");
            jtxtApellido.requestFocus();
        }else if(jtxtSalario.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Ingrese un salario");
            jtxtSalario.requestFocus();
        }else if(jcbxRol.getSelectedItem().toString().equals("")){
            JOptionPane.showMessageDialog(null, "Ingrese un rol para el empleado");
            jcbxRol.requestFocus();
        }else{
            try {
                String ced_emp, nom_emp, ape_emp, rol_emp;
                Integer sal_emp;
                Conexion cn = new Conexion();
                Connection cc = cn.conectar();
                ced_emp = jtxtCedula.getText();
                nom_emp = jtxtNombre.getText();
                ape_emp = jtxtApellido.getText();
                sal_emp = Integer.valueOf(jtxtSalario.getText());
                rol_emp = jcbxRol.getSelectedItem().toString();
                String sql = "insert into empleados (ced_emp,nom_emp,ape_emp,sal_emp,rol_emp,ruta_emp) values"
                        + "(?,?,?,?,?,'No asignado')";
                PreparedStatement psd = cc.prepareStatement(sql);
                psd.setString(1, ced_emp);
                psd.setString(2, nom_emp);
                psd.setString(3, ape_emp);
                psd.setString(4, jtxtSalario.getText());
                psd.setString(5, rol_emp);
                 int n1 = psd.executeUpdate();
                 if (n1 > 0){
                     JOptionPane.showMessageDialog(null, "Se guardo correctamente");
                     this.bloquearBotones();
                     this.bloquearTextos();
                 }
            } catch (SQLException ex) {
                Logger.getLogger(RegistroEmpleado.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        }
    }
    
    public void Modificar(){
        try {
            String ced_emp, nom_emp, ape_emp, sal_emp,rol_emp;
            Conexion cn = new Conexion();
            Connection cc = cn.conectar();
            ced_emp = jtxtCedula.getText();
            nom_emp = jtxtNombre.getText();
            ape_emp = jtxtApellido.getText();
            sal_emp = jtxtSalario.getText();
            rol_emp = jcbxRol.getSelectedItem().toString();
            String sql = "update empleados set nom_emp='" + nom_emp + "',ape_emp='" + ape_emp + "',sal_emp='" + sal_emp
                    + "',rol_emp='" + rol_emp + "'Where ced_emp='" + ced_emp + "'";
            PreparedStatement psd = cc.prepareStatement(sql);
            psd.executeUpdate();
            int n= psd.executeUpdate();
            if (n >0){
                JOptionPane.showMessageDialog(null, "Se actualizo correctamente");
                cargarTabla();
            }
                    } catch (SQLException ex) {
            Logger.getLogger(RegistroEmpleado.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void Eliminar() {
        try {
            String ced_emp;
            mds.Conexion cn = new mds.Conexion();
            Connection cc = cn.conectar();
            ced_emp = jtxtCedula.getText();
            String sql = "delete from empleados where ced_emp= '" + ced_emp + "'";
            PreparedStatement psd = cc.prepareStatement(sql);
            psd.executeUpdate();
            int n = psd.executeUpdate();
            if (n > 0) {
                JOptionPane.showMessageDialog(null, "Se borro correctamente");
                this.cargarTabla();
                this.bloquearBotones();
                this.limpiarTexto();

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se puede borrar");
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jtxtCedula = new javax.swing.JTextField();
        jtxtNombre = new javax.swing.JTextField();
        jtxtApellido = new javax.swing.JTextField();
        jtxtSalario = new javax.swing.JTextField();
        jbtnNuevo = new javax.swing.JButton();
        jbtnGuardar = new javax.swing.JButton();
        jbtnCancelar = new javax.swing.JButton();
        jbtnModificar = new javax.swing.JButton();
        jbtnEliminar = new javax.swing.JButton();
        jbtnAsignar = new javax.swing.JButton();
        jbtnReportePaquetes = new javax.swing.JButton();
        jbtnSalir = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtblEmpleados = new javax.swing.JTable();
        jcbxRol = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Cedula");

        jLabel2.setText("Nombre");

        jLabel3.setText("Apellido");

        jLabel4.setText("Salario");

        jLabel5.setText("Tipo de Empleado");

        jbtnNuevo.setText("Nuevo");
        jbtnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnNuevoActionPerformed(evt);
            }
        });

        jbtnGuardar.setText("Guardar");
        jbtnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnGuardarActionPerformed(evt);
            }
        });

        jbtnCancelar.setText("Cancelar");
        jbtnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnCancelarActionPerformed(evt);
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

        jbtnAsignar.setText("Asignar Paquetes ");

        jbtnReportePaquetes.setText("Reporte Paquetes");
        jbtnReportePaquetes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnReportePaquetesActionPerformed(evt);
            }
        });

        jbtnSalir.setText("Salir");

        jtblEmpleados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jtblEmpleados);

        jcbxRol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Encargado", "Repartidor" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jbtnSalir))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(jcbxRol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jtxtCedula, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                            .addComponent(jtxtApellido, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jtxtSalario, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jtxtNombre, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addGap(55, 55, 55)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jbtnModificar)
                    .addComponent(jbtnEliminar)
                    .addComponent(jbtnCancelar)
                    .addComponent(jbtnGuardar)
                    .addComponent(jbtnNuevo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbtnAsignar, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jbtnReportePaquetes, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtxtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnNuevo))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jtxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jbtnGuardar)
                            .addComponent(jbtnAsignar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jtxtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnCancelar)
                    .addComponent(jbtnReportePaquetes))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jtxtSalario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnModificar))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel5))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jcbxRol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbtnEliminar))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 452, Short.MAX_VALUE)
                        .addComponent(jbtnSalir))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnNuevoActionPerformed
        // TODO add your handling code here:
        Nuevo();
    }//GEN-LAST:event_jbtnNuevoActionPerformed

    private void jbtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnGuardarActionPerformed
        // TODO add your handling code here:
        Guardar();
    }//GEN-LAST:event_jbtnGuardarActionPerformed

    private void jbtnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnCancelarActionPerformed
        // TODO add your handling code here:
        Cancelar();
    }//GEN-LAST:event_jbtnCancelarActionPerformed

    private void jbtnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnModificarActionPerformed
        // TODO add your handling code here:\
        Modificar();
    }//GEN-LAST:event_jbtnModificarActionPerformed

    private void jbtnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnEliminarActionPerformed
        // TODO add your handling code here:
        Eliminar();
    }//GEN-LAST:event_jbtnEliminarActionPerformed

    private void jbtnReportePaquetesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnReportePaquetesActionPerformed
        // TODO add your handling code here:
        ReportePaquete r = new ReportePaquete();
        r.setVisible(true);
    }//GEN-LAST:event_jbtnReportePaquetesActionPerformed

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
            java.util.logging.Logger.getLogger(RegistroEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegistroEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegistroEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegistroEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegistroEmpleado().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbtnAsignar;
    private javax.swing.JButton jbtnCancelar;
    private javax.swing.JButton jbtnEliminar;
    private javax.swing.JButton jbtnGuardar;
    private javax.swing.JButton jbtnModificar;
    private javax.swing.JButton jbtnNuevo;
    private javax.swing.JButton jbtnReportePaquetes;
    private javax.swing.JButton jbtnSalir;
    private javax.swing.JComboBox<String> jcbxRol;
    private javax.swing.JTable jtblEmpleados;
    private javax.swing.JTextField jtxtApellido;
    private javax.swing.JTextField jtxtCedula;
    private javax.swing.JTextField jtxtNombre;
    private javax.swing.JTextField jtxtSalario;
    // End of variables declaration//GEN-END:variables
}
