package com.example.fire_checker;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class toRechargeRequest extends AsyncTask<Void, Void, Void> {
    final int[] res = new int[1];
    final int[] check = new int[1];
    String value ="";
    Activity this_act;

    public toRechargeRequest(Activity context){
        this_act = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        res[0] = 0;
        check[0] = 0;

    }

    @Override
    protected Void doInBackground(Void... voids) {
        String set_status_post_params = "fire_id=" + Activity_qr_scaner.serial_number;
        String PROPERTY_AUTH = "Bearer " + MainActivity.token;

        URL set_status_endpoint = null;
        try{
            set_status_endpoint = new URL("http://194.67.55.58:8080/api/toRecharge");
            try {
                HttpURLConnection set_status_connection = (HttpURLConnection) set_status_endpoint.openConnection();
                set_status_connection.setRequestMethod("POST");
                set_status_connection.setRequestProperty("Authorization", PROPERTY_AUTH);
                set_status_connection.setDoOutput(true);
                OutputStream os = set_status_connection.getOutputStream();
                os.write(set_status_post_params.getBytes());
                os.flush();
                os.close();
                System.out.println(set_status_connection.getResponseCode() + "Response code");
                if (set_status_connection.getResponseCode() == 200) {
                    InputStream set_status_response = set_status_connection.getInputStream();
                    InputStreamReader set_status_response_reader = new InputStreamReader(set_status_response, StandardCharsets.UTF_8);
                    JsonReader set_status_json_reader = new JsonReader(set_status_response_reader);
                    res[0] = 1;
                    set_status_json_reader.beginObject();
                    while (set_status_json_reader.hasNext()) {
                        String key = set_status_json_reader.nextName();
                        System.out.println(key + "Key");
                        if (key.equals("result")) {
                            value = set_status_json_reader.nextString();
                            System.out.println(value + "Value");
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
        if (check[0] == 1 && res[0] == 1) {
            System.out.println("Everything is ok");
            Activity_qr_scaner.serial_number = "";
            Activity_type_choser.chosen_type = "";
            this_act.startActivity(new Intent(this_act, Activity_Random_check.class));
        } else {
            api_error result_error = new api_error();
            result_error.dialog_api_error_starter(this_act);
        }
    }
}
