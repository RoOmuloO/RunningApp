package com.example.romuloroger.runningapp.utils;

import android.content.Context;
import android.widget.Toast;

import org.springframework.web.client.HttpClientErrorException;

public class GlobalHttpErrorHandler {

    private static GlobalHttpErrorHandler globalHttpErrorHandler;

    private Context context;

    private GlobalHttpErrorHandler(Context context) {
        this.context = context;
    }

    public void handle(HttpClientErrorException e) {
        switch (e.getStatusCode().value()) {
            case 401:
                Toast.makeText(this.context, "Você não possui permissão para acessar esse recurso", Toast.LENGTH_LONG).show();
                break;
            case 404:
                Toast.makeText(this.context, "recurso não encontrado", Toast.LENGTH_LONG).show();
                break;
            case 500:
                Toast.makeText(this.context, "erro no servido!", Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(this.context, "Erro interno", Toast.LENGTH_LONG).show();
                break;
        }
    }

    public static GlobalHttpErrorHandler getInstance(Context context) {
        if (globalHttpErrorHandler == null) {
            globalHttpErrorHandler = new GlobalHttpErrorHandler(context);
        }
        return globalHttpErrorHandler;
    }

}
