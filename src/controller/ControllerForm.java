package controller;

import view.ExportPdf;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import model.ModelForm;
import model.Venta;
import view.Carrito;
import view.Form;


public class ControllerForm implements ActionListener {

    Form vista;
    ModelForm model;
    Carrito carrito;
    
    modeloTabla tm = new modeloTabla();

    String nombre = null;
    String direccion = null;
    String nDocumento = null;
    String fecha = null;
    String tipo = null;
    double cantidad = 0;
    double valor = 0;

    ArrayList<Venta> ventas = new ArrayList<>();
    
    public ControllerForm() {
        vista = new Form();
        carrito = new Carrito(vista, true);
        
        
        model = new ModelForm();

        vista.setVisible(true);
        vista.setLocationRelativeTo(null);
        
        vista.getVerCarrito().addActionListener(this);
        vista.getGenerarReporte().addActionListener(this);
        vista.getAñadirAlCarrito().addActionListener(this);
        
        carrito.getComprar().addActionListener(this);
        carrito.getVolver().addActionListener(this);
        
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == carrito.getComprar()){
            ExportPdf exportPdf = new ExportPdf();
            Crud crud = new Crud();

            nombre = vista.getTxtNombre().getText();
            direccion = vista.getTxtDirecc().getText();
            nDocumento = vista.getTxtDocumento().getText();
            fecha = vista.getTxtFecha();
            tipo = vista.getTxtTipo().getSelectedItem().toString();
            cantidad = Double.parseDouble(vista.getTxtCantidad().getText());
            switch (tipo) {
                case "Gasolina":
                    valor = model.calcularTotal(11000, cantidad);
                    vista.getTxtTotal().setText(valor+"");
                case "ACPM":
                    valor = model.calcularTotal(10167, cantidad);
                    vista.getTxtTotal().setText(valor+"");
                case "Gas":
                    valor = model.calcularTotal(8000, cantidad);
                    vista.getTxtTotal().setText(valor+"");
            }
            if (nombre != null && direccion != null && nDocumento != null && fecha != null && tipo != null && cantidad != 0 && valor != 0) {
                exportPdf.createPdf(ventas);
                crud.create(ventas);
            }else{
                JOptionPane.showMessageDialog(null, "Por favor introduzca todos los datos");
            }
            
        }
        
        if(e.getSource() == carrito.getVolver()){
            carrito.setVisible(false);
            //vista.setVisible(true);
        }
        
        if (e.getSource() == vista.getVerCarrito()) {
            carrito.getTable().setModel(tm.getModelo());
            carrito.getTable().setModel(tm.llenar(ventas));
            //vista.setVisible(false);
            carrito.setLocationRelativeTo(null);
            carrito.setVisible(true);
        }
        if(e.getSource() == vista.getGenerarReporte()){
            Crud crud = new Crud();
            crud.exportCsv();
        }
        if(e.getSource() == vista.getAñadirAlCarrito()){
            nombre = vista.getTxtNombre().getText();
            direccion = vista.getTxtDirecc().getText();
            nDocumento = vista.getTxtDocumento().getText();
            fecha = vista.getTxtFecha();
            tipo = vista.getTxtTipo().getSelectedItem().toString();
            cantidad = Double.parseDouble(vista.getTxtCantidad().getText());
            switch (tipo) {
                case "Gasolina":
                    valor = model.calcularTotal(11000, cantidad);
                    //vista.getTxtTotal().setText(valor+"");
                case "ACPM":
                    valor = model.calcularTotal(10167, cantidad);
                    //vista.getTxtTotal().setText(valor+"");
                case "Gas":
                    valor = model.calcularTotal(8000, cantidad);
                    //vista.getTxtTotal().setText(valor+"");
            }
            if (nombre != null && direccion != null && nDocumento != null && fecha != null && tipo != null && cantidad != 0 && valor != 0) {
                ventas.add(new Venta(nombre,direccion,nDocumento, fecha, tipo, cantidad,valor));
            }else{
                JOptionPane.showMessageDialog(null, "Por favor introduzca todos los datos");
            }
        }
    }
    
        
}