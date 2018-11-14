package com.example.romuloroger.runningapp.services.response;

import java.io.Serializable;

public class LoginResponse implements Serializable {

    private int id;
    private String nome, email, token,tipo;

    public LoginResponse() {
    }

    public LoginResponse(int id, String nome, String email, String token, String tipo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.token = token;
        this.tipo = tipo;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
