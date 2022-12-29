/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto;

import java.sql.Connection;

import java.sql.DriverManager;

/**
 *
 * @author HP
 */
public class Conexion {


    public Connection conectar() {
        Connection connect;
        Class.forName("com.mysql.jdbc.Driver");
        connect = DriverManager.getConnection("jdbc:mysql://localhost/mensajeria", "root", "");
    }


    
    
}
