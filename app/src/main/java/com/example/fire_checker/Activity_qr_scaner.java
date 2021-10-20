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



        qr_code_scanner_view=findViewById(R.id.qr_scanner);
        qr_code_scanner=new CodeScanner(this, qr_code_scanner_view);

        qr_code_scanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                serial_number=result.getText().toString();
                System.out.println("каждый раз как первый");
                if (Activity_type_choser.chosen_type.equals("Первоначальный осмотр")){
                    startActivity(new Intent(Activity_qr_scaner.this, Activity_first_checking.class));
                }
                else if(Activity_type_choser.chosen_type.equals("Ежегодный осмотр")){
                    startActivity(new Intent(Activity_qr_scaner.this, Activity_everyyear_checking.class ));
                }
                else if(Activity_type_choser.chosen_type.equals("Ежеквартальный осмотр")) {
                    startActivity(new Intent(Activity_qr_scaner.this, Activity_every_cvartal_checking.class));
                }
                else if (Activity_type_choser.chosen_type.equals("Обслуживание")) {
                    startActivity(new Intent(Activity_qr_scaner.this, Activity_service.class));
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

}