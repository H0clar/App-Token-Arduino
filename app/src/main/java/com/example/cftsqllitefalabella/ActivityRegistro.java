package com.example.cftsqllitefalabella;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cftsqllitefalabella.adminDB.AdminDB;
import com.example.cftsqllitefalabella.modelo.Cliente;

import java.util.ArrayList;
import java.util.Random;

public class ActivityRegistro extends AppCompatActivity {

    EditText txtnombre, txtapellidos, txtcorreo, txtpassword;
    Button btnagregar;
    Spinner spincomunas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        txtnombre = findViewById(R.id.edittxtnombres);
        txtapellidos = findViewById(R.id.edittxtapellidos);
        txtcorreo = findViewById(R.id.edittxtcorreo);
        txtpassword = findViewById(R.id.edittxtpass);
        btnagregar = findViewById(R.id.button3);
        spincomunas = findViewById(R.id.SspinnerComunas);

        AdminDB admindb = new AdminDB(ActivityRegistro.this);
        Cursor cursor = admindb.getAllComunas();

        ArrayList<String> listaComunas = new ArrayList<>();

        while (cursor.moveToNext()) {
            @SuppressLint("Range") String nombreComuna = cursor.getString(cursor.getColumnIndex("nombre_comuna"));
            listaComunas.add(nombreComuna);
            Log.i(TAG," "+nombreComuna);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaComunas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spincomunas.setAdapter(adapter);


        btnagregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombreg = txtnombre.getText().toString();
                String apellidosg = txtapellidos.getText().toString();
                String correog = txtcorreo.getText().toString();
                String comunag = spincomunas.getSelectedItem().toString();
                String passg = txtpassword.getText().toString();
                Random random = new Random();
                long limiteInferior = 5000000000000L;
                long limiteSuperior = 6448585745578L;
                long numero = limiteInferior + (long) (random.nextDouble() * (limiteSuperior - limiteInferior));
                String ncuentag = String.valueOf(numero);

                boolean resp = admindb.ConsultaCuentaExistente(correog, ncuentag);

                if (resp) {
                    Toast.makeText(ActivityRegistro.this, "No se puede registrar, el usuario ya existe", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "El usuario existe en la base de datos");
                } else {
                    Toast.makeText(ActivityRegistro.this, "El usuario no existe", Toast.LENGTH_SHORT).show();
                    Cliente ncuelte = new Cliente(nombreg, apellidosg, passg, correog, comunag, ncuentag);
                    long respg = admindb.GuardarCliente(ncuelte);
                    long respcuenta = admindb.CreaCuenta(ncuentag);
                    Toast.makeText(ActivityRegistro.this, "" + respg, Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "IdUsuario nuevo = " + respg);
                    Log.i(TAG, "IdCuenta nueva = " + respcuenta);
                }
            }
        });
    }
}
