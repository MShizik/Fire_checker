package com.example.fire_checker;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {
    Button login_btn;
    TextView main_text_view;
    EditText login_obj, password_obj;
    Integer login_and_password_checker = 0;
    public static String login, password;
    public static String token;
    public static String notificationToken;
    ConstraintLayout progress_layout_obj;
    ProgressBar main_progress_bar;
    public static String on_refile = "На перезаправке";
    public static String on_repair = "В ремонте";
    public static String expluatation = "В эксплуатации";
    public static String utilization = "Утилизация";
    public static String service = "Обслуживание";

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    Toast.makeText(MainActivity.this, "permission granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "permission not granted", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseMessaging.getInstance().getToken()
        .addOnSuccessListener(new OnSuccessListener<String>() {
            @Override
            public void onSuccess(String token) {
                notificationToken = token;
                Log.d("FCM Token", "Token: " + token);
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("FCM Token", "Failed to get token: " + e.getMessage());
            }
        });

        String permissionCamera = Manifest.permission.CAMERA;
        String permissionNotifications = Manifest.permission.POST_NOTIFICATIONS;
        int grantCamera = ContextCompat.checkSelfPermission(this, permissionCamera);
        int grantNotifications =  ContextCompat.checkSelfPermission(this, permissionNotifications);
        if (grantCamera != PackageManager.PERMISSION_GRANTED && grantNotifications != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA},1);
            if (Build.VERSION.SDK_INT >= 33) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                    //ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.POST_NOTIFICATIONS},101);
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
                }
            }
        }

        login_btn = findViewById(R.id.login_btn);
        login_obj = findViewById(R.id.main_login_input_field);
        password_obj = findViewById(R.id.main_password_input_field);
        main_text_view = findViewById(R.id.main_login_conditions_field);
        progress_layout_obj=findViewById(R.id.progress_layout);
        main_progress_bar=findViewById(R.id.main_progress_bar);
        progress_layout_obj.setVisibility(View.GONE);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login = login_obj.getText().toString();
                password = password_obj.getText().toString();
                auth_request authorization =new auth_request();
                authorization.execute();
            }
        });


    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "permission granted", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(MainActivity.this, "permission not granted", Toast.LENGTH_SHORT).show();
            }
        }

    }




    public class auth_request extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress_layout_obj.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String POST_PARAMS = "{\"email\":\"" + login + "\",\"password\":\"" + password + "\",\"notification_token\":" + notificationToken + "\"}";
            URL login_endpoint = null;
            try {
                login_endpoint = new URL("http://194.67.55.58:8080/api/login");
                try {
                    HttpURLConnection login_connection = (HttpURLConnection) login_endpoint.openConnection();
                    login_connection.setRequestMethod("POST");
                    login_connection.setRequestProperty("Content-type", "application/json");

                    login_connection.setDoOutput(true);
                    OutputStream os = login_connection.getOutputStream();
                    os.write(POST_PARAMS.getBytes());
                    os.flush();
                    os.close();

                    if (login_connection.getResponseCode() == 200) {
                        InputStream login_response = login_connection.getInputStream();
                        InputStreamReader login_response_reader = new InputStreamReader(login_response, StandardCharsets.UTF_8);
                        JsonReader login_json_reader = new JsonReader(login_response_reader);
                        login_json_reader.beginObject();
                        while (login_json_reader.hasNext()) {
                            String key = login_json_reader.nextName();
                            if (key.equals("token")) {

                                String value = login_json_reader.nextString();
                                System.out.println(value);
                                token = value;
                                break;
                            } else {
                                login_json_reader.skipValue();
                            }
                        }
                        login_json_reader.close();
                        login_connection.disconnect();
                        startActivity(new Intent(MainActivity.this, Activity_type_choser.class));
                    } else {
                        login_and_password_checker = 1;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    login_and_password_checker = 2;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
                login_and_password_checker = 2;
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid){
            super.onPostExecute(aVoid);
            progress_layout_obj.setVisibility(View.GONE);
            if (login_and_password_checker == 1) {
                main_text_view.setTextColor(Color.rgb(237, 32, 36));
                main_text_view.setText("Неправильный логин или пароль");
                main_text_view.setTextSize(30);
                login_obj.setText(null);
                login_obj.setHint("Логин");
                password_obj.setText(null);
                password_obj.setHint("Пароль");

            }
            else if (login_and_password_checker == 2){
                main_text_view.setTextColor(Color.rgb(237, 32, 36));
                main_text_view.setText("Отсутсвует соеденение с сервером");
                main_text_view.setTextSize(30);
                login_obj.setText(null);
                login_obj.setHint("Логин");
                password_obj.setText(null);
                password_obj.setHint("Пароль");
            }
        }
    }
}