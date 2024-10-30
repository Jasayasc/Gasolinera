
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class ModelForm {
    
    public double calcularTotal(double precio, double valorGalon){ 
        return precio*valorGalon;
    }
    
    String url = "jdbc:sqlite:./ventas.db";
    Connection connect;


    public Connection conectar() {
        try {
            Class.forName("org.sqlite.JDBC");
            connect = DriverManager.getConnection(url);
            /*PreparedStatement st = connect.prepareStatement(
                                                            "CREATE TABLE IF NOT EXISTS ventas ("+
                                                                "documento   NUMERIC (10) PRIMARY KEY NOT NULL,"+
                                                                "nombre      TEXT         NOT NULL,"+
                                                                "direccion   TEXT         NOT NULL,"+
                                                                "fecha       TEXT         NOT NULL,"+
                                                                "descripcion TEXT         NOT NULL,"+
                                                                "cantidad    REAL         NOT NULL,"+
                                                                "total       REAL         NOT NULL)");
            st.execute();*/

        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            
            
        }
        return connect;
    }

    public void desconectar() {
        try {
            connect.close();
        } catch (SQLException ex) {
            Logger.getLogger(ModelForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
