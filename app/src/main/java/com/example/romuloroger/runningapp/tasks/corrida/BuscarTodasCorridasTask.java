package com.example.romuloroger.runningapp.tasks.corrida;

import android.content.Context;
import android.os.AsyncTask;


import com.example.romuloroger.runningapp.http.HttpService;
import com.example.romuloroger.runningapp.models.Corrida;

import com.example.romuloroger.runningapp.utils.GlobalHttpErrorHandler;

import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

public class BuscarTodasCorridasTask extends AsyncTask<Void, Void, List<Corrida>> {

    private Context context;
    private HttpClientErrorException httpClientErrorException;

    public BuscarTodasCorridasTask(Context context) {
        this.context = context;
    }

    @Override
    protected List<Corrida> doInBackground(Void... voids) {
        HttpService<Corrida, Corrida> httpService = new HttpService<>("corridas/", this.context, Corrida.class);
        try {
            List<Corrida> corridas = httpService.getAll("", Corrida[].class);
            return corridas;
        } catch (HttpClientErrorException e) {
            this.httpClientErrorException = e;
        }

        return new ArrayList<>();
    }

    @Override
    protected void onPostExecute(List<Corrida> corridas) {
        super.onPostExecute(corridas);
        if (this.httpClientErrorException != null) {
            GlobalHttpErrorHandler.getInstance(this.context).handle(this.httpClientErrorException);
        }
    }
}
