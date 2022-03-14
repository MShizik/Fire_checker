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
import android.widget.EditText;
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

public class Activity_everyyear_checking extends AppCompatActivity {
    final int[] result = {0};
    final int[] check = {0};
    TextView serial_number_obj;
    CheckBox year_hard_problems_obj, year_appearence_problems_obj, year_instruction_problems_obj, year_fuse_problems_obj, year_manometr_problems_obj, year_label_problems_obj, year_weight_problems_obj, year_shlang_problems_obj, year_place_problems_obj, year_bar_problems_obj, year_filters_problems_obj, year_otb_problems_obj;
    EditText year_utechka_problems_obj;
    Integer year_hard_problems_result = 0;
    Integer year_appearence_problems_result = 0;
    Integer year_instruction_problems_result = 0;
    Integer year_fuse_problems_result = 0;
    Integer year_manometr_problems_result = 0;
    Integer year_label_problems_result = 0;
    Integer year_weight_problems_result = 0;
    Integer year_shlang_problems_result = 0;
    Integer year_place_problems_result = 0;
    Integer year_bar_problems_result = 0;
    Integer year_filters_problems_result = 0;
    Integer year_otb_problems_result = 0;
    Integer year_utechka_problems_result;
    Button year_send_btn, year_back_btn;
    ConstraintLayout year_progress_layout_obj;
    ProgressBar year_progress_bar_obj;
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
        year_progress_layout_obj = findViewById(R.id.year_progress_layout);
        year_progress_bar_obj = findViewById(R.id.year_progress_bar);

        year_progress_layout_obj.setVisibility(View.GONE);

        serial_number_obj.setText(Activity_qr_scaner.serial_number);

        year_send_btn.setOnClickListener(v -> {
            year_hard_problems_result = year_hard_problems_obj.isChecked() ? 1 : 0;
            year_appearence_problems_result = year_appearence_problems_obj.isChecked() ? 1 : 0;
            year_instruction_problems_result = year_instruction_problems_obj.isChecked() ? 1 : 0;
            year_fuse_problems_result = year_fuse_problems_obj.isChecked() ? 1 : 0;
            year_manometr_problems_result = year_manometr_problems_obj.isChecked() ? 1 : 0;
            year_label_problems_result = year_label_problems_obj.isChecked() ? 1 : 0;
            year_weight_problems_result = year_weight_problems_obj.isChecked() ? 1 : 0;
            year_place_problems_result = year_place_problems_obj.isChecked() ? 1 : 0;
            year_shlang_problems_result = year_shlang_problems_obj.isChecked() ? 1 : 0;
            year_bar_problems_result = year_bar_problems_obj.isChecked() ? 1 : 0;
            year_filters_problems_result = year_filters_problems_obj.isChecked() ? 1 : 0;
            year_otb_problems_result = year_otb_problems_obj.isChecked() ? 1 : 0;
            year_utechka_problems_result = 0;
            dialog_service_and_review_starter();
        });

