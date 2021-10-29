package com.example.fire_checker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import org.w3c.dom.Text;

public class Activity_type_choser extends AppCompatActivity {

    Spinner checker_type_choser_obj;
    Button start_checking_btn;
    public static String chosen_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_choser);

        checker_type_choser_obj=findViewById(R.id.checker_type_choser_field);
        start_checking_btn=findViewById(R.id.start_checking_btn);


        ArrayAdapter<?> types_adapter =ArrayAdapter.createFromResource(this, R.array.types, android.R.layout.simple_spinner_item);
        types_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        checker_type_choser_obj.setAdapter(types_adapter);
        checker_type_choser_obj.setSelection(0);

        checker_type_choser_obj.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String[] type =getResources().getStringArray(R.array.types);
                chosen_type = type[i].toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        start_checking_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Activity_type_choser.this, Activity_qr_scaner.class));
            }
        });
    }
}