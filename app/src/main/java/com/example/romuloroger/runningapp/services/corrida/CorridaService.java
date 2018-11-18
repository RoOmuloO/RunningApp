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


    public void buscarTodas() {
        new BuscarTodasCorridasTask(this.context).execute();
    }


    public static CorridaService getInstance(Context context) {
        if (corridaService == null) {
            corridaService = new CorridaService(context);
        }
        return corridaService;
    }

}
