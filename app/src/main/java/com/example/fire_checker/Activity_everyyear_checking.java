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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class Activity_everyyear_checking extends AppCompatActivity {

    TextView serial_number_obj;
    CheckBox year_hard_problems_obj, year_appearence_problems_obj, year_instruction_problems_obj, year_fuse_problems_obj, year_manometr_problems_obj, year_label_problems_obj, year_weight_problems_obj, year_shlang_problems_obj, year_place_problems_obj, year_bar_problems_obj, year_filters_problems_obj, year_otb_problems_obj;
    EditText year_utechka_problems_obj;
    Integer year_hard_problems_result = 0, year_appearence_problems_result = 0, year_instruction_problems_result = 0, year_fuse_problems_result = 0, year_manometr_problems_result = 0, year_label_problems_result = 0, year_weight_problems_result = 0, year_shlang_problems_result = 0, year_place_problems_result = 0, year_bar_problems_result = 0, year_filters_problems_result = 0, year_otb_problems_result = 0, year_utechka_problems_result;
    Button year_send_btn, year_back_btn;
    public static String year_type_chosen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_everyyear_checking);

        serial_number_obj = findViewById(R.id.year_serial_number_field);
        year_hard_problems_obj = findViewById(R.id.year_hard_problems_appearence_check_box);
        year_appearence_problems_obj = findViewById(R.id.year_problems_appearence_check_box);
        year_instruction_problems_obj = findViewById(R.id.year_instruction_problems_check_box);
        year_fuse_problems_obj = findViewById(R.id.year_fuse_problems_check_box);
        year_manometr_problems_obj = findViewById(R.id.year_manometr_problems_check_box);
        year_label_problems_obj = findViewById(R.id.year_label_problems_check_box);
        year_weight_problems_obj = findViewById(R.id.year_weight_problems_check_box);
        year_shlang_problems_obj = findViewById(R.id.year_shlang_problems_check_box);
        year_place_problems_obj = findViewById(R.id.year_place_problems_check_box);
        year_bar_problems_obj = findViewById(R.id.year_bar_problems_check_box);
        year_filters_problems_obj = findViewById(R.id.year_filters_problems_check_box);
        year_otb_problems_obj = findViewById(R.id.year_otb_problems_check_box);
        year_utechka_problems_obj = findViewById(R.id.year_utechka_problems_field);
        year_send_btn = findViewById(R.id.year_send_btn);
        year_back_btn = findViewById(R.id.year_back_btn);

        year_hard_problems_obj.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                year_hard_problems_result = 1;
            } else {
                year_hard_problems_result = 0;
            }
        });
        year_appearence_problems_obj.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                year_appearence_problems_result = 1;
            } else {
                year_appearence_problems_result = 0;
            }
        });
        year_instruction_problems_obj.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                year_instruction_problems_result = 1;
            } else {
                year_instruction_problems_result = 0;
            }
        });
        year_fuse_problems_obj.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                year_fuse_problems_result = 1;
            } else {
                year_fuse_problems_result = 0;
            }
        });
        year_manometr_problems_obj.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                year_manometr_problems_result = 1;
            } else {
                year_manometr_problems_result = 0;
            }
        });
        year_label_problems_obj.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                year_label_problems_result = 1;
            } else {
                year_label_problems_result = 0;
            }
        });
        year_weight_problems_obj.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                year_weight_problems_result = 1;
            } else {
                year_weight_problems_result = 0;
            }
        });
        year_place_problems_obj.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                year_place_problems_result = 1;
            } else {
                year_place_problems_result = 0;
            }
        });
        year_shlang_problems_obj.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                year_shlang_problems_result = 1;
            } else {
                year_shlang_problems_result = 0;
            }
        });
        year_bar_problems_obj.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                year_bar_problems_result = 1;
            } else {
                year_bar_problems_result = 0;
            }
        });
        year_filters_problems_obj.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                year_filters_problems_result = 1;
            } else {
                year_filters_problems_result = 0;
            }
        });
        year_otb_problems_obj.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                year_otb_problems_result = 1;
            } else {
                year_otb_problems_result = 0;
            }
        });

        serial_number_obj.setText(Activity_qr_scaner.serial_number);

        year_send_btn.setOnClickListener(v -> {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Dialog dialog_review_year = new Dialog(Activity_everyyear_checking.this);

                    dialog_review_year.setContentView(R.layout.dialog_service_and_review);
                    Spinner dialog_review_obj = (Spinner) dialog_review_year.findViewById(R.id.dialog_service_and_review_type_choser_field);
                    ArrayAdapter<?> types_adapter = ArrayAdapter.createFromResource(Activity_everyyear_checking.this, R.array.types_service_review, android.R.layout.simple_spinner_item);
                    Button dialog_review_send_btn = (Button) dialog_review_year.findViewById(R.id.dialog_service_and_review_type_choser_btn);
                    dialog_review_year.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    types_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dialog_review_obj.setAdapter(types_adapter);
                    dialog_review_obj.setSelection(0);
                    dialog_review_year.setCancelable(false);

                    dialog_review_send_btn.setOnClickListener(v -> {
                        dialog_review_year.dismiss();
                        Activity_qr_scaner.serial_number = "";
                        Activity_type_choser.chosen_type = "";
                        startActivity(new Intent(Activity_everyyear_checking.this, Activity_type_choser.class));
                    });

                    dialog_review_obj.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            String[] type = getResources().getStringArray(R.array.types);
                            year_type_chosen = type[i].toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                    dialog_review_year.show();
                }
            });
        });

        year_back_btn.setOnClickListener(v -> {
            Activity_qr_scaner.serial_number = "";
            Activity_type_choser.chosen_type = "";
            startActivity(new Intent(Activity_everyyear_checking.this, Activity_type_choser.class));
        });
    }
}