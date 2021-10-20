package com.example.fire_checker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    Button login_btn;
    EditText login_obj, password_obj;
    public static String login, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login_btn=findViewById(R.id.login_btn);
        login_obj=findViewById(R.id.main_login_input_field);
        password_obj=findViewById(R.id.main_password_input_field);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login = login_obj.getText().toString();
                password=password_obj.getText().toString();
                startActivity(new Intent(MainActivity.this, Activity_type_choser.class));
            }
        });

    }
}