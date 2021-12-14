package com.example.fire_checker;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.JsonReader;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.annotation.Target;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Activity_qr_scaner extends AppCompatActivity {
    CodeScanner qr_code_scanner;
    CodeScannerView qr_code_scanner_view;
    Button scaner_home_btn;

    public static String serial_number;
    public static String service_chosen_type;
    public static Integer dialog_service_condition_problems_results;
    public static Integer dialog_on_service_condition_problems_results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scaner);

        scaner_home_btn = findViewById(R.id.scaner_home_btn);
        qr_code_scanner_view = findViewById(R.id.qr_scanner);
        qr_code_scanner = new CodeScanner(this, qr_code_scanner_view);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.black));
        }

        qr_code_scanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                serial_number = result.getText().toString();
                ownership_checker(serial_number);


            }
        });

        scaner_home_btn.setOnClickListener(v -> {
            Activity_type_choser.chosen_type = "";
            startActivity(new Intent(Activity_qr_scaner.this, Activity_type_choser.class));
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        qr_code_scanner.startPreview();
    }

    @Override
    protected void onPause() {
        qr_code_scanner.releaseResources();
        super.onPause();
    }


    protected void dialog_error_existance_starter() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Dialog dialog_error_existance = new Dialog(Activity_qr_scaner.this);
                dialog_error_existance.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
                dialog_error_existance.setContentView(R.layout.dialog_error_existance);
                Button dialog_error_existance_back_btn = (Button) dialog_error_existance.findViewById(R.id.dialog_error_existance_back_btn);
                dialog_error_existance_back_btn.setOnClickListener(v -> {
                    dialog_error_existance.dismiss();
                    serial_number = "";
                });
                dialog_error_existance.setCancelable(false);
                dialog_error_existance.show();
            }
        });
    }

    protected void dialog_error_used_user_starter() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Dialog dialog_error_used_user = new Dialog(Activity_qr_scaner.this);
                dialog_error_used_user.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
                dialog_error_used_user.setContentView(R.layout.dialog_error_used_user);
                Button dialog_error_used_user_back_btn = (Button) dialog_error_used_user.findViewById(R.id.dialog_error_used_user_back_btn);
                dialog_error_used_user_back_btn.setOnClickListener(v -> {
                    dialog_error_used_user.dismiss();
                    serial_number = "";
                });
                dialog_error_used_user.setCancelable(false);
                dialog_error_used_user.show();
            }
        });
    }

    protected void dialog_error_used_dif_starter() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Dialog dialog_error_used_dif = new Dialog(Activity_qr_scaner.this);
                dialog_error_used_dif.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
                dialog_error_used_dif.setContentView(R.layout.dialog_error_used_dif);
                Button dialog_error_used_dif_btn = (Button) dialog_error_used_dif.findViewById(R.id.dialog_error_used_dif_back_btn);
                dialog_error_used_dif_btn.setOnClickListener(v -> {
                    dialog_error_used_dif.dismiss();
                    serial_number = "";
                });
                dialog_error_used_dif.setCancelable(false);
                dialog_error_used_dif.show();
            }
        });
    }

    protected void dialog_util_starter() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Dialog dialog_util = new Dialog(Activity_qr_scaner.this);
                dialog_util.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog_util.setContentView(R.layout.dialog_utilization_confirmation);
                Button dialog_util_confirmation_btn = (Button) dialog_util.findViewById(R.id.dialog_utilization_util_btn);
                dialog_util_confirmation_btn.setOnClickListener(v -> {
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            String type="decommissioned";
                            Date current_date = new Date();
                            String util_post_params = "fire_id=" + Activity_qr_scaner.serial_number + "&date=" + current_date + "&type=" + type;
                            String PROPERTY_AUTH = "Bearer " + MainActivity.token;
                            URL util_endpoint = null;
                            try {
                                util_endpoint = new URL("http://194.67.55.58:8080/api/changeStatus");
                                try {
                                    HttpURLConnection util_connection = (HttpURLConnection) util_endpoint.openConnection();
                                    util_connection.setRequestMethod("POST");
                                    util_connection.setRequestProperty("Authorization", PROPERTY_AUTH);

                                    util_connection.setDoOutput(true);
                                    OutputStream os = util_connection.getOutputStream();
                                    os.write(util_post_params.getBytes());
                                    os.flush();
                                    os.close();

                                    if (util_connection.getResponseCode() == 200) {
                                        InputStream util_response = util_connection.getInputStream();
                                        InputStreamReader util_response_reader = new InputStreamReader(util_response, StandardCharsets.UTF_8);
                                        JsonReader util_json_reader = new JsonReader(util_response_reader);
                                        util_json_reader.beginObject();
                                        while (util_json_reader.hasNext()) {
                                            String key = util_json_reader.nextName();
                                            if (key.equals("result")) {
                                                String value = util_json_reader.nextString();
                                                if (value.equals("success")) {
                                                    Activity_qr_scaner.serial_number = "";
                                                    Activity_type_choser.chosen_type = "";
                                                    startActivity(new Intent(Activity_qr_scaner.this, Activity_type_choser.class));
                                                    dialog_util.dismiss();
                                                }
                                            } else {
                                                util_json_reader.skipValue();
                                            }
                                        }
                                        util_json_reader.close();
                                        util_connection.disconnect();
                                    } else {

                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }


                        }
                    });
                });
                dialog_util.setCancelable(false);
                dialog_util.show();
            }
        });
    }

    protected void dialog_refile_starter() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                String PROPERTY_AUTH = "Bearer " + MainActivity.token;
                URL refile_endpoint = null;
                try {
                    refile_endpoint = new URL("http://194.67.55.58:8080/api/getStatus?fire_id=" + Activity_qr_scaner.serial_number);
                    try {
                        HttpURLConnection refile_connection = (HttpURLConnection) refile_endpoint.openConnection();
                        refile_connection.setRequestMethod("GET");
                        refile_connection.setRequestProperty("Authorization", PROPERTY_AUTH);

                        if (refile_connection.getResponseCode() == 200) {
                            InputStream refile_response = refile_connection.getInputStream();
                            InputStreamReader refile_response_reader = new InputStreamReader(refile_response, StandardCharsets.UTF_8);
                            JsonReader refile_json_reader = new JsonReader(refile_response_reader);
                            refile_json_reader.beginObject();
                            while (refile_json_reader.hasNext()) {
                                String key = refile_json_reader.nextName();
                                if (key.equals("current_status")) {
                                    String value = refile_json_reader.nextString();
                                    if (value.equals("onRefile")) {
                                        //Диалоговое окно для принятия с заправки
                                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                                            @Override
                                            public void run() {
                                                Dialog dialog_get_from_refile = new Dialog(Activity_qr_scaner.this);
                                                dialog_get_from_refile.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                                dialog_get_from_refile.setContentView(R.layout.dialog_get_from_refile);
                                                Button dialog_get_from_refile_confirmation_btn = (Button) dialog_get_from_refile.findViewById(R.id.dialog_get_from_refile_btn);
                                                dialog_get_from_refile_confirmation_btn.setOnClickListener(v -> {
                                                    AsyncTask.execute(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            String type="refiled";
                                                            Date current_date = new Date();
                                                            String get_from_refile_post_params = "fire_id=" + Activity_qr_scaner.serial_number + "&date=" + current_date + "&type=" + type;
                                                            String PROPERTY_AUTH = "Bearer " + MainActivity.token;
                                                            URL get_from_refile_endpoint = null;
                                                            try {
                                                                get_from_refile_endpoint = new URL("http://194.67.55.58:8080/api/changeStatus");
                                                                try {
                                                                    HttpURLConnection get_from_refile_connection = (HttpURLConnection) get_from_refile_endpoint.openConnection();
                                                                    get_from_refile_connection.setRequestMethod("POST");
                                                                    get_from_refile_connection.setRequestProperty("Authorization", PROPERTY_AUTH);

                                                                    get_from_refile_connection.setDoOutput(true);
                                                                    OutputStream os = get_from_refile_connection.getOutputStream();
                                                                    os.write(get_from_refile_post_params.getBytes());
                                                                    os.flush();
                                                                    os.close();

                                                                    if (get_from_refile_connection.getResponseCode() == 200) {
                                                                        InputStream get_from_refile_response = get_from_refile_connection.getInputStream();
                                                                        InputStreamReader get_from_refile_response_reader = new InputStreamReader(get_from_refile_response, StandardCharsets.UTF_8);
                                                                        JsonReader get_from_refile_json_reader = new JsonReader(get_from_refile_response_reader);
                                                                        get_from_refile_json_reader.beginObject();
                                                                        while (get_from_refile_json_reader.hasNext()) {
                                                                            String key = get_from_refile_json_reader.nextName();
                                                                            if (key.equals("result")) {
                                                                                String value = get_from_refile_json_reader.nextString();
                                                                                if (value.equals("success")) {
                                                                                    Activity_qr_scaner.serial_number = "";
                                                                                    Activity_type_choser.chosen_type = "";
                                                                                    startActivity(new Intent(Activity_qr_scaner.this, Activity_type_choser.class));
                                                                                    dialog_get_from_refile.dismiss();
                                                                                }
                                                                            } else {
                                                                                get_from_refile_json_reader.skipValue();
                                                                            }
                                                                        }
                                                                        get_from_refile_json_reader.close();
                                                                        get_from_refile_connection.disconnect();
                                                                    } else {

                                                                    }
                                                                } catch (IOException e) {
                                                                    e.printStackTrace();
                                                                }
                                                            } catch (MalformedURLException e) {
                                                                e.printStackTrace();
                                                            }


                                                        }
                                                    });
                                                });
                                                dialog_get_from_refile.setCancelable(false);
                                                dialog_get_from_refile.show();
                                            }
                                        });
                                    } else {
                                        //Диалоговое окно для отправки на заправку
                                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                                            @Override
                                            public void run() {
                                                Dialog dialog_send_to_refile = new Dialog(Activity_qr_scaner.this);
                                                dialog_send_to_refile.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                                dialog_send_to_refile.setContentView(R.layout.dialog_send_to_refile);
                                                Button dialog_send_to_refile_confirmation_btn = (Button) dialog_send_to_refile.findViewById(R.id.dialog_send_to_refile_btn);
                                                dialog_send_to_refile_confirmation_btn.setOnClickListener(v -> {
                                                    AsyncTask.execute(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            String type="onRefile";
                                                            Date current_date = new Date();
                                                            String send_to_refile_post_params = "fire_id=" + Activity_qr_scaner.serial_number + "&date=" + current_date + "&type=" + type;
                                                            String PROPERTY_AUTH = "Bearer " + MainActivity.token;
                                                            URL send_to_refile_endpoint = null;
                                                            try {
                                                                send_to_refile_endpoint = new URL("http://194.67.55.58:8080/api/changeStatus");
                                                                try {
                                                                    HttpURLConnection send_to_refile_connection = (HttpURLConnection) send_to_refile_endpoint.openConnection();
                                                                    send_to_refile_connection.setRequestMethod("POST");
                                                                    send_to_refile_connection.setRequestProperty("Authorization", PROPERTY_AUTH);

                                                                    send_to_refile_connection.setDoOutput(true);
                                                                    OutputStream os = send_to_refile_connection.getOutputStream();
                                                                    os.write(send_to_refile_post_params.getBytes());
                                                                    os.flush();
                                                                    os.close();

                                                                    if (send_to_refile_connection.getResponseCode() == 200) {
                                                                        InputStream send_to_refile_response = send_to_refile_connection.getInputStream();
                                                                        InputStreamReader send_to_refile_response_reader = new InputStreamReader(send_to_refile_response, StandardCharsets.UTF_8);
                                                                        JsonReader send_to_refile_json_reader = new JsonReader(send_to_refile_response_reader);
                                                                        send_to_refile_json_reader.beginObject();
                                                                        while (send_to_refile_json_reader.hasNext()) {
                                                                            String key = send_to_refile_json_reader.nextName();
                                                                            if (key.equals("result")) {
                                                                                String value = send_to_refile_json_reader.nextString();
                                                                                if (value.equals("success")) {
                                                                                    Activity_qr_scaner.serial_number = "";
                                                                                    Activity_type_choser.chosen_type = "";
                                                                                    startActivity(new Intent(Activity_qr_scaner.this, Activity_type_choser.class));
                                                                                    dialog_send_to_refile.dismiss();
                                                                                }
                                                                            } else {
                                                                                send_to_refile_json_reader.skipValue();
                                                                            }
                                                                        }
                                                                        send_to_refile_json_reader.close();
                                                                        send_to_refile_connection.disconnect();
                                                                    } else {

                                                                    }
                                                                } catch (IOException e) {
                                                                    e.printStackTrace();
                                                                }
                                                            } catch (MalformedURLException e) {
                                                                e.printStackTrace();
                                                            }


                                                        }
                                                    });
                                                });
                                                dialog_send_to_refile.setCancelable(false);
                                                dialog_send_to_refile.show();
                                            }
                                        });
                                    }
                                } else {
                                    refile_json_reader.skipValue();
                                }
                            }
                            refile_json_reader.close();
                            refile_connection.disconnect();
                        } else {

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

    protected void dialog_service_starter() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                String PROPERTY_AUTH = "Bearer " + MainActivity.token;
                URL service_endpoint = null;
                try {
                    service_endpoint = new URL("http://194.67.55.58:8080/api/getStatus?fire_id=" + Activity_qr_scaner.serial_number);
                    try {
                        HttpURLConnection service_connection = (HttpURLConnection) service_endpoint.openConnection();
                        service_connection.setRequestMethod("GET");
                        service_connection.setRequestProperty("Authorization", PROPERTY_AUTH);

                        if (service_connection.getResponseCode() == 200) {
                            InputStream service_response = service_connection.getInputStream();
                            InputStreamReader service_response_reader = new InputStreamReader(service_response, StandardCharsets.UTF_8);
                            JsonReader service_json_reader = new JsonReader(service_response_reader);
                            service_json_reader.beginObject();
                            while (service_json_reader.hasNext()) {
                                String key = service_json_reader.nextName();
                                if (key.equals("current_status")) {
                                    String value = service_json_reader.nextString();
                                    if (value.equals("onService")) {
                                        //Диалоговое окно для закрытия обслуживания
                                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                                            @Override
                                            public void run() {
                                                Dialog dialog_service = new Dialog(Activity_qr_scaner.this);
                                                dialog_service.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                                dialog_service.setContentView(R.layout.dialog_on_service);
                                                Spinner dialog_service_obj = (Spinner) dialog_service.findViewById(R.id.dialog_on_service_type_choser_field);
                                                ArrayAdapter<?> types_adapter = ArrayAdapter.createFromResource(Activity_qr_scaner.this, R.array.types_service_review, android.R.layout.simple_spinner_item);
                                                Button dialog_service_send_btn = (Button) dialog_service.findViewById(R.id.dialog_on_service_type_choser_btn);
                                                CheckBox dialog_service_condition_problems = (CheckBox) dialog_service.findViewById(R.id.dialog_on_service_condition_check_box);

                                                types_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                dialog_service_obj.setAdapter(types_adapter);
                                                dialog_service_obj.setSelection(0);
                                                dialog_service.setCancelable(false);

                                                dialog_service_send_btn.setOnClickListener(v -> {
                                                    AsyncTask.execute(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            String type="";
                                                            Date current_date = new Date();
                                                            if (service_chosen_type.equals("Отправить на заправку")) {
                                                                type = "onRefile";
                                                            } else if (service_chosen_type.equals("Отправить на ремонт")) {
                                                                type = "onRepair";
                                                            } else if (service_chosen_type.equals("Вывести из эксплуатации")) {
                                                                type = "decommissioned";
                                                            }
                                                            String service_post_params = "fire_id=" + Activity_qr_scaner.serial_number + "&date=" + current_date + "&type=" + type;
                                                            String PROPERTY_AUTH = "Bearer " + MainActivity.token;
                                                            URL service_endpoint = null;
                                                            try {
                                                                service_endpoint = new URL("http://194.67.55.58:8080/api/changeStatus");
                                                                try {
                                                                    HttpURLConnection service_connection = (HttpURLConnection) service_endpoint.openConnection();
                                                                    service_connection.setRequestMethod("POST");
                                                                    service_connection.setRequestProperty("Authorization", PROPERTY_AUTH);

                                                                    service_connection.setDoOutput(true);
                                                                    OutputStream os = service_connection.getOutputStream();
                                                                    os.write(service_post_params.getBytes());
                                                                    os.flush();
                                                                    os.close();

                                                                    if (service_connection.getResponseCode() == 200) {
                                                                        InputStream service_response = service_connection.getInputStream();
                                                                        InputStreamReader service_response_reader = new InputStreamReader(service_response, StandardCharsets.UTF_8);
                                                                        JsonReader service_json_reader = new JsonReader(service_response_reader);
                                                                        service_json_reader.beginObject();
                                                                        while (service_json_reader.hasNext()) {
                                                                            String key = service_json_reader.nextName();
                                                                            if (key.equals("result")) {
                                                                                String value = service_json_reader.nextString();
                                                                                if (value.equals("success")) {
                                                                                    Activity_qr_scaner.serial_number = "";
                                                                                    Activity_type_choser.chosen_type = "";
                                                                                    startActivity(new Intent(Activity_qr_scaner.this, Activity_type_choser.class));
                                                                                    dialog_service.dismiss();
                                                                                }
                                                                            } else {
                                                                                service_json_reader.skipValue();
                                                                            }
                                                                        }
                                                                        service_json_reader.close();
                                                                        service_connection.disconnect();
                                                                    } else {

                                                                    }
                                                                } catch (IOException e) {
                                                                    e.printStackTrace();
                                                                }
                                                            } catch (MalformedURLException e) {
                                                                e.printStackTrace();
                                                            }


                                                        }
                                                    });
                                                });

                                                dialog_service_condition_problems.setOnCheckedChangeListener((buttonView, isChecked) -> {
                                                    if (isChecked) {
                                                        dialog_service_condition_problems_results = 1;
                                                    } else {
                                                        dialog_service_condition_problems_results = 0;
                                                    }
                                                });

                                                dialog_service_obj.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                    @Override
                                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                        String[] type = getResources().getStringArray(R.array.types_service_review);
                                                        service_chosen_type = type[i].toString();
                                                    }

                                                    @Override
                                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                                    }
                                                });

                                                dialog_service.show();
                                            }
                                        });
                                    } else {
                                        //Диалоговое окно для закрытия обслуживания
                                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                                            @Override
                                            public void run() {
                                                Dialog dialog_on_service = new Dialog(Activity_qr_scaner.this);
                                                dialog_on_service.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                                dialog_on_service.setContentView(R.layout.dialog_on_service);
                                                Spinner dialog_on_service_obj = (Spinner) dialog_on_service.findViewById(R.id.dialog_on_service_type_choser_field);
                                                ArrayAdapter<?> types_adapter = ArrayAdapter.createFromResource(Activity_qr_scaner.this, R.array.types_service_review, android.R.layout.simple_spinner_item);
                                                Button dialog_on_service_send_btn = (Button) dialog_on_service.findViewById(R.id.dialog_on_service_type_choser_btn);
                                                CheckBox dialog_on_service_condition_problems = (CheckBox) dialog_on_service.findViewById(R.id.dialog_on_service_condition_check_box);

                                                types_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                dialog_on_service_obj.setAdapter(types_adapter);
                                                dialog_on_service_obj.setSelection(0);
                                                dialog_on_service.setCancelable(false);

                                                dialog_on_service_send_btn.setOnClickListener(v -> {

                                                    AsyncTask.execute(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            String type="";
                                                            Date current_date = new Date();
                                                            if (service_chosen_type.equals("Принять с заправки")) {
                                                                type = "refiled";
                                                            } else if (service_chosen_type.equals("Принять с ремонта")) {
                                                                type = "repaired";
                                                            } else if (service_chosen_type.equals("Вывести из эксплуатации")) {
                                                                type = "decommissioned";
                                                            }
                                                            String on_service_post_params = "fire_id=" + Activity_qr_scaner.serial_number + "&date=" + current_date.getDate() + "&type=" + type;
                                                            String PROPERTY_AUTH = "Bearer " + MainActivity.token;
                                                            URL on_service_endpoint = null;
                                                            try {
                                                                on_service_endpoint = new URL("http://194.67.55.58:8080/api/changeStatus");
                                                                try {
                                                                    HttpURLConnection on_service_connection = (HttpURLConnection) on_service_endpoint.openConnection();
                                                                    on_service_connection.setRequestMethod("POST");
                                                                    on_service_connection.setRequestProperty("Authorization", PROPERTY_AUTH);

                                                                    on_service_connection.setDoOutput(true);
                                                                    OutputStream os = on_service_connection.getOutputStream();
                                                                    os.write(on_service_post_params.getBytes());
                                                                    os.flush();
                                                                    os.close();

                                                                    if (on_service_connection.getResponseCode() == 200) {
                                                                        InputStream on_service_response = on_service_connection.getInputStream();
                                                                        InputStreamReader on_service_response_reader = new InputStreamReader(on_service_response, StandardCharsets.UTF_8);
                                                                        JsonReader on_service_json_reader = new JsonReader(on_service_response_reader);
                                                                        on_service_json_reader.beginObject();
                                                                        while (on_service_json_reader.hasNext()) {
                                                                            String key = on_service_json_reader.nextName();
                                                                            if (key.equals("result")) {
                                                                                String value = on_service_json_reader.nextString();
                                                                                if (value.equals("success")) {
                                                                                    Activity_qr_scaner.serial_number = "";
                                                                                    Activity_type_choser.chosen_type = "";
                                                                                    startActivity(new Intent(Activity_qr_scaner.this, Activity_type_choser.class));
                                                                                    dialog_on_service.dismiss();
                                                                                }
                                                                            } else {
                                                                                on_service_json_reader.skipValue();
                                                                            }
                                                                        }
                                                                        on_service_json_reader.close();
                                                                        on_service_connection.disconnect();
                                                                    } else {

                                                                    }
                                                                } catch (IOException e) {
                                                                    e.printStackTrace();
                                                                }
                                                            } catch (MalformedURLException e) {
                                                                e.printStackTrace();
                                                            }


                                                        }
                                                    });
                                                });

                                                dialog_on_service_condition_problems.setOnCheckedChangeListener((buttonView, isChecked) -> {
                                                    if (isChecked) {
                                                        dialog_on_service_condition_problems_results = 1;
                                                    } else {
                                                        dialog_on_service_condition_problems_results = 0;
                                                    }
                                                });

                                                dialog_on_service_obj.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                    @Override
                                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                        String[] type = getResources().getStringArray(R.array.types_on_service);
                                                        service_chosen_type = type[i].toString();
                                                    }

                                                    @Override
                                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                                    }
                                                });

                                                dialog_on_service.show();
                                            }
                                        });
                                    }
                                } else {
                                    service_json_reader.skipValue();
                                }
                            }
                            service_json_reader.close();
                            service_connection.disconnect();
                        } else {

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

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    protected void ownership_checker(String serial) {
        final int[] result = new int[2];
        final String[] ownership = new String[2];
        ownership[0] = "-1";
        ownership[1] = "-1";
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                String PROPERTY_AUTH = "Bearer " + MainActivity.token;
                URL ownership_endpoint = null;
                try {
                    ownership_endpoint = new URL("http://194.67.55.58:8080/api/getExtinguisher?fire_id=" + Activity_qr_scaner.serial_number);
                    try {
                        HttpURLConnection ownership_connection = (HttpURLConnection) ownership_endpoint.openConnection();
                        ownership_connection.setRequestMethod("GET");
                        ownership_connection.setRequestProperty("Authorization", PROPERTY_AUTH);

                        if (ownership_connection.getResponseCode() == 200) {
                            InputStream ownership_response = ownership_connection.getInputStream();
                            InputStreamReader ownership_response_reader = new InputStreamReader(ownership_response, StandardCharsets.UTF_8);
                            JsonReader ownership_json_reader = new JsonReader(ownership_response_reader);
                            ownership_json_reader.beginObject();
                            while (ownership_json_reader.hasNext()) {
                                String key = ownership_json_reader.nextName();
                                System.out.println(key);
                                if (key.equals("isFree")) {
                                    ownership[0] = ownership_json_reader.nextString();
                                    break;
                                } else if (key.equals("userFire")) {
                                    ownership[1] = ownership_json_reader.nextString();
                                } else if (key.equals("result")) {
                                    if (!ownership_json_reader.nextString().equals("success")) {
                                        dialog_error_existance_starter();
                                    }
                                } else {
                                    ownership_json_reader.skipValue();
                                }
                            }
                            if (ownership[0].equals("1") && ownership[1].equals("1")) {
                                switch (Activity_type_choser.chosen_type) {
                                    case "Первоначальный осмотр": {
                                        startActivity(new Intent(Activity_qr_scaner.this, Activity_first_checking.class));
                                    }
                                    case "Ежегодный осмотр": {
                                        startActivity(new Intent(Activity_qr_scaner.this, Activity_everyyear_checking.class));
                                    }
                                    case "Ежеквартальный осмотр": {
                                        startActivity(new Intent(Activity_qr_scaner.this, Activity_every_cvartal_checking.class));
                                    }
                                    case "Обслуживание": {
                                        dialog_service_starter();
                                    }
                                    case "Заправка": {
                                        dialog_refile_starter();
                                    }
                                    case "Утилизация": {
                                        dialog_util_starter();
                                    }
                                }
                            } else if (ownership[0].equals("0") && ownership[1].equals("1")) {
                                dialog_error_used_user_starter();
                            } else if (ownership[0].equals("0") && ownership[1].equals("0")) {
                                dialog_error_used_dif_starter();
                            }
                            ownership_json_reader.close();
                            ownership_connection.disconnect();
                        } else {

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


