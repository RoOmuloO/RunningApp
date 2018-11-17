package com.example.romuloroger.runningapp.tasks.usuario;

import android.content.Context;
import android.os.AsyncTask;

import com.example.romuloroger.runningapp.http.HttpService;
import com.example.romuloroger.runningapp.models.request.LoginRequest;
import com.example.romuloroger.runningapp.models.response.LoginResponse;
import com.example.romuloroger.runningapp.utils.GlobalHttpErrorHandler;
import com.example.romuloroger.runningapp.utils.Preferencias;

import org.springframework.web.client.HttpClientErrorException;

public class LoginTask extends AsyncTask<LoginRequest, Void, LoginResponse> {

    private Context context;
    private HttpClientErrorException httpClientErrorException;

    public LoginTask(Context context) {
        this.context = context;
    }

    @Override
    protected LoginResponse doInBackground(LoginRequest... loginRequests) {
        HttpService<LoginResponse, LoginRequest> httpService = new HttpService<>("usuarios/", this.context, LoginResponse.class);
        try {
            LoginResponse response = httpService.post("login", loginRequests[0]);
            if (response != null) {
                Preferencias.salvarToken(response, this.context);
            }
            return response;
        } catch (HttpClientErrorException e) {
            this.httpClientErrorException = e;
            return null;
        }
    }


    @Override
    protected void onPostExecute(LoginResponse response) {
        super.onPostExecute(response);
        if (this.httpClientErrorException != null) {
            GlobalHttpErrorHandler.getInstance(this.context).handle(this.httpClientErrorException);
        }

    }
}
