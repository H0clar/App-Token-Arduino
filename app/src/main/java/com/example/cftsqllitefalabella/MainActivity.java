package com.example.cftsqllitefalabella;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cftsqllitefalabella.adminDB.AdminDB;

public class MainActivity extends AppCompatActivity {

    private TextView txtbuton;
    private TextView txtbuton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtbuton = findViewById(R.id.textView3);
        txtbuton2 = findViewById(R.id.textViewLogin);

        cargardatos();

        txtbuton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActivityRegistro.class);
                startActivity(intent);
            }
        });

        txtbuton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActivityLogin.class);
                startActivity(intent);
            }
        });
    }

    public void cargardatos() {
        AdminDB admindb = new AdminDB(MainActivity.this);
        SQLiteDatabase database = admindb.getWritableDatabase();
        //admindb.insertarDatosComuna();
        if (database != null) {
            Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show();
        }
    }
}
