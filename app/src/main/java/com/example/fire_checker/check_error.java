package com.example.fire_checker;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class check_error extends AppCompatActivity {
    public void dialog_check_error_starter(){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Dialog dialog_error_check = new Dialog(getApplicationContext());
                dialog_error_check.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
                dialog_error_check.setContentView(R.layout.dialog_error_check);
                Button dialog_error_check_back_btn = (Button) dialog_error_check.findViewById(R.id.dialog_error_check_btn);
                dialog_error_check_back_btn.setOnClickListener(v -> {
                    dialog_error_check.dismiss();
                    Activity_qr_scaner.serial_number = "";
                });
                dialog_error_check.setCancelable(false);
                dialog_error_check.show();
            }
        });
    }
}
