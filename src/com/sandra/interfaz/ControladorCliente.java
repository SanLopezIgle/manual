
package com.sandra.interfaz;

import com.sandra.conexion.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class ControladorCliente implements ActionListener{
    
    private Cliente cliente;
    private Conexion conexion;
    private Formulario formulario;

    public ControladorCliente(Cliente cliente, Conexion conexion, Formulario formulario) {
        this.cliente = cliente;
        this.conexion = conexion;
        this.formulario = formulario;
        this.formulario.bInsertar.addActionListener(this);
        this.formulario.bModificar.addActionListener(this);
        this.formulario.bBorrar.addActionListener(this);
        this.formulario.bBuscar.addActionListener(this);
    }
    
    public void iniciar(){
        formulario.setTitle("Cliente");
        formulario.setLocationRelativeTo(null);
        formulario.lBuscar.setVisible(false);
        cargarTabla();
    }
    
    public void limpiar(){
        formulario.lDNI.setText(null);
        formulario.lNombre.setText(null);
        formulario.lApellidos.setText(null);
        formulario.lTelefono.setText(null);
    }
    
    public void cargarTabla(){
        DefaultTableModel modelo = (DefaultTableModel) formulario.tabla.getModel();
        ArrayList<Cliente> listaClientes = conexion.listaCliente();
        modelo.setRowCount(0);
        for(Cliente elemento : listaClientes){
            Object datos[] = new Object[4];
            datos[0] = elemento.getDni();
            datos[1] = elemento.getNombre();
            datos[2] = elemento.getApellidos();
            datos[3] = elemento.getTelefono();
            
            modelo.addRow(datos);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == formulario.bInsertar){
            try {
                cliente.setDni(formulario.lDNI.getText());
                cliente.setNombre(formulario.lNombre.getText());
                cliente.setApellidos(formulario.lApellidos.getText());
                cliente.setTelefono(Integer.parseInt(formulario.lTelefono.getText()));
                
                if(conexion.insertarCliente(cliente)){
                    JOptionPane.showMessageDialog(null, "Registro guardado");
                    cargarTabla();
                    limpiar();
                }
                else{
                    JOptionPane.showMessageDialog(null, "Error al guardar");
                    limpiar();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Error al guardar");
            }
        }
        
        if(e.getSource() == formulario.bModificar){
            cliente.setDni(formulario.lDNI.getText());
            cliente.setNombre(formulario.lNombre.getText());
            cliente.setApellidos(formulario.lApellidos.getText());
            cliente.setTelefono(Integer.parseInt(formulario.lTelefono.getText()));
            if(conexion.modificarCliente(cliente)){
                JOptionPane.showMessageDialog(null, "Registro modificado");
                cargarTabla();
                limpiar();
            }
            else{
                JOptionPane.showMessageDialog(null, "Error al modificar");
                limpiar();
            }
        }
        if(e.getSource() == formulario.bBorrar){
            cliente.setDni(formulario.lDNI.getText());
            if(conexion.borrarCliente(cliente)){
                JOptionPane.showMessageDialog(null, "Registro eliminado");
                cargarTabla();
                limpiar();
            }
            else{
                JOptionPane.showMessageDialog(null, "Error al eliminar");
                limpiar();
            }
        }
        if(e.getSource() == formulario.bBuscar){
            cliente.setDni(formulario.lDNI.getText());
            if(conexion.buscarCliente(cliente)){
                formulario.lDNI.setText(cliente.getDni());
                formulario.lNombre.setText(cliente.getNombre());
                formulario.lApellidos.setText(cliente.getApellidos());
                formulario.lTelefono.setText(String.valueOf(cliente.getTelefono()));
            }
            else{
                JOptionPane.showMessageDialog(null, "No se encontró el registro");
                limpiar();
            }
        }
        
    }
    
    
    
}
