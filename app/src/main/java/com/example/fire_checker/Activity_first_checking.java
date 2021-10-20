package com.example.fire_checker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Activity_first_checking extends AppCompatActivity {

    TextView serial_number_obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_checking);

        serial_number_obj=findViewById(R.id.cvartal_serial_number_field);
        serial_number_obj.setText(Activity_qr_scaner.serial_number);

    }
}