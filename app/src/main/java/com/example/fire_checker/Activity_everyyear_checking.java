package com.example.fire_checker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class Activity_everyyear_checking extends AppCompatActivity {

    TextView serial_number_obj;
    CheckBox year_hard_problems_obj, year_appearence_problems_obj, year_instruction_problems_obj, year_fuse_problems_obj, year_manometr_problems_obj, year_label_problems_obj, year_weight_problems_obj, year_shlang_problems_obj, year_place_problems_obj, year_bar_problems_obj, year_filters_problems_obj, year_otb_problems_obj;
    EditText  year_utechka_problems_obj;
    Integer year_hard_problems_result=0,year_appearence_problems_result=0,year_instruction_problems_result=0, year_fuse_problems_result=0, year_manometr_problems_result=0, year_label_problems_result=0, year_weight_problems_result=0, year_shlang_problems_result=0, year_place_problems_result=0, year_bar_problems_result=0, year_filters_problems_result=0, year_otb_problems_result=0, year_utechka_problems_result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_everyyear_checking);

        serial_number_obj=findViewById(R.id.year_serial_number_field);
        year_hard_problems_obj=findViewById(R.id.year_hard_problems_appearence_check_box);
        year_appearence_problems_obj=findViewById(R.id.year_problems_appearence_check_box);
        year_instruction_problems_obj=findViewById(R.id.year_instruction_problems_check_box);
        year_fuse_problems_obj=findViewById(R.id.year_fuse_problems_check_box);
        year_manometr_problems_obj=findViewById(R.id.year_manometr_problems_check_box);
        year_label_problems_obj=findViewById(R.id.year_label_problems_check_box);
        year_weight_problems_obj=findViewById(R.id.year_weight_problems_check_box);
        year_shlang_problems_obj=findViewById(R.id.year_shlang_problems_check_box);
        year_place_problems_obj=findViewById(R.id.year_place_problems_check_box);
        year_bar_problems_obj=findViewById(R.id.year_bar_problems_check_box);
        year_filters_problems_obj=findViewById(R.id.year_filters_problems_check_box);
        year_otb_problems_obj=findViewById(R.id.year_otb_problems_check_box);
        year_utechka_problems_obj=findViewById(R.id.year_utechka_problems_field);

        year_hard_problems_obj.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                year_hard_problems_result=1;
            }
            else {
                year_hard_problems_result=0;
            }
        });
        year_appearence_problems_obj.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                year_appearence_problems_result=1;
            }
            else {
                year_appearence_problems_result=0;
            }
        });
        year_instruction_problems_obj.setOnCheckedChangeListener((buttonView, isChecked) ->{
            if (isChecked){
                year_instruction_problems_result=1;
            }
            else{
                year_instruction_problems_result=0;
            }
        } );
        year_fuse_problems_obj.setOnCheckedChangeListener((buttonView, isChecked) ->{
            if (isChecked){
                year_fuse_problems_result=1;
            }
            else{
                year_fuse_problems_result=0;
            }
        } );
        year_manometr_problems_obj.setOnCheckedChangeListener((buttonView, isChecked) ->{
            if (isChecked){
                year_manometr_problems_result=1;
            }
            else{
                year_manometr_problems_result=0;
            }
        } );
        year_label_problems_obj.setOnCheckedChangeListener((buttonView, isChecked) ->{
            if (isChecked){
                year_label_problems_result=1;
            }
            else{
                year_label_problems_result=0;
            }
        } );
        year_weight_problems_obj.setOnCheckedChangeListener((buttonView, isChecked) ->{
            if (isChecked){
                year_weight_problems_result=1;
            }
            else{
                year_weight_problems_result=0;
            }
        } );
        year_place_problems_obj.setOnCheckedChangeListener((buttonView, isChecked) ->{
            if (isChecked){
                year_place_problems_result=1;
            }
            else{
                year_place_problems_result=0;
            }
        } );
        year_shlang_problems_obj.setOnCheckedChangeListener((buttonView, isChecked) ->{
            if (isChecked){
                year_shlang_problems_result=1;
            }
            else{
                year_shlang_problems_result=0;
            }
        } );
        year_bar_problems_obj.setOnCheckedChangeListener((buttonView, isChecked) ->{
            if (isChecked){
                year_bar_problems_result=1;
            }
            else{
                year_bar_problems_result=0;
            }
        } );
        year_filters_problems_obj.setOnCheckedChangeListener((buttonView, isChecked) ->{
            if (isChecked){
                year_filters_problems_result=1;
            }
            else{
                year_filters_problems_result=0;
            }
        } );
        year_otb_problems_obj.setOnCheckedChangeListener((buttonView, isChecked) ->{
            if (isChecked){
                year_otb_problems_result=1;
            }
            else{
                year_otb_problems_result=0;
            }
        } );

        serial_number_obj.setText(Activity_qr_scaner.serial_number);
    }
}