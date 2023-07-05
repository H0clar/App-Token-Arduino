package com.example.cftsqllitefalabella.adminDB;

import android.provider.BaseColumns;

import androidx.activity.result.contract.ActivityResultContracts;

public class CuentaConstrac {

    public static abstract class cuentaEntry implements BaseColumns{

        public static final String TABLE_NAME = "cuenta";
        public static final String NCUENTA = "ncuenta";
        public static final String SALDO = "saldo";
        public static final String TIPOCUENTA = "tipocuenta";
    }

}
