package com.example.cftsqllitefalabella;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cftsqllitefalabella.adminDB.AdminDB;

public class ActivityLogin extends AppCompatActivity {

    EditText txtcorreo, txtpass;
    Button btninicia;
    private TextView txtbtnregistrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtbtnregistrar = findViewById(R.id.textView6);
        txtcorreo = findViewById(R.id.edittxtnombres);
        txtpass = findViewById(R.id.edittxtapellidos);
        btninicia = findViewById(R.id.button3);
        AdminDB admindb= new AdminDB(ActivityLogin.this);

        btninicia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuarioi = txtcorreo.getText().toString();
                String passi = txtpass.getText().toString();
                int respsesion = admindb.ConsultaAccesoCliente(usuarioi, passi);
                if (respsesion < 0){
                    Toast.makeText(ActivityLogin.this, "Verifique Nombre usuario o contraseña", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "Datos no existen en BBDD");
                }else{
                    Log.i(TAG, "Usuario Existe!");
                    Intent intent = new Intent(ActivityLogin.this, MenuActivityCliente.class);
                    intent.putExtra("respsesion", respsesion);
                    startActivity(intent);
                }

            }
        });


        txtbtnregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityLogin.this, ActivityRegistro.class);
                startActivity(intent);
            }
        });
    }
}