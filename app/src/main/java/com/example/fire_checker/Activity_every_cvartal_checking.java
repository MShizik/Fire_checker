package com.example.fire_checker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.TextView;

public class Activity_every_cvartal_checking extends AppCompatActivity {

    TextView serial_number_obj;
    CheckBox cvartal_hard_problems_obj, cvartal_appearence_problems_obj, cvartal_instruction_problems_obj, cvartal_fuse_problems_obj, cvartal_manometr_problems_obj, cvartal_label_problems_obj, cvartal_weight_problems_obj, cvartal_shlang_problems_obj, cvartal_place_problems_obj, cvartal_bar_problems_obj;
    Integer cvartal_hard_problems_result=0,cvartal_appearence_problems_result=0,cvartal_instruction_problems_result=0, cvartal_fuse_problems_result=0, cvartal_manometr_problems_result=0, cvartal_label_problems_result=0, cvartal_weight_problems_result=0, cvartal_shlang_problems_result=0, cvartal_place_problems_result=0, cvartal_bar_problems_result=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_every_cvartal_checking);

        serial_number_obj=findViewById(R.id.cvartal_serial_number_field);
        cvartal_hard_problems_obj=findViewById(R.id.cvartal_hard_problems_appearence_check_box);
        cvartal_appearence_problems_obj=findViewById(R.id.cvartal_problems_appearence_check_box);
        cvartal_instruction_problems_obj=findViewById(R.id.cvartal_instruction_problems_check_box);
        cvartal_fuse_problems_obj=findViewById(R.id.cvartal_fuse_problems_check_box);
        cvartal_manometr_problems_obj=findViewById(R.id.cvartal_manometr_problems_check_box);
        cvartal_label_problems_obj=findViewById(R.id.cvartal_label_problems_check_box);
        cvartal_weight_problems_obj=findViewById(R.id.cvartal_weight_problems_check_box);
        cvartal_shlang_problems_obj=findViewById(R.id.cvartal_shlang_problems_check_box);
        cvartal_place_problems_obj=findViewById(R.id.cvartal_place_problems_check_box);
        cvartal_bar_problems_obj=findViewById(R.id.cvartal_bar_problems_check_box);


        serial_number_obj.setText(Activity_qr_scaner.serial_number);

        cvartal_hard_problems_obj.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                cvartal_hard_problems_result=1;
            }
            else {
                cvartal_hard_problems_result=0;
            }
        });
        cvartal_appearence_problems_obj.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                cvartal_appearence_problems_result=1;
            }
            else {
                cvartal_appearence_problems_result=0;
            }
        });
        cvartal_instruction_problems_obj.setOnCheckedChangeListener((buttonView, isChecked) ->{
            if (isChecked){
                cvartal_instruction_problems_result=1;
            }
            else{
                cvartal_instruction_problems_result=0;
            }
        } );
        cvartal_fuse_problems_obj.setOnCheckedChangeListener((buttonView, isChecked) ->{
            if (isChecked){
                cvartal_fuse_problems_result=1;
            }
            else{
                cvartal_fuse_problems_result=0;
            }
        } );
        cvartal_manometr_problems_obj.setOnCheckedChangeListener((buttonView, isChecked) ->{
            if (isChecked){
                cvartal_manometr_problems_result=1;
            }
            else{
                cvartal_manometr_problems_result=0;
            }
        } );
        cvartal_label_problems_obj.setOnCheckedChangeListener((buttonView, isChecked) ->{
            if (isChecked){
                cvartal_label_problems_result=1;
            }
            else{
                cvartal_label_problems_result=0;
            }
        } );
        cvartal_weight_problems_obj.setOnCheckedChangeListener((buttonView, isChecked) ->{
            if (isChecked){
                cvartal_weight_problems_result=1;
            }
            else{
                cvartal_weight_problems_result=0;
            }
        } );
        cvartal_place_problems_obj.setOnCheckedChangeListener((buttonView, isChecked) ->{
            if (isChecked){
                cvartal_place_problems_result=1;
            }
            else{
                cvartal_place_problems_result=0;
            }
        } );
        cvartal_shlang_problems_obj.setOnCheckedChangeListener((buttonView, isChecked) ->{
            if (isChecked){
                cvartal_shlang_problems_result=1;
            }
            else{
                cvartal_shlang_problems_result=0;
            }
        } );
        cvartal_bar_problems_obj.setOnCheckedChangeListener((buttonView, isChecked) ->{
            if (isChecked){
                cvartal_bar_problems_result=1;
            }
            else{
                cvartal_bar_problems_result=0;
            }
        } );

    }
}