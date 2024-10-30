package controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import model.ModelForm;
import model.Venta;

public class Crud {

    ModelForm con = new ModelForm();
    Connection cn;

    public void create(ArrayList<Venta> lista) {
        String sql;
        PreparedStatement preparedStatement;
        try {
            cn = con.conectar();
            for (Venta venta : lista) {
                sql = "INSERT INTO ventas ( nombre, direccion,documento,fecha,descripcion,cantidad,total ) VALUES ('" + venta.getNombre() + "', '" + venta.getDireccion()  + "', '" + venta.getDocumento()
                        + "', '" + venta.getFecha() + "', '" + venta.getTipo() + "', '" + venta.getCantidad() + "', '" + venta.getValor() + "');";
                preparedStatement = cn.prepareStatement(sql);
                preparedStatement.execute();
            }
            cn.close();
            JOptionPane.showMessageDialog(null, "Se registro la venta con exito");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    public void exportCsv() {

        ResultSet resul = null;
        
        cn = con.conectar();
        try {
            BufferedWriter outStream= new BufferedWriter(new FileWriter("pagos.csv",true));
            PreparedStatement st = cn.prepareStatement("select * from ventas");
            resul = st.executeQuery();

            while (resul.next()) {
                System.out.println(resul.getString("id") + ", " 
                        + resul.getString("nombre") + ", "
                        + resul.getString("direccion") + ", "
                        + resul.getString("documento") + ", "
                        + resul.getString("fecha") + ", "
                        + resul.getString("descripcion") + ", "
                        + resul.getString("cantidad") + ", "
                        + resul.getString("total") + "\n");
                outStream.write(resul.getString("id") + ", " 
                        + resul.getString("nombre") + ", "
                        + resul.getString("direccion") + ", "
                        + resul.getString("documento") + ", "
                        + resul.getString("fecha") + ", "
                        + resul.getString("descripcion") + ", "
                        + resul.getString("cantidad") + ", "
                        + resul.getString("total") + "\n");
            }
            outStream.close();
            cn.close();
            System.out.println("Reporte generado");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage()+"aqui");
            System.exit(0);
        }
    }
}
