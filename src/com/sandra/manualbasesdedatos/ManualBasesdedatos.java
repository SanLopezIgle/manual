
package com.sandra.manualbasesdedatos;

import com.sandra.conexion.Cliente;
import com.sandra.conexion.Conexion;
import com.sandra.interfaz.ControladorCliente;
import com.sandra.interfaz.Formulario;


public class ManualBasesdedatos {

    public static void main(String[] args) {
        Cliente cliente = new Cliente();
        Conexion con = new Conexion();
        Formulario form = new Formulario();
        ControladorCliente cCliente = new ControladorCliente(cliente, con, form);
        
        cCliente.iniciar();
        form.setVisible(true);
    }
    
}
