package com.example.fire_checker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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

public class Activity_qr_scaner extends AppCompatActivity {
    CodeScanner qr_code_scanner;
    CodeScannerView qr_code_scanner_view;

    public static String serial_number;
    public static String service_chosen_type;
    public static Integer dialog_service_condition_problems_results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scaner);



        qr_code_scanner_view=findViewById(R.id.qr_scanner);
        qr_code_scanner=new CodeScanner(this, qr_code_scanner_view);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.black));
        }

        qr_code_scanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                serial_number=result.getText().toString();

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
                    new Handler(Looper.getMainLooper()).post(new Runnable(){
                        @Override
                        public void run(){
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
                                dialog_service.dismiss();
                                Activity_qr_scaner.serial_number = "";
                                Activity_type_choser.chosen_type = "";
                                startActivity(new Intent(Activity_qr_scaner.this, Activity_type_choser.class));
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
                                    String[] type = getResources().getStringArray(R.array.types);
                                    service_chosen_type = type[i].toString();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });

                            dialog_service.show();
                        }
                    });

                }
                else if (Activity_type_choser.chosen_type.equals("Заправка")){

                    //Диалоговое окно для отправки на заправку
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Dialog dialog_send_to_refile = new Dialog(Activity_qr_scaner.this);
                            dialog_send_to_refile.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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
                            dialog_get_from_refile.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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
                            dialog_util.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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