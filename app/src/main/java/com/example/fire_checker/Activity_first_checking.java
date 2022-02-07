package com.example.fire_checker;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
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

public class Activity_first_checking extends AppCompatActivity {
    ProgressBar first_progress_bar_obj;
    ConstraintLayout first_progress_layout;
    TextView serial_number_obj;
    CheckBox first_hard_problems_obj, first_appearence_problems_obj, first_instruction_problems_obj, first_fuse_problems_obj, first_manometr_problems_obj, first_label_problems_obj, first_weight_problems_obj, first_shlang_problems_obj, first_bar_problems_obj;
    Integer first_hard_problems_result = 0, first_appearence_problems_result = 0, first_instruction_problems_result = 0, first_fuse_problems_result = 0, first_manometr_problems_result = 0, first_label_problems_result = 0, first_weight_problems_result = 0, first_shlang_problems_result = 0, first_bar_problems_result = 0;
    Button first_accept_btn, first_back_btn;
    final int[] result = {0};
    final int[] check = {0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_checking);

        serial_number_obj = findViewById(R.id.first_serial_number_field);
        first_accept_btn = findViewById(R.id.first_send_btn);
        first_back_btn = findViewById(R.id.first_back_btn);
        first_hard_problems_obj = findViewById(R.id.first_hard_problems_appearence_check_box);
        first_appearence_problems_obj = findViewById(R.id.first_problems_appearence_check_box);
        first_instruction_problems_obj = findViewById(R.id.first_instruction_problems_check_box);
        first_fuse_problems_obj = findViewById(R.id.first_fuse_problems_check_box);
        first_manometr_problems_obj = findViewById(R.id.first_manometr_problems_check_box);
        first_label_problems_obj = findViewById(R.id.first_label_problems_check_box);
        first_weight_problems_obj = findViewById(R.id.first_weight_problems_check_box);
        first_shlang_problems_obj = findViewById(R.id.first_shlang_problems_check_box);
        first_bar_problems_obj = findViewById(R.id.first_bar_problems_check_box);
        first_progress_bar_obj =findViewById(R.id.first_progress_bar);
        first_progress_layout = findViewById(R.id.first_progress_layout);

        first_progress_layout.setVisibility(View.GONE);
        serial_number_obj.setText(Activity_qr_scaner.serial_number);

        first_accept_btn.setOnClickListener(v -> {
            first_hard_problems_result = first_hard_problems_obj.isChecked() ? 1 : 0;
            first_appearence_problems_result = first_appearence_problems_obj.isChecked() ? 1 : 0;
            first_instruction_problems_result = first_instruction_problems_obj.isChecked() ? 1 : 0;
            first_fuse_problems_result = first_fuse_problems_obj.isChecked() ? 1 : 0;
            first_manometr_problems_result = first_manometr_problems_obj.isChecked() ? 1 : 0;
            first_label_problems_result = first_label_problems_obj.isChecked() ? 1 : 0;
            first_weight_problems_result = first_weight_problems_obj.isChecked() ? 1 : 0;
            first_shlang_problems_result = first_shlang_problems_obj.isChecked() ? 1 : 0;
            first_bar_problems_result = (first_bar_problems_obj.isChecked() ? 1 : 0);
            update_check first_check = new update_check();
            first_check.execute();
        });

        first_back_btn.setOnClickListener(v -> {
            Activity_qr_scaner.serial_number = "";
            startActivity(new Intent(Activity_first_checking.this, Activity_qr_scaner.class));
        });

    }


    public class update_check extends AsyncTask<Void, Void, Void> {
        @Override
        public void onPreExecute() {
            super.onPreExecute();
            first_progress_layout.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String serial_number = Activity_qr_scaner.serial_number;
            String appearence = first_appearence_problems_result.toString();
            String cover = first_hard_problems_result.toString();
            String instruction = first_instruction_problems_result.toString();
            String fuse = first_fuse_problems_result.toString();
            String manometr = first_manometr_problems_result.toString();
            String label = first_label_problems_result.toString();
            String weight = first_weight_problems_result.toString();
            String shlang = first_shlang_problems_result.toString();
            String bar = first_bar_problems_result.toString();
            String update_check_post_params = "fire_id=" + serial_number + "&appearence=" + appearence + "&cover=" + cover + "&instruction=" + instruction + "&fuse=" + fuse + "&manometr=" +
                    manometr + "&label=" + label + "&weight=" + weight + "&shlang=" + shlang + "&bar=" + bar;
            String PROPERTY_AUTH = "Bearer " + MainActivity.token;
            URL update_check_endpoint = null;
            try {
                update_check_endpoint = new URL("http://194.67.55.58:8080/api/initialCheck");
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
                                } else {
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
                    } else {
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
            first_progress_layout.setVisibility(View.GONE);
            Activity_qr_scaner.serial_number = "";
            if (check[0] == 1 && result[0]==1) {
                startActivity(new Intent(Activity_first_checking.this, Activity_qr_scaner.class));
            }
            else if (check[0]==0 && result[0]==1){
                check_error check_error= new check_error();
                check_error.dialog_check_error_starter(Activity_first_checking.this);
            }
            else{
                api_error error = new api_error();
                error.dialog_api_error_starter(Activity_first_checking.this);
            }

        }


    }
}