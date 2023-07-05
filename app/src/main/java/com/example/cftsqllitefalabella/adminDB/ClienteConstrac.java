package com.example.cftsqllitefalabella.adminDB;

import android.provider.BaseColumns;

public class ClienteConstrac {
    public static abstract class clienteEntry implements BaseColumns{

        public static final String TABLE_NAME = "clientes";
        public static final String NOMBRE = "nombre";
        public static final String APELLIDOS = "apellidos";
        public static final String PASSWORD = "password";
        public static final String CORREO = "correo";
        public static final String COMUNA = "comuna";
        public static final String NUMCUENTA = "ncuenta";


    }
}
