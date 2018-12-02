package com.example.romuloroger.runningapp.models;

import java.util.ArrayList;
import java.util.List;

public class Corredor {
    private int id;
    private String nome;
    private String login;
    private String senha;
    private String email;
    private String pontuacao = "";
    private int classificacaoGeral = 0;
    private int posicao;
    private List<Corrida>corridas = new ArrayList<>();

    public Corredor() {
    }

    public Corredor(String nome, String login, String senha, String email) {
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.email = email;
    }

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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPontuacao() {
        return pontuacao;
    }

    public int getClassificacaoGeral() {
        return classificacaoGeral;
    }

    public void setClassificacaoGeral(int classificacaoGeral) {
        this.classificacaoGeral = classificacaoGeral;
    }

    public void setPontuacao(String pontuacao) {
        this.pontuacao = pontuacao;
    }

    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }

    public List<Corrida> getCorridas() {
        return corridas;
    }

    public void setCorridas(List<Corrida> corridas) {
        this.corridas = corridas;
    }
}
