package com.example.fire_checker;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.JsonReader;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class Activity_every_cvartal_checking extends AppCompatActivity {
    final int[] result = {0};
    final int[] check = {0};
    TextView serial_number_obj;
    ConstraintLayout cvartal_progress_layout_obj;
    ProgressBar cvartal_progress_bar_obj;
    CheckBox cvartal_hard_problems_obj, cvartal_appearence_problems_obj, cvartal_instruction_problems_obj, cvartal_fuse_problems_obj, cvartal_manometr_problems_obj, cvartal_label_problems_obj, cvartal_weight_problems_obj, cvartal_shlang_problems_obj, cvartal_place_problems_obj, cvartal_bar_problems_obj;
    Integer cvartal_hard_problems_result = 0, cvartal_appearence_problems_result=0, cvartal_instruction_problems_result = 0, cvartal_fuse_problems_result = 0, cvartal_manometr_problems_result = 0, cvartal_label_problems_result = 0, cvartal_weight_problems_result = 0, cvartal_shlang_problems_result = 0, cvartal_place_problems_result = 0, cvartal_bar_problems_result = 0;
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
        cvartal_progress_layout_obj=findViewById(R.id.cvartal_progress_layout);
        cvartal_progress_bar_obj=findViewById(R.id.cvartal_progress_bar);

        serial_number_obj.setText(Activity_qr_scaner.serial_number);
        cvartal_progress_layout_obj.setVisibility(View.GONE);


        cvartal_send_btn.setOnClickListener(v -> {
            cvartal_hard_problems_result=cvartal_hard_problems_obj.isChecked()?1:0;
            cvartal_appearence_problems_result=cvartal_appearence_problems_obj.isChecked()?1:0;
            cvartal_instruction_problems_result=cvartal_instruction_problems_obj.isChecked()?1:0;
            cvartal_fuse_problems_result=cvartal_fuse_problems_obj.isChecked()?1:0;
            cvartal_manometr_problems_result=cvartal_manometr_problems_obj.isChecked()?1:0;
            cvartal_label_problems_result=cvartal_label_problems_obj.isChecked()?1:0;
            cvartal_weight_problems_result=cvartal_weight_problems_obj.isChecked()?1:0;
            cvartal_place_problems_result=cvartal_place_problems_obj.isChecked()?1:0;
            cvartal_shlang_problems_result=cvartal_shlang_problems_obj.isChecked()?1:0;
            cvartal_bar_problems_result=cvartal_bar_problems_obj.isChecked()?1:0;
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
                ArrayAdapter<?> types_adapter = ArrayAdapter.createFromResource(Activity_every_cvartal_checking.this, R.array.types_checking, android.R.layout.simple_spinner_item);
                Button dialog_review_send_btn = (Button) dialog_review_cvartal.findViewById(R.id.dialog_service_and_review_type_choser_btn);
                dialog_review_cvartal.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                types_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dialog_review_obj.setAdapter(types_adapter);
                dialog_review_obj.setSelection(0);
                dialog_review_cvartal.setCancelable(false);

                dialog_review_send_btn.setOnClickListener(v -> {
                    if (cvartal_type_chosen.equals("Проверка пройдена")){
                       dialog_review_cvartal.dismiss();
                       quarterlu_check quarter_check =new quarterlu_check();
                       quarter_check.execute();
                    }
                    else if(cvartal_type_chosen.equals("Отправить на перезаправку")){
                        set_status_request("onRefile",new Date(), Activity_qr_scaner.serial_number, MainActivity.token, dialog_review_cvartal );
                    }
                    else if (cvartal_type_chosen.equals("Отправить в ремонт")) {
                        set_status_request("onRepair",new Date(), Activity_qr_scaner.serial_number, MainActivity.token, dialog_review_cvartal );
                    }
                    else if (cvartal_type_chosen.equals("Вывести из эксплуатации")) {
                        set_status_request("decommissioned",new Date(), Activity_qr_scaner.serial_number, MainActivity.token, dialog_review_cvartal );
                    }
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


    public class quarterlu_check extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            cvartal_progress_layout_obj.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String serial_number=Activity_qr_scaner.serial_number;
            String appearence = cvartal_appearence_problems_result.toString();
            String cover = cvartal_hard_problems_result.toString();
            String instruction = cvartal_instruction_problems_result.toString();
            String fuse = cvartal_fuse_problems_result.toString();
            String manometr = cvartal_manometr_problems_result.toString();
            String label = cvartal_label_problems_result.toString();
            String place = cvartal_place_problems_result.toString();
            String weight = cvartal_weight_problems_result.toString();
            String shlang = cvartal_shlang_problems_result.toString();
            String bar= cvartal_bar_problems_result.toString();
            String token = MainActivity.token;
            String update_check_post_params = "fire_id=" + serial_number +"&appearence="+appearence +"&cover="+cover +"&instruction="+instruction+"&fuse="+fuse+"&manometr" +
                    manometr+"&label="+label+"&weight="+weight+"&place="+place+"&shlang="+shlang+"&bar="+bar;
            String PROPERTY_AUTH = "Bearer " + token;
            URL update_check_endpoint = null;
            try {
                update_check_endpoint = new URL("http://194.67.55.58:8080/api/quarterlyCheck");
                try {
                    HttpURLConnection update_check_connection = (HttpURLConnection) update_check_endpoint.openConnection();
                    update_check_connection.setRequestMethod("POST");
                    update_check_connection.setRequestProperty("Authorization", PROPERTY_AUTH);

                    update_check_connection.setDoOutput(true);
                    OutputStream os = update_check_connection.getOutputStream();
                    os.write(update_check_post_params.getBytes());
                    os.flush();
                    os.close();

                    if (update_check_connection.getResponseCode() == 200) {
                        InputStream update_check_response = update_check_connection.getInputStream();
                        InputStreamReader update_check_response_reader = new InputStreamReader(update_check_response, StandardCharsets.UTF_8);
                        JsonReader update_check_json_reader = new JsonReader(update_check_response_reader);
                        update_check_json_reader.beginObject();
                        while (update_check_json_reader.hasNext()) {
                            String key = update_check_json_reader.nextName();
                            if (key.equals("result")) {
                                String value = update_check_json_reader.nextString();
                                if (value.equals("success")) {
                                    result[0] =1;
                                }
                                else{

                                    api_error result_error=new api_error();
                                    result_error.dialog_api_error_starter();
                                    break;
                                }
                            }
                            else if(key.equals("data")){
                                update_check_json_reader.beginObject();
                                while (update_check_json_reader.hasNext()){
                                    String key_2 = update_check_json_reader.nextName();
                                    if (key_2.equals("check_succed")){
                                        check[0]=1;
                                    }
                                }
                            }
                            else {
                                update_check_json_reader.skipValue();
                            }
                        }
                        update_check_json_reader.close();
                        update_check_connection.disconnect();
                    } else {
                        api_error result_error=new api_error();
                        result_error.dialog_api_error_starter();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid){
            super.onPostExecute(aVoid);
            cvartal_progress_layout_obj.setVisibility(View.GONE);
            if(check[0]==1) {
                Activity_qr_scaner.serial_number = "";
                startActivity(new Intent(Activity_every_cvartal_checking.this, Activity_qr_scaner.class));
            }
            else{
                check_error check_er=new check_error();
                check_er.dialog_check_error_starter();
            }
        }
    }
    protected void set_status_request(String type, Date date, String serial_number, String token, Dialog main_dialog){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                String set_status_post_params = "fire_id=" + serial_number + "&date=" + date + "&type=" + type;
                String PROPERTY_AUTH = "Bearer " + token;
                URL set_status_endpoint = null;
                try {
                    set_status_endpoint = new URL("http://194.67.55.58:8080/api/changeStatus");
                    try {
                        HttpURLConnection set_status_connection = (HttpURLConnection) set_status_endpoint.openConnection();
                        set_status_connection.setRequestMethod("POST");
                        set_status_connection.setRequestProperty("Authorization", PROPERTY_AUTH);

                        set_status_connection.setDoOutput(true);
                        OutputStream os = set_status_connection.getOutputStream();
                        os.write(set_status_post_params.getBytes());
                        os.flush();
                        os.close();

                        if (set_status_connection.getResponseCode() == 200) {
                            InputStream set_status_response = set_status_connection.getInputStream();
                            InputStreamReader set_status_response_reader = new InputStreamReader(set_status_response, StandardCharsets.UTF_8);
                            JsonReader set_status_json_reader = new JsonReader(set_status_response_reader);
                            set_status_json_reader.beginObject();
                            while (set_status_json_reader.hasNext()) {
                                String key = set_status_json_reader.nextName();
                                if (key.equals("result")) {
                                    String value = set_status_json_reader.nextString();
                                    if (value.equals("success")) {
                                        Activity_qr_scaner.serial_number = "";
                                        Activity_type_choser.chosen_type = "";
                                        main_dialog.dismiss();
                                        startActivity(new Intent(Activity_every_cvartal_checking.this, Activity_type_choser.class));
                                    }
                                    else{
                                        api_error result_error=new api_error();
                                        result_error.dialog_api_error_starter();
                                        break;
                                    }
                                }
                                else {
                                    set_status_json_reader.skipValue();
                                }
                            }
                            set_status_json_reader.close();
                            set_status_connection.disconnect();
                        } else {
                            api_error result_error=new api_error();
                            result_error.dialog_api_error_starter();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }


            }
        });

    }
}