package com.example.romuloroger.runningapp.models;

import java.util.Calendar;
import java.util.Date;

public class Corrida {
    private int id;
    private String nome;
    private int km;
    private int valorInscricao;
    private String dataCorrida;
    private boolean finalizada;
    private int numroInscritos;
    private boolean cancelada;
    private int posicao;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getKm() {
        return km;
    }

    public void setKm(int km) {
        this.km = km;
    }

    public int getValorInscricao() {
        return valorInscricao;
    }

    public void setValorInscricao(int valorInscricao) {
        this.valorInscricao = valorInscricao;
    }

    public String getDataCorrida() {
        return dataCorrida;
    }

    public void setDataCorrida(String dataCorrida) {
        this.dataCorrida = dataCorrida;
    }

    public boolean isFinalizada() {
        return finalizada;
    }

    public void setFinalizada(boolean finalizada) {
        this.finalizada = finalizada;
    }

    public int getNumroInscritos() {
        return numroInscritos;
    }

    public void setNumroInscritos(int numroInscritos) {
        this.numroInscritos = numroInscritos;
    }

    public boolean isCancelada() {
        return cancelada;
    }

    public void setCancelada(boolean cancelada) {
        this.cancelada = cancelada;
    }

    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }
}