        year_back_btn.setOnClickListener(v -> {
            Activity_type_choser.chosen_type = "";
            startActivity(new Intent(Activity_everyyear_checking.this, Activity_Random_check.class));
        });
    }

    protected void dialog_service_and_review_starter() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Dialog dialog_review_year = new Dialog(Activity_everyyear_checking.this);

                dialog_review_year.setContentView(R.layout.dialog_service_and_review);
                Spinner dialog_review_obj = (Spinner) dialog_review_year.findViewById(R.id.dialog_service_and_review_type_choser_field);
                ArrayAdapter<?> types_adapter = ArrayAdapter.createFromResource(Activity_everyyear_checking.this, R.array.types_checking, android.R.layout.simple_spinner_item);
                Button dialog_review_send_btn = (Button) dialog_review_year.findViewById(R.id.dialog_service_and_review_type_choser_btn);
                dialog_review_year.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                types_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dialog_review_obj.setAdapter(types_adapter);
                dialog_review_obj.setSelection(0);
                dialog_review_year.setCancelable(false);

                dialog_review_send_btn.setOnClickListener(v -> {
                    if (year_type_chosen.equals("Проверка пройдена")) {
                        everyyear_check_request check_request = new everyyear_check_request();
                        check_request.execute();
                        dialog_review_year.dismiss();
                    } else if (year_type_chosen.equals("Отправить на перезаправку")) {
                        year_type_chosen = MainActivity.on_refile;
                        set_status_request set_status = new set_status_request();
                        set_status.execute();
                    } else if (year_type_chosen.equals("Отправить в ремонт")) {
                        year_type_chosen = MainActivity.on_repair;
                        set_status_request set_status = new set_status_request();
                        set_status.execute();
                    } else if (year_type_chosen.equals("Вывести из эксплуатации")) {
                        year_type_chosen = MainActivity.utilization;
                        set_status_request set_status = new set_status_request();
                        set_status.execute();
                    }
                });

                dialog_review_obj.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String[] type = getResources().getStringArray(R.array.types_checking);
                        year_type_chosen = type[i].toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                dialog_review_year.show();
            }
        });
    }

    public class set_status_request extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            result[0] = 0;
            check[0] = 0;
            year_progress_layout_obj.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String set_status_post_params = "fire_id=" + Activity_qr_scaner.serial_number + "&date=" + new Date() + "&type=" + year_type_chosen.toString();
            String PROPERTY_AUTH = "Bearer " + MainActivity.token;
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
                        result[0] = 1;
                        InputStream set_status_response = set_status_connection.getInputStream();
                        InputStreamReader set_status_response_reader = new InputStreamReader(set_status_response, StandardCharsets.UTF_8);
                        JsonReader set_status_json_reader = new JsonReader(set_status_response_reader);
                        set_status_json_reader.beginObject();
                        while (set_status_json_reader.hasNext()) {
                            String key = set_status_json_reader.nextName();
                            if (key.equals("result")) {
                                String value = set_status_json_reader.nextString();
                                if (value.equals("success")) {
                                    check[0] = 1;
                                } else {
                                    check[0] = 0;
                                    break;
                                }
                            } else {
                                set_status_json_reader.skipValue();
                            }
                        }
                        set_status_json_reader.close();
                        set_status_connection.disconnect();
                    } else {
                        result[0] = 0;
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
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            year_progress_layout_obj.setVisibility(View.GONE);
            if (check[0] == 1 && result[0] == 1) {
                Activity_qr_scaner.serial_number = "";
                Activity_type_choser.chosen_type = "";
                startActivity(new Intent(Activity_everyyear_checking.this, Activity_type_choser.class));
            } else if (check[0] == 0 && result[0] == 1) {
                check_error check_er = new check_error();
                check_er.dialog_check_error_starter(Activity_everyyear_checking.this);
            } else {
                api_error result_error = new api_error();
                result_error.dialog_api_error_starter(Activity_everyyear_checking.this);
            }
        }
    }

    public class everyyear_check_request extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            result[0] = 0;
            check[0] = 0;
            year_progress_layout_obj.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String serial_number = Activity_qr_scaner.serial_number;
            String appearence = year_appearence_problems_result.toString();
            String cover = year_hard_problems_result.toString();
            String instruction = year_instruction_problems_result.toString();
            String fuse = year_fuse_problems_result.toString();
            String manometr = year_manometr_problems_result.toString();
            String label = year_label_problems_result.toString();
            String place = year_place_problems_result.toString();
            String weight = year_weight_problems_result.toString();
            String shlang = year_shlang_problems_result.toString();
            String bar = year_bar_problems_result.toString();
            String OTB = year_otb_problems_result.toString();
            String utechka = year_utechka_problems_result.toString();
            String token = MainActivity.token;
            String update_check_post_params = "fire_id=" + serial_number + "&appearence=" + appearence + "&cover=" + cover + "&instruction=" + instruction + "&fuse=" + fuse + "&manometr=" +
                    manometr + "&label=" + label + "&weight=" + weight + "&place=" + place + "&shlang=" + shlang + "&bar=" + bar + "&OTB=" + OTB + "&utechka=" + utechka;
            String PROPERTY_AUTH = "Bearer " + token;
            URL update_check_endpoint = null;
            try {
                update_check_endpoint = new URL("http://194.67.55.58:8080/api/annualCheck");
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
                                    result[0] = 1;
                                }
                                else {
                                    result[0]=0;
                                    check[0]=0;
                                    break;
                                }
                            } else if (key.equals("data")) {
                                update_check_json_reader.beginObject();
                                while (update_check_json_reader.hasNext()) {
                                    String key_2 = update_check_json_reader.nextName();
                                    if (key_2.equals("check_succed")) {
                                        if (update_check_json_reader.nextInt() == 1) {
                                            check[0] = 1;
                                        }
                                    }
                                }
                            } else {
                                update_check_json_reader.skipValue();
                            }
                        }
                    update_check_json_reader.close();
                    update_check_connection.disconnect();
                } else{

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch(
        MalformedURLException e)

        {
            e.printStackTrace();
        }
            return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        year_progress_layout_obj.setVisibility(View.GONE);
        if (check[0] == 1 && result[0] == 1) {
            year_type_chosen = MainActivity.expluatation;
            set_status_request set_status =new set_status_request();
            set_status.execute();
        } else if (check[0] == 0 && result[0] == 1) {
            check_error check_error = new check_error();
            check_error.dialog_check_error_starter(Activity_everyyear_checking.this);
        } else {
            api_error error = new api_error();
            error.dialog_api_error_starter(Activity_everyyear_checking.this);
        }
    }
}

}