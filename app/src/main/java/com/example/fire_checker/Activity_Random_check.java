package com.example.fire_checker;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.JsonReader;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Scanner;

public class Activity_Random_check extends AppCompatActivity {

    TextView random_sn, random_next_check_date, random_next_refile_date;
    Button random_start_check_btn;
    ImageButton home_btn;
    Spinner random_type_choser_obj;
    Integer dialog_service_condition_problems_results, dialog_on_service_condition_problems_results;
    String type, serial_number, value, service_chosen_type;
    String random_next_check_date_txt = "Следующая проверка:\n", random_next_refile_date_txt = "Следущая перезаправка:\n";
    final int[] res = new int[1];
    final int[] check = new int[1];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_check);

        random_sn = findViewById(R.id.random_fire_sn);
        random_next_check_date = findViewById(R.id.random_next_check_date);
        random_next_refile_date = findViewById(R.id.random_next_refile_date);
        random_start_check_btn = findViewById(R.id.random_start_checking_btn);
        random_type_choser_obj = findViewById(R.id.random_type_choser_field);
        home_btn = findViewById(R.id.random_home_btn);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        random_sn.setText("Серийный номер:\n" + Activity_qr_scaner.serial_number);

        SpannableString content = new SpannableString(random_next_check_date_txt);
        content.setSpan(new UnderlineSpan(), 0, random_next_check_date_txt.length(), 0);
        random_next_check_date.setText(content);
        random_next_check_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_Random_check.this, Activity_every_cvartal_checking.class));
            }
        });

        content = new SpannableString(random_next_refile_date_txt);
        content.setSpan(new UnderlineSpan(), 0, random_next_refile_date_txt.length(), 0);
        random_next_refile_date.setText(content);
        random_next_refile_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_refile_starter(MainActivity.expluatation);
            }
        });
        findViewById(R.id.random_progress_layout).setVisibility(View.GONE);


        ArrayAdapter<?> types_adapter =ArrayAdapter.createFromResource(this, R.array.types, android.R.layout.simple_spinner_item);
        types_adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_main);
        random_type_choser_obj.setAdapter(types_adapter);
        random_type_choser_obj.setSelection(0);

        serial_number = Activity_qr_scaner.serial_number;

        random_type_choser_obj.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String[] type =getResources().getStringArray(R.array.types);
                Activity_type_choser.chosen_type = type[i].toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        random_start_check_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (Activity_type_choser.chosen_type) {
                    case "Ежегодный осмотр": {
                        startActivity(new Intent(Activity_Random_check.this, Activity_everyyear_checking.class));
                        break;
                    }
                    case "Ежеквартальный осмотр": {
                        startActivity(new Intent(Activity_Random_check.this, Activity_every_cvartal_checking.class));
                        break;
                    }
                    case "Обслуживание": {
                        dialog_service_starter(MainActivity.expluatation);
                        break;
                    }
                    case "Заправка": {
                        dialog_refile_starter(MainActivity.on_refile);
                        break;
                    }
                    case "Утилизация": {
                        dialog_util_starter();
                        break;
                    }
                }


            }
        });

        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_Random_check.this, Activity_type_choser.class));
            }
        });
    }

    protected void dialog_util_starter() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Dialog dialog_util = new Dialog(Activity_Random_check.this);
                dialog_util.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog_util.setContentView(R.layout.dialog_utilization_confirmation);
                Button dialog_util_confirmation_btn = (Button) dialog_util.findViewById(R.id.dialog_utilization_util_btn);
                dialog_util_confirmation_btn.setOnClickListener(v -> {
                    dialog_util.dismiss();
                    Date current_date = new Date();
                    type = MainActivity.utilization;
                    Activity_Random_check.set_status_request set_status = new Activity_Random_check.set_status_request();
                    set_status.execute();
                });
                dialog_util.setCancelable(true);
                dialog_util.show();
            }
        });
    }

    protected void dialog_refile_starter(String status) {
        if (status.equals(MainActivity.on_refile)) {
            //Диалоговое окно для принятия с заправки
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Dialog dialog_get_from_refile = new Dialog(Activity_Random_check.this);
                    dialog_get_from_refile.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog_get_from_refile.setContentView(R.layout.dialog_get_from_refile);
                    Button dialog_get_from_refile_confirmation_btn = (Button) dialog_get_from_refile.findViewById(R.id.dialog_get_from_refile_btn);
                    dialog_get_from_refile_confirmation_btn.setOnClickListener(v -> {
                        dialog_get_from_refile.dismiss();
                        Date current_date = new Date();
                        type = MainActivity.expluatation;
                        Activity_Random_check.set_status_request set_status = new Activity_Random_check.set_status_request();
                        set_status.execute();

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
                    Dialog dialog_send_to_refile = new Dialog(Activity_Random_check.this);
                    dialog_send_to_refile.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog_send_to_refile.setContentView(R.layout.dialog_send_to_refile);
                    Button dialog_send_to_refile_confirmation_btn = (Button) dialog_send_to_refile.findViewById(R.id.dialog_send_to_refile_btn);
                    dialog_send_to_refile_confirmation_btn.setOnClickListener(v -> {
                        dialog_send_to_refile.dismiss();
                        type = MainActivity.on_refile;
                        Activity_Random_check.set_status_request set_status = new Activity_Random_check.set_status_request();
                        set_status.execute();
                    });
                    dialog_send_to_refile.setCancelable(false);
                    dialog_send_to_refile.show();
                }
            });
        }
    }

    protected void dialog_service_starter(String status) {

        if (status.equals(MainActivity.on_refile) || status.equals(MainActivity.on_repair)) {
            //Диалоговое окно для закрытия обслуживания
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Dialog dialog_service = new Dialog(Activity_Random_check.this);
                    dialog_service.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog_service.setContentView(R.layout.dialog_on_service);
                    Spinner dialog_service_obj = (Spinner) dialog_service.findViewById(R.id.dialog_on_service_type_choser_field);
                    ArrayAdapter<?> types_adapter = ArrayAdapter.createFromResource(Activity_Random_check.this, R.array.types_on_service, android.R.layout.simple_spinner_item);
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
                        } else if (service_chosen_type.equals("Принять с ремонта")) {
                            type = MainActivity.expluatation;
                        } else if (service_chosen_type.equals("Вывести из эксплуатации")) {
                            type = MainActivity.utilization;
                        }
                        Activity_Random_check.set_status_request set_status = new Activity_Random_check.set_status_request();
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
                            String[] type = getResources().getStringArray(R.array.types_service_review);
                            service_chosen_type = type[i].toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            service_chosen_type=MainActivity.expluatation;
                        }
                    });

                    dialog_service.show();
                }
            });
        } else {
            //Диалоговое окно для открытия обслуживания
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Dialog dialog_on_service = new Dialog(Activity_Random_check.this);
                    dialog_on_service.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog_on_service.setContentView(R.layout.dialog_on_service);
                    Spinner dialog_on_service_obj = (Spinner) dialog_on_service.findViewById(R.id.dialog_on_service_type_choser_field);
                    ArrayAdapter<?> types_adapter = ArrayAdapter.createFromResource(Activity_Random_check.this, R.array.types_service_review, android.R.layout.simple_spinner_item);
                    Button dialog_on_service_send_btn = (Button) dialog_on_service.findViewById(R.id.dialog_on_service_type_choser_btn);
                    CheckBox dialog_on_service_condition_problems = (CheckBox) dialog_on_service.findViewById(R.id.dialog_on_service_condition_check_box);

                    types_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dialog_on_service_obj.setAdapter(types_adapter);
                    dialog_on_service_obj.setSelection(0);
                    dialog_on_service.setCancelable(true);

                    dialog_on_service_send_btn.setOnClickListener(v -> {
                        Date current_date = new Date();
                        type = MainActivity.on_refile;
                        if (service_chosen_type.equals("Отправить на заправку")) {
                            type = MainActivity.on_refile;
                        } else if (service_chosen_type.equals("Отправить на ремонт")) {
                            type = MainActivity.on_repair;
                        } else if (service_chosen_type.equals("Вывести из эксплуатации")) {
                            type = MainActivity.utilization;
                        }
                        dialog_on_service.dismiss();
                        Activity_Random_check.set_status_request set_status = new Activity_Random_check.set_status_request();
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
                            String[] type = getResources().getStringArray(R.array.types_on_service);
                            service_chosen_type = type[i].toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            service_chosen_type=MainActivity.on_refile;
                        }
                    });

                    dialog_on_service.show();
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
            findViewById(R.id.random_progress_layout).setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String set_status_post_params = "fire_id=" + serial_number + "&date=" + new Date() + "&type=" + type;
            String PROPERTY_AUTH = "Bearer " + MainActivity.token;
            System.out.println(type);
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
                        res[0] = 1;
                        set_status_json_reader.beginObject();
                        while (set_status_json_reader.hasNext()) {
                            String key = set_status_json_reader.nextName();
                            if (key.equals("result")) {
                                value = set_status_json_reader.nextString();
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
            findViewById(R.id.random_progress_layout).setVisibility(View.GONE);
            if (check[0] == 1 && res[0] == 1) {
                System.out.println("Everything is ok");
                Activity_qr_scaner.serial_number = "";
                Activity_type_choser.chosen_type = "";
                startActivity(new Intent(Activity_Random_check.this, Activity_type_choser.class));
            } else {
                api_error result_error = new api_error();
                result_error.dialog_api_error_starter(Activity_Random_check.this);
            }
        }
    }

    public class get_status_request extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            res[0] = 0;
            check[0] = 0;
            findViewById(R.id.random_progress_layout).setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String PROPERTY_AUTH = "Bearer " + MainActivity.token;
            URL get_status_endpoint = null;
            try {

                get_status_endpoint = new URL("http://194.67.55.58:8080/api/getStatus?fire_id=" + serial_number);
                try {
                    HttpURLConnection get_status_connection = (HttpURLConnection) get_status_endpoint.openConnection();
                    get_status_connection.setRequestMethod("GET");
                    get_status_connection.setRequestProperty("Authorization", PROPERTY_AUTH);
                    if (get_status_connection.getResponseCode() == 200) {
                        res[0] = 1;
                        String inline = "";
                        Scanner scanner = new Scanner(get_status_endpoint.openStream());
                        InputStream get_status_input = get_status_connection.getInputStream();
                        InputStreamReader get_status_input_reader = new InputStreamReader(get_status_input, StandardCharsets.UTF_8);
                        JsonReader get_status_json_reader = new JsonReader(get_status_input_reader);
                        get_status_json_reader.beginObject();
                        while (get_status_json_reader.hasNext()) {
                            String key = get_status_json_reader.nextName();
                            if (key.equals("current_status")) {
                                check[0] = 1;
                                value = get_status_json_reader.nextString();
                                break;
                            } else {
                                get_status_json_reader.skipValue();
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
            findViewById(R.id.random_progress_layout).setVisibility(View.GONE);

            if (check[0] == 1 && res[0] == 1) {
                System.out.println(value);
                if (Activity_type_choser.chosen_type.equals("Обслуживание")) {

                    dialog_service_starter(value);
                } else {
                    dialog_refile_starter(value);
                }
            }
            else {
                api_error result_error = new api_error();
                result_error.dialog_api_error_starter(Activity_Random_check.this);
            }
        }
    }
}