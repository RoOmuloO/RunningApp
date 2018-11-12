package com.example.romuloroger.runningapp.services.corrida;

public class CorridaService {

    private static CorridaService corridaService;

    private CorridaService() {
    }

    public static CorridaService getInstance() {
        if (corridaService == null) {
            corridaService = new CorridaService();
        }
        return corridaService;
    }

}
