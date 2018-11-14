package com.example.romuloroger.runningapp.services;

import android.os.AsyncTask;

import com.example.romuloroger.runningapp.services.request.LoginRequest;
import com.example.romuloroger.runningapp.services.response.LoginResponse;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class LoginServiceTask extends AsyncTask<LoginRequest,Void,LoginResponse> {

    @Override
    protected LoginResponse doInBackground(LoginRequest... loginRequests) {
        URL url = null;
        try {
            url = new URL("https://api.davesmartins.com.br/api/usuarios/login");

            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setReadTimeout(95 * 1000);
            urlConnection.setConnectTimeout(95 * 1000);
            urlConnection.setDoInput(true);
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("X-Environment", "android");

            //String login = "{\"login\": \""+strings[0]+"\"," +
            //        "\"senha\": \""+strings[1]+"\" }";
            String login = new Gson().toJson(loginRequests[0]);
            urlConnection.setDoOutput(true);
            urlConnection.getOutputStream().write(login.getBytes());

            urlConnection.connect();

            String finalJson = "";
            if (urlConnection.getResponseCode() == 200) {
                InputStream responseBody = urlConnection.getInputStream();
                InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
                BufferedReader reader = new BufferedReader(responseBodyReader);
                StringBuffer buffer = new StringBuffer();
                String line ="";

                while ((line=reader.readLine())!=null){
                    buffer.append(line);
                }

                finalJson = buffer.toString();
            } else {
                finalJson = urlConnection.getResponseCode()+"";
            }

            LoginResponse usuario = new Gson().fromJson(finalJson.toString(),LoginResponse.class);

            return usuario;


        }catch (MalformedURLException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }
}
