package com.example.cftsqllitefalabella.modelo;

import android.content.ContentValues;

import com.example.cftsqllitefalabella.adminDB.ClienteConstrac;

public class Cliente {
    private String nombre;

    private String apellidos;

    private String password;

    private String correo;

    private String comuna;

    private String ncuenta;


    public Cliente(String nombre, String apellidos, String password, String correo, String comuna, String ncuenta) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.password = password;
        this.correo = correo;
        this.comuna = comuna;
        this.ncuenta = ncuenta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getComuna() {
        return comuna;
    }

    public void setComuna(String comuna) {
        this.comuna = comuna;
    }

    public String getNcuenta() {
        return ncuenta;
    }

    public void setNcuenta(String ncuenta) {
        this.ncuenta = ncuenta;
    }

public ContentValues toContentValue(){
    ContentValues contenedor = new ContentValues();
    contenedor.put(ClienteConstrac.clienteEntry.NOMBRE, nombre);
    contenedor.put(ClienteConstrac.clienteEntry.APELLIDOS, apellidos);
    contenedor.put(ClienteConstrac.clienteEntry.PASSWORD, password);
    contenedor.put(ClienteConstrac.clienteEntry.CORREO, correo);
    contenedor.put(ClienteConstrac.clienteEntry.COMUNA, comuna);
    contenedor.put(ClienteConstrac.clienteEntry.NUMCUENTA, ncuenta);
    return contenedor;
}


}
