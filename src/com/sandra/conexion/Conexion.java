
package com.sandra.conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.*;


public class Conexion {
    
    String url = "file:///C:/Users/Sandra/Documents/NetBeansProjects/manualBasesdedatos/basededatos/mibasededatos.db";
    Connection conexion = null;
    ResultSet resultado = null;
    
    public void conectar(){
        try {
            conexion = DriverManager.getConnection("jdbc:sqlite:" + url);
            if(conexion != null){
                System.out.println("Conectado");
            }
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }
    
    public void cerrar(){
        try {
            conexion.close();
        } catch (SQLException e) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public boolean insertarCliente(Cliente cliente){
        conectar();
        try {
            PreparedStatement st = conexion.prepareStatement("insert into clientes (dni, nombre, apellidos, telefono) values (?,?,?,?)");
            st.setString(1, cliente.getDni().toUpperCase());
            st.setString(2, cliente.getNombre().toUpperCase());
            st.setString(3, cliente.getApellidos().toUpperCase());
            st.setInt(4, cliente.getTelefono());
            
            st.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }finally{
            cerrar();
        }
    }
    
    public boolean modificarCliente(Cliente cliente){
        conectar();
        try {
            PreparedStatement st = conexion.prepareStatement("update clientes set dni=?, nombre=?, apellidos=?, telefono=? where dni=?");
            st.setString(1, cliente.getDni().toUpperCase());
            st.setString(1, cliente.getNombre().toUpperCase());
            st.setString(1, cliente.getApellidos().toUpperCase());
            st.setInt(1, cliente.getTelefono());
            st.setString(5, cliente.getDni());
            
            st.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        } finally{
            cerrar();
        }
    }
    
    public boolean borrarCliente(Cliente cliente){
        conectar();
        try {
            PreparedStatement st = conexion.prepareStatement("delete from clientes where dni=?");
            st.setString(1, cliente.getDni());
            st.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        } finally {
            cerrar();
        }
    }
    
    public boolean buscarCliente(Cliente cliente){
        conectar();
        try {
            PreparedStatement st = conexion.prepareStatement("select * from clientes where dni=?");
            st.setString(1, cliente.getDni().toUpperCase());
            resultado = st.executeQuery();
            if(resultado.next()){
                cliente.setDni(resultado.getString("DNI"));
                cliente.setNombre(resultado.getString("Nombre"));
                cliente.setApellidos(resultado.getString("Apellidos"));
                cliente.setTelefono(resultado.getInt("Telefono"));
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        } finally {
            cerrar();
        }
    }
    
    public ArrayList<Cliente> listaCliente(){
        conectar();
        ArrayList<Cliente> listaClientes = new ArrayList<>();
        try {
            PreparedStatement st = conexion.prepareStatement("select * from clientes");
            resultado = st.executeQuery();
            while(resultado.next()){
                Cliente cliente = new Cliente();
                cliente.setDni(resultado.getString("dni"));
                cliente.setNombre(resultado.getString("nombre"));
                cliente.setApellidos(resultado.getString("apellidos"));
                cliente.setTelefono(resultado.getInt("telefono"));
                listaClientes.add(cliente);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            cerrar();
        }
        return listaClientes;
    }
}
