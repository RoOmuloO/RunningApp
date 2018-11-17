package com.example.romuloroger.runningapp.services.corrida;

import android.content.Context;

import com.example.romuloroger.runningapp.models.Corrida;
import com.example.romuloroger.runningapp.tasks.corrida.BuscarTodasCorridasTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CorridaService {

    private static CorridaService corridaService;
    private Context context;

    private CorridaService(Context context) {
        this.context = context;
    }


    public List<Corrida> buscarTodas() {
        try {
            List<Corrida> corridas = new BuscarTodasCorridasTask(this.context).execute().get();
            return corridas;
        } catch (InterruptedException e) {
            return new ArrayList<>();
        } catch (ExecutionException e) {
            return new ArrayList<>();
        }
    }


    public static CorridaService getInstance(Context context) {
        if (corridaService == null) {
            corridaService = new CorridaService(context);
        }
        return corridaService;
    }

}
