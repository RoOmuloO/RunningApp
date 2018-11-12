package com.example.romuloroger.runningapp.services.corredor;

import com.example.romuloroger.runningapp.models.Corredor;
import com.example.romuloroger.runningapp.models.Corrida;

import java.util.List;

public class CorredorService {

    private static CorredorService corredorService;

    private CorredorService(){}

    public List<Corredor> buscarTodos() {
        return null;
    }

    public Corredor salvar(Corredor corredor) {
        return null;
    }

    public List<Corrida> buscarCorrida(int id) {
        return null;
    }

    public void CancelarCorrida(int idCorrida) {

    }

    public static CorredorService getInstance(){
        if(corredorService == null){
            corredorService = new CorredorService();
        }
        return corredorService;
    }

}
