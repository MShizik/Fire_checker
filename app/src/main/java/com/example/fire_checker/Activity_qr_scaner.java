package com.example.fire_checker;

import static android.view.View.GONE;

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
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

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
import java.util.Locale;
import java.util.Scanner;

public class Activity_qr_scaner extends AppCompatActivity {
    CodeScanner qr_code_scanner;
    CodeScannerView qr_code_scanner_view;
    ImageButton scaner_home_btn;
    ConstraintLayout scaner_layout_obj;
    ProgressBar scaner_progres_bar;
    String type = "";
    String value = "";
    String value_2 = "";
    final int[] res = new int[1];
    final int[] check = new int[1];
    final int[] result = new int[2];
    final String[] ownership = new String[2];
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
        findViewById(R.id.scaner_progress_layout).setVisibility(GONE);
        scaner_progres_bar = findViewById(R.id.scaner_progress_bar);
        scaner_layout_obj = findViewById(R.id.scaner_progress_layout);
        serial_number = "";
        qr_code_scanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                if (!serial_number.equals(result.getText().toString())){
                    serial_number = result.getText().toString();

                    ownership_request ownership_check = new ownership_request();
                    ownership_check.execute();
                }

            }
        });
        qr_code_scanner.startPreview();


        scaner_home_btn.setOnClickListener(v -> {
            Activity_type_choser.chosen_type = "";
            startActivity(new Intent(Activity_qr_scaner.this, Activity_type_choser.class));
        });
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
                    Activity_type_choser.chosen_type = "";
                    startActivity(new Intent(Activity_qr_scaner.this, Activity_type_choser.class));
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
                    Activity_type_choser.chosen_type = "";
                    startActivity(new Intent(Activity_qr_scaner.this, Activity_type_choser.class));
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
                    Activity_type_choser.chosen_type = "";
                    startActivity(new Intent(Activity_qr_scaner.this, Activity_type_choser.class));
                });
                dialog_error_used_dif.setCancelable(false);
                dialog_error_used_dif.show();
            }
        });
    }

    protected void dialog_error_not_connected_starter() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Dialog dialog_error_not_connected = new Dialog(Activity_qr_scaner.this);
                dialog_error_not_connected.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
                dialog_error_not_connected.setContentView(R.layout.dialog_error_not_connected);
                Button dialog_error_not_connected_btn = (Button) dialog_error_not_connected.findViewById(R.id.dialog_error_not_connected_back_btn);
                dialog_error_not_connected_btn.setOnClickListener(v -> {
                    dialog_error_not_connected.dismiss();
                    serial_number = "";
                    Activity_type_choser.chosen_type = "";
                    startActivity(new Intent(Activity_qr_scaner.this, Activity_type_choser.class));
                });
                dialog_error_not_connected.setCancelable(true);
                dialog_error_not_connected.show();
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
                    dialog_util.dismiss();
                    Date current_date = new Date();
                    type = MainActivity.utilization;
                    set_status_request set_status = new set_status_request();
                    set_status.execute();
                });
                dialog_util.setCancelable(true);
                dialog_util.show();
            }
        });
    }

    protected void dialog_refile_starter(String status) {
        if (status.equals(MainActivity.on_refile) || status.equals(MainActivity.service)) {
            //Диалоговое окно для принятия с заправки
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Dialog dialog_get_from_refile = new Dialog(Activity_qr_scaner.this);
                    dialog_get_from_refile.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog_get_from_refile.setContentView(R.layout.dialog_get_from_refile);
                    Button dialog_get_from_refile_confirmation_btn = (Button) dialog_get_from_refile.findViewById(R.id.dialog_get_from_refile_btn);
                    dialog_get_from_refile_confirmation_btn.setOnClickListener(v -> {
                        dialog_get_from_refile.dismiss();
                        Date current_date = new Date();
                        type = MainActivity.expluatation;
                        fromRechargeRequest rechargeRequest = new fromRechargeRequest(Activity_qr_scaner.this);
                        rechargeRequest.execute();

                    });
                    dialog_get_from_refile.setCancelable(true);
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
                        dialog_send_to_refile.dismiss();
                        type = MainActivity.on_refile;
                        toRechargeRequest rechargeRequest = new toRechargeRequest(Activity_qr_scaner.this);
                        rechargeRequest.execute();
                    });
                    dialog_send_to_refile.setCancelable(false);
                    dialog_send_to_refile.show();
                }
            });
        }
    }

    protected void dialog_service_starter(String status) {

        if (status.equals(MainActivity.on_refile) || status.equals(MainActivity.on_repair) || status.equals(MainActivity.service)) {
            //Диалоговое окно для закрытия обслуживания
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Dialog dialog_service = new Dialog(Activity_qr_scaner.this);
                    dialog_service.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog_service.setContentView(R.layout.dialog_on_service);
                    Spinner dialog_service_obj = (Spinner) dialog_service.findViewById(R.id.dialog_on_service_type_choser_field);
                    ArrayAdapter<?> types_adapter = ArrayAdapter.createFromResource(Activity_qr_scaner.this, R.array.types_on_service, android.R.layout.simple_spinner_item);
                    Button dialog_service_send_btn = (Button) dialog_service.findViewById(R.id.dialog_on_service_type_choser_btn);
                    CheckBox dialog_service_condition_problems = (CheckBox) dialog_service.findViewById(R.id.dialog_on_service_condition_check_box);

                    types_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dialog_service_obj.setAdapter(types_adapter);
                    dialog_service_obj.setSelection(0);
                    dialog_service.setCancelable(true);
                    dialog_service.dismiss();
                    dialog_service_send_btn.setOnClickListener(v -> {
                        Date current_date = new Date();
                        if (service_chosen_type.equals("Принять с заправки")) {
                            type = MainActivity.expluatation;
                            fromRechargeRequest rechargeRequest = new fromRechargeRequest(Activity_qr_scaner.this);
                            rechargeRequest.execute();
                        } else if (service_chosen_type.equals("Принять с ремонта")) {
                            type = MainActivity.expluatation;
                        } else if (service_chosen_type.equals("Вывести из эксплуатации")) {
                            type = MainActivity.utilization;
                        }
                        dialog_service.dismiss();
                        set_status_request set_status = new set_status_request();
                        set_status.execute();

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
                            String[] type = getResources().getStringArray(R.array.types_on_service);
                            service_chosen_type = type[i].toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            service_chosen_type=MainActivity.expluatation;
                        }
                    });
                    if (!dialog_service.isShowing()) {
                        dialog_service.show();
                    }
                }
            });
        } else {
            //Диалоговое окно для открытия обслуживания
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
                    dialog_on_service.setCancelable(true);

                    dialog_on_service_send_btn.setOnClickListener(v -> {
                        Date current_date = new Date();
                        if (service_chosen_type.equals("Отправить на заправку")) {
                            type = MainActivity.on_refile;
                            toRechargeRequest rechargeRequest = new toRechargeRequest(Activity_qr_scaner.this);
                            rechargeRequest.execute();
                        } else if (service_chosen_type.equals("Отправить на ремонт")) {
                            type = MainActivity.on_repair;
                        } else if (service_chosen_type.equals("Вывести из эксплуатации")) {
                            type = MainActivity.utilization;
                        }
                        else{
                            type = MainActivity.on_repair;
                        }
                        dialog_on_service.dismiss();
                        set_status_request set_status = new set_status_request();
                        set_status.execute();
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
                            String[] type = getResources().getStringArray(R.array.types_service_review);
                            service_chosen_type = type[i].toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            service_chosen_type=MainActivity.on_refile;
                        }
                    });

                    if (!dialog_on_service.isShowing()) {
                        dialog_on_service.show();
                    }
                }
            });
        }

    }

    public class set_status_request extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            res[0] = 0;
            check[0] = 0;
            findViewById(R.id.scaner_progress_layout).setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String set_status_post_params = "fire_id=" + serial_number + "&date=" + new Date() + "&type=" + type;
            String PROPERTY_AUTH = "Bearer " + MainActivity.token;

            URL set_status_endpoint = null;
            try{
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
                    System.out.println(set_status_connection.getResponseCode() + "Response code");
                    if (set_status_connection.getResponseCode() == 200) {
                        InputStream set_status_response = set_status_connection.getInputStream();
                        InputStreamReader set_status_response_reader = new InputStreamReader(set_status_response, StandardCharsets.UTF_8);
                        JsonReader set_status_json_reader = new JsonReader(set_status_response_reader);
                        res[0] = 1;
                        set_status_json_reader.beginObject();
                        while (set_status_json_reader.hasNext()) {
                            String key = set_status_json_reader.nextName();
                            System.out.println(key + "Key");
                            if (key.equals("result")) {
                                value = set_status_json_reader.nextString();
                                System.out.println(value + "Value");
                                if (value.equals("success")) {
                                    check[0] = 1;
                                } else {
                                    check[0] = 0;
                                }
                            } else {
                                set_status_json_reader.skipValue();
                            }
                        }
                        set_status_json_reader.close();
                        set_status_connection.disconnect();
                    } else {
                        res[0] = 0;
                    }
                } catch (IOException e) {
                    res[0] = 0;
                    e.printStackTrace();
                }
            } catch (MalformedURLException e) {
                res[0] = 0;
                e.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            findViewById(R.id.scaner_progress_layout).setVisibility(View.GONE);
            if (check[0] == 1 && res[0] == 1) {
                System.out.println("Everything is ok");
                Activity_qr_scaner.serial_number = "";
                Activity_type_choser.chosen_type = "";
                startActivity(new Intent(Activity_qr_scaner.this, Activity_type_choser.class));
            } else {
                api_error result_error = new api_error();
                result_error.dialog_api_error_starter(Activity_qr_scaner.this);
            }
        }
    }


    public class ownership_request extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            result[0] = -1;
            ownership[0] = "-1";
            ownership[1] = "-1";
            runOnUiThread(() -> findViewById(R.id.scaner_progress_layout).setVisibility(View.VISIBLE));
        }

        @Override
        protected Void doInBackground(Void... voids) {
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
                        System.out.println(ownership_response.toString());
                        InputStreamReader ownership_response_reader = new InputStreamReader(ownership_response, StandardCharsets.UTF_8);
                        JsonReader ownership_json_reader = new JsonReader(ownership_response_reader);
                        ownership_json_reader.beginObject();
                        while (ownership_json_reader.hasNext()) {
                            String key = ownership_json_reader.nextName();
                            if (key.equals("result")) {
                                if (!ownership_json_reader.nextString().equals("success")) {
                                    result[0] = 0;
                                } else {
                                    result[0] = 1;
                                }
                            } else if (key.equals("data")) {
                                ownership_json_reader.beginObject();
                                while (ownership_json_reader.hasNext()) {
                                    String key_2 = ownership_json_reader.nextName();

                                    if (key_2.equals("isFree")) {
                                        ownership[0] = ownership_json_reader.nextString();

                                    } else if (key_2.equals("userFire")) {
                                        ownership[1] = ownership_json_reader.nextString();

                                    } else {
                                        ownership_json_reader.skipValue();
                                    }
                                }
                            } else {
                                ownership_json_reader.skipValue();
                            }
                        }
                        ownership_response.close();
                        ownership_response_reader.close();
                        ownership_json_reader.close();
                        ownership_connection.disconnect();
                    } else {
                        result[0] = -1;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (
                    MalformedURLException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            findViewById(R.id.scaner_progress_layout).setVisibility(GONE);
            if (result[0] == 0) {
                dialog_error_existance_starter();
            } else if (ownership[0].equals("1") && ownership[1].equals("0") && Activity_type_choser.chosen_type.equals("Первоначальный осмотр")) {
                startActivity(new Intent(Activity_qr_scaner.this, Activity_first_checking.class));

            } else if (ownership[0].equals("0") && ownership[1].equals("1") && Activity_type_choser.chosen_type.equals("Первоначальный осмотр")) {
                Activity_qr_scaner.this.dialog_error_used_user_starter();
            } else if (ownership[0].equals("1") && ownership[1].equals("1") && !Activity_type_choser.chosen_type.equals("Первоначальный осмотр")) {
                Activity_qr_scaner.this.dialog_error_not_connected_starter();
            } else if (ownership[0].equals("0") && ownership[1].equals("1") && !Activity_type_choser.chosen_type.equals("Первоначальный осмотр")) {
                switch (Activity_type_choser.chosen_type) {
                    case "Ежегодный осмотр": {
                        startActivity(new Intent(Activity_qr_scaner.this, Activity_everyyear_checking.class));
                        break;
                    }
                    case "Ежеквартальный осмотр": {
                        startActivity(new Intent(Activity_qr_scaner.this, Activity_every_cvartal_checking.class));
                        break;
                    }
                    case "Обслуживание": {
                        System.out.println("Обслуживание");
                        get_data_request get_data = new get_data_request();
                        get_data.execute();
                        //get_status_request get_status = new get_status_request();
                        //get_status.execute();
                        //dialog_service_starter(MainActivity.expluatation);
                        break;
                    }
                    case "Заправка": {
                        System.out.println("Заправка");
                        get_data_request get_data = new get_data_request();
                        get_data.execute();
                        //get_status_request get_status = new get_status_request();
                        //get_status.execute();
                        //dialog_refile_starter(MainActivity.on_refile);
                        break;
                    }
                    case "Утилизация": {
                        dialog_util_starter();
                        break;
                    }
                    case "Random_check":{
                        startActivity(new Intent(Activity_qr_scaner.this, Activity_Random_check.class));
                        break;
                    }
                }

            } else if (ownership[0].equals("0") && ownership[1].equals("0")) {
                dialog_error_used_dif_starter();
            }
            else{
                api_error result_error = new api_error();
                result_error.dialog_api_error_starter(Activity_qr_scaner.this);
            }
            qr_code_scanner.startPreview();
        }
    }

    public class get_data_request extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            value = "";
            res[0] = 0;
            check[0] = 0;
            findViewById(R.id.scaner_progress_layout).setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String PROPERTY_AUTH = "Bearer " + MainActivity.token;
            URL get_data_endpoint = null;

            try {

                get_data_endpoint = new URL("http://194.67.55.58:8080/api/getExtinguisherData?fire_id=" + serial_number);
                try {
                    HttpURLConnection get_data_connection = (HttpURLConnection) get_data_endpoint.openConnection();
                    get_data_connection.setRequestMethod("GET");
                    get_data_connection.setRequestProperty("Authorization", PROPERTY_AUTH);
                    System.out.println(get_data_connection.getResponseCode() + "Response code");
                    if (get_data_connection.getResponseCode() == 200) {
                        res[0] = 1;
                        String inline = "";
                        Scanner scanner = new Scanner(get_data_endpoint.openStream());
                        InputStream get_data_input = get_data_connection.getInputStream();
                        InputStreamReader get_data_input_reader = new InputStreamReader(get_data_input, StandardCharsets.UTF_8);
                        JsonReader get_data_json_reader = new JsonReader(get_data_input_reader);
                        get_data_json_reader.beginObject();
                        while (get_data_json_reader.hasNext()) {
                            String key = get_data_json_reader.nextName();
                            if (key.equals("result")) {
                                if (!get_data_json_reader.nextString().equals("success")) {
                                    check[0] = 0;
                                } else {
                                    check[0] = 1;
                                }
                            } else if (key.equals("data")) {
                                get_data_json_reader.beginObject();
                                while (get_data_json_reader.hasNext()) {
                                    String key_2 = get_data_json_reader.nextName();
                                    System.out.println("Ключ_2"+ key_2);
                                    if (key_2.equals("status")){
                                        value = get_data_json_reader.nextString();
                                    }
                                    else {
                                        get_data_json_reader.skipValue();
                                    }
                                }
                            } else {
                                get_data_json_reader.skipValue();
                            }
                        }
                    } else {
                        res[0] = 0;
                    }
                } catch (IOException e) {
                    res[0] = 0;
                    e.printStackTrace();

                }
            } catch (MalformedURLException e) {
                res[0] = 0;
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            findViewById(R.id.scaner_progress_layout).setVisibility(View.GONE);

            if (check[0] == 1 && res[0] == 1) {
                System.out.println(value+"Значение");
                if (Activity_type_choser.chosen_type.equals("Обслуживание")){
                    dialog_service_starter(value);
                }
                else{
                    dialog_refile_starter(value);
                }
            }
            else {
                api_error result_error = new api_error();
                result_error.dialog_api_error_starter(Activity_qr_scaner.this);
            }
        }
    }

}