package com.example.cftsqllitefalabella;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ActivityTocken extends AppCompatActivity {

    private DatabaseReference codigoReference;
    private ValueEventListener codigoListener;

    private TextView textViewCode;
    private TextView textViewTimer;
    private CountDownTimer countDownTimer;
    private static final long TIMER_DURATION = 20000; // Duración del contador en milisegundos (20 segundos)
    private static final long TIMER_INTERVAL = 1000; // Intervalo de actualización del contador en milisegundos (1 segundo)

    private Long lastCodigo; // Variable para almacenar el último código recibido

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tocken);

        textViewCode = findViewById(R.id.textViewCode);
        textViewTimer = findViewById(R.id.textViewTimer);

        // Get the database reference for "code"
        codigoReference = FirebaseDatabase.getInstance().getReference("code");

        // Set up the value event listener for real-time updates
        codigoListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Long codigo = dataSnapshot.getValue(Long.class);
                    String codigoStr = String.format("%06d", codigo);
                    textViewCode.setText("Los 6 dígitos son: " + codigoStr);

                    // Check if the code has changed
                    if (lastCodigo == null || !codigo.equals(lastCodigo)) {
                        lastCodigo = codigo;
                        restartCountdownTimer();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                textViewCode.setText("Error al obtener el código de la base de datos");
            }
        };

        // Attach the listener to the database reference
        codigoReference.addValueEventListener(codigoListener);

        // Set up and start the countdown timer
        countDownTimer = new CountDownTimer(TIMER_DURATION, TIMER_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Calculate remaining seconds
                long seconds = millisUntilFinished / 1000;

                // Format the remaining time into a readable format
                String timeLeftFormatted = String.format("%02d:%02d", seconds / 60, seconds % 60);

                // Update the timer TextView
                textViewTimer.setText("Tiempo restante: " + timeLeftFormatted);
            }

            @Override
            public void onFinish() {
                // Restart the countdown timer
                restartCountdownTimer();
            }
        };

        countDownTimer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Remove the listener when the activity is destroyed
        if (codigoReference != null && codigoListener != null) {
            codigoReference.removeEventListener(codigoListener);
        }

        // Cancel and release the countdown timer
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }

    private void restartCountdownTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        // Reset the timer and start again
        countDownTimer.start();
    }
}
