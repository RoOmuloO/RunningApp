package com.example.romuloroger.runningapp.tasks.corrida;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;


import com.example.romuloroger.runningapp.R;
import com.example.romuloroger.runningapp.adapter.CorridasAdapter;
import com.example.romuloroger.runningapp.http.HttpService;
import com.example.romuloroger.runningapp.models.Corrida;

import com.example.romuloroger.runningapp.services.corrida.CorridaService;
import com.example.romuloroger.runningapp.utils.GlobalHttpErrorHandler;

import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

public class BuscarTodasCorridasTask extends AsyncTask<Void, Void, List<Corrida>> {

    private Context context;
    private HttpClientErrorException httpClientErrorException;
    private ProgressDialog progressBar;

    public BuscarTodasCorridasTask(Context context) {

        this.context = context;
        this.progressBar = new ProgressDialog(this.context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (((Activity) context) != null && !((Activity) context).isFinishing()) {
            this.progressBar.setTitle("Aguarde....");
            this.progressBar.show();
        }

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
        } else {
            this.listarCorridas(corridas);
        }

        this.progressBar.dismiss();
    }

    private void listarCorridas(List<Corrida> corridas) {
        RecyclerView recViewListaCorridas = (RecyclerView) ((Activity) context).findViewById(R.id.listaCorridas);
        CorridasAdapter corridasAdapter = new CorridasAdapter(corridas);
        if (recViewListaCorridas != null) {
            recViewListaCorridas.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this.context);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recViewListaCorridas.setLayoutManager(layoutManager);
            recViewListaCorridas.setAdapter(corridasAdapter);
        }

    }

}
