package com.example.fire_checker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.TextView;

public class Activity_first_checking extends AppCompatActivity {

    TextView serial_number_obj;
    CheckBox first_hard_problems_obj, first_appearence_problems_obj, first_instruction_problems_obj, first_fuse_problems_obj, first_manometr_problems_obj, first_label_problems_obj, first_weight_problems_obj, first_shlang_problems_obj, first_bar_problems_obj;
    Integer first_hard_problems_result=0,first_appearence_problems_result=0,first_instruction_problems_result=0, first_fuse_problems_result=0, first_manometr_problems_result=0, first_label_problems_result=0, first_weight_problems_result=0, first_shlang_problems_result=0, first_bar_problems_result=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_checking);

        serial_number_obj=findViewById(R.id.first_serial_number_field);
        first_hard_problems_obj=findViewById(R.id.first_hard_problems_appearence_check_box);
        first_appearence_problems_obj=findViewById(R.id.first_problems_appearence_check_box);
        first_instruction_problems_obj=findViewById(R.id.first_instruction_problems_check_box);
        first_fuse_problems_obj=findViewById(R.id.first_fuse_problems_check_box);
        first_manometr_problems_obj=findViewById(R.id.first_manometr_problems_check_box);
        first_label_problems_obj=findViewById(R.id.first_label_problems_check_box);
        first_weight_problems_obj=findViewById(R.id.first_weight_problems_check_box);
        first_shlang_problems_obj=findViewById(R.id.first_shlang_problems_check_box);
        first_bar_problems_obj=findViewById(R.id.first_bar_problems_check_box);

        serial_number_obj.setText(Activity_qr_scaner.serial_number);

        first_hard_problems_obj.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                first_hard_problems_result=1;
            }
            else {
                first_hard_problems_result=0;
            }
        });
        first_appearence_problems_obj.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                first_appearence_problems_result=1;
            }
            else {
                first_appearence_problems_result=0;
            }
        });
        first_instruction_problems_obj.setOnCheckedChangeListener((buttonView, isChecked) ->{
            if (isChecked){
                first_instruction_problems_result=1;
            }
            else{
                first_instruction_problems_result=0;
            }
        } );
        first_fuse_problems_obj.setOnCheckedChangeListener((buttonView, isChecked) ->{
            if (isChecked){
                first_fuse_problems_result=1;
            }
            else{
                first_fuse_problems_result=0;
            }
        } );
        first_manometr_problems_obj.setOnCheckedChangeListener((buttonView, isChecked) ->{
            if (isChecked){
                first_manometr_problems_result=1;
            }
            else{
                first_manometr_problems_result=0;
            }
        } );
        first_label_problems_obj.setOnCheckedChangeListener((buttonView, isChecked) ->{
            if (isChecked){
                first_label_problems_result=1;
            }
            else{
                first_label_problems_result=0;
            }
        } );
        first_weight_problems_obj.setOnCheckedChangeListener((buttonView, isChecked) ->{
            if (isChecked){
                first_weight_problems_result=1;
            }
            else{
                first_weight_problems_result=0;
            }
        } );
        first_shlang_problems_obj.setOnCheckedChangeListener((buttonView, isChecked) ->{
            if (isChecked){
                first_shlang_problems_result=1;
            }
            else{
                first_shlang_problems_result=0;
            }
        } );
        first_bar_problems_obj.setOnCheckedChangeListener((buttonView, isChecked) ->{
            if (isChecked){
                first_bar_problems_result=1;
            }
            else{
                first_bar_problems_result=0;
            }
        } );

    }
}