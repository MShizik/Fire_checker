package com.example.fire_checker;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class api_error extends AppCompatActivity {
    public void dialog_api_error_starter(Activity context){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Dialog dialog_error_api;
                dialog_error_api = new Dialog(context);
                dialog_error_api.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
                dialog_error_api.setContentView(R.layout.dialog_error_api);
                Button dialog_error_api_back_btn = (Button) dialog_error_api.findViewById(R.id.dialog_error_api_btn);
                dialog_error_api_back_btn.setOnClickListener(v -> {
                    dialog_error_api.dismiss();
                    Activity_qr_scaner.serial_number = "";
                });
                dialog_error_api.setCancelable(false);
                dialog_error_api.show();
            }
        });
    }
}
