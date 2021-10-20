package com.example.fire_checker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

public class Activity_qr_scaner extends AppCompatActivity {
    CodeScanner qr_code_scanner;
    CodeScannerView qr_code_scanner_view;

    public static String serial_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scaner);

        String permission = Manifest.permission.CAMERA;
        int grant = ContextCompat.checkSelfPermission(this, permission);
        if (grant != PackageManager.PERMISSION_GRANTED) {
            String[] permission_list = new String[1];
            permission_list[0] = permission;
            ActivityCompat.requestPermissions(this, permission_list, 1);
        }

        qr_code_scanner_view=findViewById(R.id.qr_scanner);
        qr_code_scanner=new CodeScanner(this, qr_code_scanner_view);

        qr_code_scanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        serial_number=result.getText().toString();
                        Toast.makeText(Activity_qr_scaner.this,Activity_type_choser.chosen_type, Toast.LENGTH_SHORT).show();
                    }
                });

                switch (Activity_type_choser.chosen_type){

                    case "Первоначальный осмотр":
                        startActivity(new Intent(Activity_qr_scaner.this, Activity_first_checking.class));
                        System.out.println(Activity_type_choser.chosen_type+"переход выполнен");
                    case "Ежегодный осмотр":
                        startActivity(new Intent(Activity_qr_scaner.this, Activity_everyyear_checking.class ));
                        System.out.println(Activity_type_choser.chosen_type+"переход выполнен");
                    case "Ежеквартальный осмотр":
                        startActivity(new Intent(Activity_qr_scaner.this, Activity_every_cvartal_checking.class));
                        System.out.println(Activity_type_choser.chosen_type+"переход выполнен");
                    case "Обслуживание":
                        startActivity(new Intent(Activity_qr_scaner.this, Activity_service.class));
                        System.out.println(Activity_type_choser.chosen_type+"переход выполнен");
                }
            }
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
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(Activity_qr_scaner.this,"permission granted", Toast.LENGTH_SHORT).show();
                // perform your action here

            } else {
                Toast.makeText(Activity_qr_scaner.this,"permission not granted", Toast.LENGTH_SHORT).show();
            }
        }

    }
}