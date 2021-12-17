package com.example.fire_checker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    Integer login_and_password_checker=0;
    public static String login, password;
    public static String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String permission = Manifest.permission.CAMERA;
        int grant = ContextCompat.checkSelfPermission(this, permission);
        if (grant != PackageManager.PERMISSION_GRANTED) {
            String[] permission_list = new String[1];
            permission_list[0] = permission;
            ActivityCompat.requestPermissions(this, permission_list, 1);
        }

        login_btn=findViewById(R.id.login_btn);
        login_obj=findViewById(R.id.main_login_input_field);
        password_obj=findViewById(R.id.main_password_input_field);
        main_text_view=findViewById(R.id.main_login_conditions_field);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login = login_obj.getText().toString();
                password=password_obj.getText().toString();
                String POST_PARAMS="{\"email\":\""+login+"\",\"password\":\""+password+"\"}";
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        URL login_endpoint = null;
                        try {
                            login_endpoint = new URL("http://194.67.55.58:8080/api/login");
                            try {
                                HttpURLConnection login_connection =(HttpURLConnection) login_endpoint.openConnection();
                                login_connection.setRequestMethod("POST");
                                login_connection.setRequestProperty("Content-type","application/json");

                                login_connection.setDoOutput(true);
                                OutputStream os = login_connection.getOutputStream();
                                os.write(POST_PARAMS.getBytes());
                                os.flush();
                                os.close();

                               if (login_connection.getResponseCode() == 200){
                                    InputStream login_response=login_connection.getInputStream();
                                    InputStreamReader login_response_reader=new InputStreamReader(login_response, StandardCharsets.UTF_8);
                                    JsonReader login_json_reader = new JsonReader(login_response_reader);
                                    login_json_reader.beginObject();
                                    while (login_json_reader.hasNext()){
                                        String key = login_json_reader.nextName();
                                        if(key.equals("token")){

                                            String value = login_json_reader.nextString();
                                            System.out.println(value);
                                            token =value;
                                            break;
                                        }
                                        else{
                                            login_json_reader.skipValue();
                                        }
                                    }
                                    login_json_reader.close();
                                    login_connection.disconnect();
                                    startActivity(new Intent(MainActivity.this, Activity_type_choser.class));
                                     }
                                else{
                                    login_and_password_checker=1;


                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }




                    }
                    public void onProgressUpdate(){

                    }
                });
            if (login_and_password_checker==1){
                main_text_view.setTextColor(Color.rgb(237,32,36));
                main_text_view.setText("Неправильный логин или пароль");
                main_text_view.setTextSize(30);
                login_obj.setText(null);
                login_obj.setHint("Логин");
                password_obj.setText(null);
                password_obj.setHint("Пароль");

            }
            }
        });



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this,"permission granted", Toast.LENGTH_SHORT).show();


            } else {
                Toast.makeText(MainActivity.this,"permission not granted", Toast.LENGTH_SHORT).show();
            }
        }

    }
}