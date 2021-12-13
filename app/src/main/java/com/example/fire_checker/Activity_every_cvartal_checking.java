package com.example.fire_checker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

public class Activity_every_cvartal_checking extends AppCompatActivity {

    TextView serial_number_obj;
    CheckBox cvartal_hard_problems_obj, cvartal_appearence_problems_obj, cvartal_instruction_problems_obj, cvartal_fuse_problems_obj, cvartal_manometr_problems_obj, cvartal_label_problems_obj, cvartal_weight_problems_obj, cvartal_shlang_problems_obj, cvartal_place_problems_obj, cvartal_bar_problems_obj;
    Integer cvartal_hard_problems_result = 0, cvartal_appearence_problems_result = 0, cvartal_instruction_problems_result = 0, cvartal_fuse_problems_result = 0, cvartal_manometr_problems_result = 0, cvartal_label_problems_result = 0, cvartal_weight_problems_result = 0, cvartal_shlang_problems_result = 0, cvartal_place_problems_result = 0, cvartal_bar_problems_result = 0;
    Button cvartal_send_btn, cvartal_back_btn;
    public static String cvartal_type_chosen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_every_cvartal_checking);

        serial_number_obj = findViewById(R.id.cvartal_serial_number_field);
        cvartal_hard_problems_obj = findViewById(R.id.cvartal_hard_problems_appearence_check_box);
        cvartal_appearence_problems_obj = findViewById(R.id.cvartal_problems_appearence_check_box);
        cvartal_instruction_problems_obj = findViewById(R.id.cvartal_instruction_problems_check_box);
        cvartal_fuse_problems_obj = findViewById(R.id.cvartal_fuse_problems_check_box);
        cvartal_manometr_problems_obj = findViewById(R.id.cvartal_manometr_problems_check_box);
        cvartal_label_problems_obj = findViewById(R.id.cvartal_label_problems_check_box);
        cvartal_weight_problems_obj = findViewById(R.id.cvartal_weight_problems_check_box);
        cvartal_shlang_problems_obj = findViewById(R.id.cvartal_shlang_problems_check_box);
        cvartal_place_problems_obj = findViewById(R.id.cvartal_place_problems_check_box);
        cvartal_bar_problems_obj = findViewById(R.id.cvartal_bar_problems_check_box);
        cvartal_send_btn = findViewById(R.id.cvartal_send_btn);
        cvartal_back_btn = findViewById(R.id.cvartal_back_btn);


        serial_number_obj.setText(Activity_qr_scaner.serial_number);

        cvartal_hard_problems_obj.setOnCheckedChangeListener((buttonView, isChecked) -> {
            cvartal_hard_problems_result = (isChecked)? 1 : 0;
        });
        cvartal_appearence_problems_obj.setOnCheckedChangeListener((buttonView, isChecked) -> {
            cvartal_appearence_problems_result = (isChecked)? 1 : 0;
        });
        cvartal_instruction_problems_obj.setOnCheckedChangeListener((buttonView, isChecked) -> {
            cvartal_instruction_problems_result = (isChecked)? 1 : 0;
        });
        cvartal_fuse_problems_obj.setOnCheckedChangeListener((buttonView, isChecked) -> {
            cvartal_fuse_problems_result = (isChecked)? 1 : 0;
        });
        cvartal_manometr_problems_obj.setOnCheckedChangeListener((buttonView, isChecked) -> {
            cvartal_manometr_problems_result = (isChecked)? 1 : 0;
        });
        cvartal_label_problems_obj.setOnCheckedChangeListener((buttonView, isChecked) -> {
           cvartal_label_problems_result = (isChecked)? 1 : 0;
        });
        cvartal_weight_problems_obj.setOnCheckedChangeListener((buttonView, isChecked) -> {
            cvartal_weight_problems_result = (isChecked)? 1 : 0;
        });
        cvartal_place_problems_obj.setOnCheckedChangeListener((buttonView, isChecked) -> {
            cvartal_place_problems_result = (isChecked)? 1 : 0;
        });
        cvartal_shlang_problems_obj.setOnCheckedChangeListener((buttonView, isChecked) -> {
            cvartal_shlang_problems_result = (isChecked)? 1 : 0;
        });
        cvartal_bar_problems_obj.setOnCheckedChangeListener((buttonView, isChecked) -> {
            cvartal_bar_problems_result = (isChecked)? 1 : 0;
        });

        cvartal_send_btn.setOnClickListener(v -> {
            dialog_service_and_review_starter();
        });

        cvartal_back_btn.setOnClickListener(v -> {
            Activity_qr_scaner.serial_number = "";
            Activity_type_choser.chosen_type = "";
            startActivity(new Intent(Activity_every_cvartal_checking.this, Activity_type_choser.class));
        });


    }

    protected void dialog_service_and_review_starter(){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Dialog dialog_review_cvartal = new Dialog(Activity_every_cvartal_checking.this);
                dialog_review_cvartal.setContentView(R.layout.dialog_service_and_review);
                Spinner dialog_review_obj = (Spinner) dialog_review_cvartal.findViewById(R.id.dialog_service_and_review_type_choser_field);
                ArrayAdapter<?> types_adapter = ArrayAdapter.createFromResource(Activity_every_cvartal_checking.this, R.array.types_service_review, android.R.layout.simple_spinner_item);
                Button dialog_review_send_btn = (Button) dialog_review_cvartal.findViewById(R.id.dialog_service_and_review_type_choser_btn);
                dialog_review_cvartal.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                types_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dialog_review_obj.setAdapter(types_adapter);
                dialog_review_obj.setSelection(0);
                dialog_review_cvartal.setCancelable(false);

                dialog_review_send_btn.setOnClickListener(v -> {
                    dialog_review_cvartal.dismiss();
                    Activity_qr_scaner.serial_number = "";
                    Activity_type_choser.chosen_type = "";
                    startActivity(new Intent(Activity_every_cvartal_checking.this, Activity_type_choser.class));
                });

                dialog_review_obj.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String[] type = getResources().getStringArray(R.array.types_checking);

                        cvartal_type_chosen = type[i].toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                dialog_review_cvartal.show();
            }
        });
    }
}