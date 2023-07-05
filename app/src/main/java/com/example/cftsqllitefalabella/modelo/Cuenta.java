package com.example.cftsqllitefalabella.modelo;

import android.content.ContentValues;

import com.example.cftsqllitefalabella.adminDB.CuentaConstrac;

public class Cuenta {

    private String ncuenta;

    private Float saldo;

    private String tipocuenta;

    public Cuenta(String ncuenta, Float saldo, String tipocuenta) {
        this.ncuenta = ncuenta;
        this.saldo = saldo;
        this.tipocuenta = tipocuenta;
    }

    public String getNcuenta() {
        return ncuenta;
    }

    public void setNcuenta(String ncuenta) {
        this.ncuenta = ncuenta;
    }

    public Float getSaldo() {
        return saldo;
    }

    public void setSaldo(Float saldo) {
        this.saldo = saldo;
    }

    public String getTipocuenta() {
        return tipocuenta;
    }

    public void setTipocuenta(String tipocuenta) {
        this.tipocuenta = tipocuenta;
    }


    public ContentValues toContentValue () {
        ContentValues contenedor = new ContentValues();
        contenedor.put(CuentaConstrac.cuentaEntry.NCUENTA,ncuenta);
        contenedor.put (CuentaConstrac.cuentaEntry.SALDO, saldo);
        contenedor.put (CuentaConstrac.cuentaEntry.TIPOCUENTA, tipocuenta);
        return contenedor;
    }


}
