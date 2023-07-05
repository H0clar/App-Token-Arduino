package com.example.cftsqllitefalabella;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ActivityTransferencia extends AppCompatActivity {

    private EditText rutEditText;
    private EditText nombresEditText;
    private EditText apellidosEditText;
    private EditText correoEditText;
    private Spinner bancoDestinoSpinner;
    private Spinner tipoCuentaSpinner;
    private EditText montoTransferenciaEditText;
    private Button transferirButton;

    private DatabaseReference databaseReference;
    private ValueEventListener valueEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transferencia);

        rutEditText = findViewById(R.id.edittxtRut);
        nombresEditText = findViewById(R.id.edittxtnombres);
        apellidosEditText = findViewById(R.id.edittxtapellidos);
        correoEditText = findViewById(R.id.edittxtcorreo);
        bancoDestinoSpinner = findViewById(R.id.spinnerBancoDestino);
        tipoCuentaSpinner = findViewById(R.id.spinnerTipoCuenta);
        montoTransferenciaEditText = findViewById(R.id.edittxtMontoTransferencia);
        transferirButton = findViewById(R.id.button3);

        transferirButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realizarTransferencia();
            }
        });

        // referencia del codigo rules db
        databaseReference = FirebaseDatabase.getInstance().getReference().child("code");
    }

    private void realizarTransferencia() {
        // obtener los valores ingresados por el usuario
        String rut = rutEditText.getText().toString();
        String nombres = nombresEditText.getText().toString();
        String apellidos = apellidosEditText.getText().toString();
        String correo = correoEditText.getText().toString();
        String bancoDestino = bancoDestinoSpinner.getSelectedItem().toString();
        String tipoCuenta = tipoCuentaSpinner.getSelectedItem().toString();
        String montoTransferencia = montoTransferenciaEditText.getText().toString();

        // validacion de campos vacios
        if (rut.isEmpty() || nombres.isEmpty() || apellidos.isEmpty() || correo.isEmpty() || bancoDestino.isEmpty() || tipoCuenta.isEmpty() || montoTransferencia.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
        } else {
            // Obtener el código desde la base de datos Firebase
            valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Long codigo = dataSnapshot.getValue(Long.class);
                        String codigoStr = String.valueOf(codigo);

                        // Abrir la ventana ActivityTocken.java y mostrar el codigo
                        Intent intent = new Intent(ActivityTransferencia.this, ActivityTocken.class);
                        intent.putExtra("codigo", codigoStr);
                        startActivity(intent);



                        // Restablecer los campos despues de la transferencia exitosa
                        rutEditText.setText("");
                        nombresEditText.setText("");
                        apellidosEditText.setText("");
                        correoEditText.setText("");
                        bancoDestinoSpinner.setSelection(0);
                        tipoCuentaSpinner.setSelection(0);
                        montoTransferenciaEditText.setText("");
                    } else {
                        Toast.makeText(ActivityTransferencia.this, "No se encontróningún código de transferencia", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(ActivityTransferencia.this, "Error al obtener el código de transferencia", Toast.LENGTH_SHORT).show();
                }
            };

            // Agregar el listener a la referencia de la base de datos
            databaseReference.addListenerForSingleValueEvent(valueEventListener);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Eliminar el listener cuando la actividad se destruye
        if (valueEventListener != null) {
            databaseReference.removeEventListener(valueEventListener);
        }
    }
}
