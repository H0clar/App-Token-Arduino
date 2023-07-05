package com.example.cftsqllitefalabella;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.cftsqllitefalabella.adminDB.AdminDB;
import com.example.cftsqllitefalabella.adminDB.ClienteConstrac;
import com.example.cftsqllitefalabella.adminDB.CuentaConstrac;

public class MenuActivityCliente extends AppCompatActivity {

    TextView txtnombre, txtsaldo, txtncuenta;
    ImageButton viewTransferencia;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_cliente);

        txtnombre = findViewById(R.id.textnombrecliente);
        txtsaldo = findViewById(R.id.textsaldodisponible);
        txtncuenta = findViewById(R.id.edittxtncuenta);
        viewTransferencia = findViewById(R.id.viewTransferir);

        viewTransferencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivityCliente.this, ActivityTransferencia.class);
                startActivity(intent);
            }
        });

        AdminDB admindb = new AdminDB(MenuActivityCliente.this);
        int respsesion = getIntent().getIntExtra("respsesion", -1);
        Cursor cursor = admindb.obtenerClienteConCuenta(respsesion);
        Log.i(TAG, "" + cursor);

        if (cursor.moveToFirst()) {
            int saldoIndex = cursor.getColumnIndex(CuentaConstrac.cuentaEntry.SALDO);
            String saldo = cursor.getString(saldoIndex);

            int nombreIndex = cursor.getColumnIndex(ClienteConstrac.clienteEntry.NOMBRE);
            String nombre = cursor.getString(nombreIndex);

            int apellidoIndex = cursor.getColumnIndex(ClienteConstrac.clienteEntry.APELLIDOS);
            String apellido = cursor.getString(apellidoIndex);

            int ncuentaIndex = cursor.getColumnIndex(ClienteConstrac.clienteEntry.NUMCUENTA);
            String ncuenta = cursor.getString(ncuentaIndex);

            Log.i(TAG, "Saldo: " + saldo);
            Log.i(TAG, "Nombre: " + nombre);
            Log.i(TAG, "Apellido: " + apellido);
            Log.i(TAG, "Número de cuenta: " + ncuenta);

            txtnombre.setText("Hola, " + nombre + " " + apellido);
            txtsaldo.setText("" + saldo);
            txtncuenta.setText("" + ncuenta);
        }
    }
}
