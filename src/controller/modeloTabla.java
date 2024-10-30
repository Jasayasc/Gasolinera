package controller;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Venta;

public class modeloTabla {

    DefaultTableModel modelHistoria = new DefaultTableModel();
    ArrayList<Venta> ventas = new ArrayList<>();

    public DefaultTableModel getModelo() {
        String[] cabecera = {"Descripcion", "Cantidad", "Valor"};
        modelHistoria.setColumnIdentifiers(cabecera);
        return modelHistoria;
        
    }

    public DefaultTableModel llenar(ArrayList<Venta> lista) {
        modelHistoria.setRowCount(0);
        this.ventas = lista;
        try {
            for (Venta venta : lista) {
                modelHistoria.addRow(new Object[]{venta.getTipo(), venta.getCantidad(), venta.getValor()});
                
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            System.exit(0);
        }
        return modelHistoria;
    }

}
