package com.example.menu;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class BreakFast extends AppCompatActivity {
    TextView brkText;
    DatabaseReference databaseReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_break_fast);

        // Initialize the TextView
        brkText = findViewById(R.id.brkText);
        FirebaseApp.initializeApp(this);

        // Fetch the current day of the week
//        LocalDate localDate = LocalDate.now();
//        String localDay = localDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());

        // Set the database reference based on the current day
        databaseReference = FirebaseDatabase.getInstance().getReference().child("rutine").child("WeekTwo").child("Tuesday"); // Use the dynamic day

        // Read data from Firebase and update the TextView
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String message = snapshot.getValue(String.class);
                if (message != null) {
                    brkText.setText(message);
                } else {
                    brkText.setText("No data available for " );
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                brkText.setText("Error: " + error.getMessage());
            }
        });

        // Handle insets for a smooth UI experience
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
