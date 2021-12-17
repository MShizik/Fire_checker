package com.example.fire_checker;

import android.annotation.SuppressLint;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class Activity_qr_scaner extends AppCompatActivity {
    CodeScanner qr_code_scanner;
    CodeScannerView qr_code_scanner_view;
    AppCompatImageButton scaner_home_btn;

    public static String serial_number;
    public static String service_chosen_type;
    public static Integer dialog_service_condition_problems_results;
    public static Integer dialog_on_service_condition_problems_results;

    @SuppressLint("WrongViewCast")
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
                qr_code_scanner.startPreview();
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
                    String type = "";
                    Date current_date = new Date();
                    type = "decommissioned";
                    set_status_request(type, current_date, serial_number, MainActivity.token, dialog_util);
                });
                dialog_util.setCancelable(false);
                dialog_util.show();
            }
        });
    }

    protected void dialog_refile_starter(String status) {
        if (status.equals("onRefile")) {
            //Диалоговое окно для принятия с заправки
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Dialog dialog_get_from_refile = new Dialog(Activity_qr_scaner.this);
                    dialog_get_from_refile.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog_get_from_refile.setContentView(R.layout.dialog_get_from_refile);
                    Button dialog_get_from_refile_confirmation_btn = (Button) dialog_get_from_refile.findViewById(R.id.dialog_get_from_refile_btn);
                    dialog_get_from_refile_confirmation_btn.setOnClickListener(v -> {
                        String type = "";
                        Date current_date = new Date();
                        type = "refiled";
                        set_status_request(type, current_date, serial_number, MainActivity.token, dialog_get_from_refile);
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
                        String type = "";
                        Date current_date = new Date();
                        type = "onRefile";
                        set_status_request(type, current_date, serial_number, MainActivity.token, dialog_send_to_refile);
                    });
                    dialog_send_to_refile.setCancelable(false);
                    dialog_send_to_refile.show();
                }
            });
        }
    }

    protected void dialog_service_starter(String status) {

        if (status.equals("onRepair") || status.equals("onRefile")) {
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
                        String type = "";
                        Date current_date = new Date();
                        if (service_chosen_type.equals("Отправить на заправку")) {
                            type = "onRefile";
                        } else if (service_chosen_type.equals("Отправить на ремонт")) {
                            type = "onRepair";
                        } else if (service_chosen_type.equals("Вывести из эксплуатации")) {
                            type = "decommissioned";
                        }
                        set_status_request(type, current_date, serial_number, MainActivity.token, dialog_service);
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
                        String type = "";
                        Date current_date = new Date();
                        if (service_chosen_type.equals("Принять с заправки")) {
                            type = "refiled";
                        } else if (service_chosen_type.equals("Принять с ремонта")) {
                            type = "repaired";
                        } else if (service_chosen_type.equals("Вывести из эксплуатации")) {
                            type = "decommissioned";
                        }
                        set_status_request(type, current_date, serial_number, MainActivity.token, dialog_on_service);
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

    }


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
                                if (key.equals("result")) {
                                    if (!ownership_json_reader.nextString().equals("success")) {
                                        dialog_error_existance_starter();
                                    }
                                } else if (key.equals("data")) {
                                    ownership_json_reader.beginObject();
                                    while (ownership_json_reader.hasNext()) {
                                        String key_2 = ownership_json_reader.nextName();
                                        System.out.println(key_2);
                                        if (key_2.equals("isFree")) {
                                            ownership[0] = ownership_json_reader.nextString();
                                            System.out.println(ownership[0]);
                                        } else if (key_2.equals("userFire")) {
                                            ownership[1] = ownership_json_reader.nextString();
                                            System.out.println(ownership[1]);
                                        } else {
                                            ownership_json_reader.skipValue();
                                        }
                                    }
                                } else {
                                    ownership_json_reader.skipValue();
                                }
                            }
                            if (ownership[0].equals("1") && ownership[1].equals("1") && Activity_type_choser.chosen_type.equals("Первоначальный осмотр")) {
                                startActivity(new Intent(Activity_qr_scaner.this, Activity_first_checking.class));

                            } else if (ownership[0].equals("0") && ownership[1].equals("1") && Activity_type_choser.chosen_type.equals("Первоначальный осмотр")) {
                                dialog_error_used_user_starter();
                            } else if (ownership[0].equals("0") && ownership[1].equals("1") && !Activity_type_choser.chosen_type.equals("Первоначальный осмотр")) {
                                switch (Activity_type_choser.chosen_type) {
                                    case "Ежегодный осмотр": {
                                        startActivity(new Intent(Activity_qr_scaner.this, Activity_everyyear_checking.class));
                                    }
                                    case "Ежеквартальный осмотр": {
                                        startActivity(new Intent(Activity_qr_scaner.this, Activity_every_cvartal_checking.class));
                                    }
                                    case "Обслуживание": {
                                        get_status_request(serial_number, MainActivity.token, "Обслуживание");
                                    }
                                    case "Заправка": {
                                        get_status_request(serial_number, MainActivity.token, "Заправка");
                                    }
                                    case "Утилизация": {
                                        dialog_util_starter();
                                    }
                                }

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
                } catch (
                        MalformedURLException e) {
                    e.printStackTrace();
                }


            }
        });

    }

    protected void get_status_request(String token, String serial_number, String type) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                String PROPERTY_AUTH = "Bearer" + token;
                URL get_status_endpoint = null;
                try {
                    get_status_endpoint = new URL("http://194.67.55.58:8080/api/getStatus?fire_id=" + serial_number);
                    try {
                        HttpURLConnection get_status_connection = (HttpURLConnection) get_status_endpoint.openConnection();
                        get_status_connection.setRequestMethod("GET");
                        get_status_connection.setRequestProperty("Authorization", PROPERTY_AUTH);

                        if (get_status_connection.getResponseCode() == 200) {
                            InputStream get_status_input = get_status_connection.getInputStream();
                            InputStreamReader get_status_input_reader = new InputStreamReader(get_status_input, StandardCharsets.UTF_8);
                            JsonReader get_status_json_reader = new JsonReader(get_status_input_reader);
                            get_status_json_reader.beginObject();
                            while (get_status_json_reader.hasNext()) {
                                String key = get_status_json_reader.nextName();
                                System.out.println(key);
                                if (key.equals("current_status")) {
                                    String value = get_status_json_reader.nextString();
                                    System.out.println(value);
                                    if (type.equals("Обслуживание")) {
                                        dialog_service_starter(value);
                                    } else {
                                        dialog_refile_starter(value);
                                    }
                                    break;
                                }
                            }
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

    protected void set_status_request(String type, Date date, String serial_number, String token, Dialog main_dialog) {
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
                                System.out.println(key);
                                if (key.equals("result")) {
                                    String value = set_status_json_reader.nextString();
                                    System.out.println(value);
                                    if (value.equals("success")) {
                                        Activity_qr_scaner.serial_number = "";
                                        Activity_type_choser.chosen_type = "";
                                        main_dialog.dismiss();
                                        startActivity(new Intent(Activity_qr_scaner.this, Activity_type_choser.class));
                                    }
                                } else {
                                    set_status_json_reader.skipValue();
                                }
                            }
                            set_status_json_reader.close();
                            set_status_connection.disconnect();
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


