package com.example.fire_checker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
                else if (Activity_type_choser.chosen_type.equals("Заправка")){

                    //Диалоговое окно для отправки на заправку
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Dialog dialog_send_to_refile = new Dialog(Activity_qr_scaner.this);
                            dialog_send_to_refile.setContentView(R.layout.dialog_send_to_refile);
                            Button dialog_send_to_refile_confirmation_btn= (Button)dialog_send_to_refile.findViewById(R.id.dialog_send_to_refile_btn);
                            dialog_send_to_refile_confirmation_btn.setOnClickListener(v->{
                                dialog_send_to_refile.dismiss();
                                serial_number="";
                                Activity_type_choser.chosen_type="";
                                startActivity(new Intent(Activity_qr_scaner.this, Activity_type_choser.class));
                            });
                            dialog_send_to_refile.setCancelable(false);
                            dialog_send_to_refile.show();
                        }
                    });

                    //Диалоговое окно для принятия с заправки
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Dialog dialog_get_from_refile = new Dialog(Activity_qr_scaner.this);
                            dialog_get_from_refile.setContentView(R.layout.dialog_get_from_refile);
                            Button dialog_get_from_refile_confirmation_btn= (Button)dialog_get_from_refile.findViewById(R.id.dialog_get_from_refile_btn);
                            dialog_get_from_refile_confirmation_btn.setOnClickListener(v->{
                                dialog_get_from_refile.dismiss();
                                serial_number="";
                                Activity_type_choser.chosen_type="";
                                startActivity(new Intent(Activity_qr_scaner.this, Activity_type_choser.class));
                            });
                            dialog_get_from_refile.setCancelable(false);
                            dialog_get_from_refile.show();
                        }
                    });

                }
                else if (Activity_type_choser.chosen_type.equals("Утилизация")){
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Dialog dialog_util = new Dialog(Activity_qr_scaner.this);
                            dialog_util.setContentView(R.layout.dialog_utilization_confirmation);
                            Button dialog_util_confirmation_btn= (Button)dialog_util.findViewById(R.id.dialog_utilization_util_btn);
                            dialog_util_confirmation_btn.setOnClickListener(v->{
                                dialog_util.dismiss();
                                serial_number="";
                                Activity_type_choser.chosen_type="";
                                startActivity(new Intent(Activity_qr_scaner.this, Activity_type_choser.class));
                            });
                            dialog_util.setCancelable(false);
                            dialog_util.show();
                        }
                    });
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